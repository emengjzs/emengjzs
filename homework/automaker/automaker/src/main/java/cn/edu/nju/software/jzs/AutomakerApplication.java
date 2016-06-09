package cn.edu.nju.software.jzs;

import cn.edu.nju.software.jzs.service.schedule.JobAutowiredBeanFactory;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.wc.SVNClientManager;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.serializer.SerializerFeature.QuoteFieldNames;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteDateUseDateFormat;

@SpringBootApplication
@EnableAsync

public class AutomakerApplication  {


    public static void main(String[] args) {
        SpringApplication.run(AutomakerApplication.class, args);
    }


    @Bean
    public SVNClientManager svnClientManager() {
        // ac
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
        return SVNClientManager.newInstance();
    }


    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobAutowiredBeanFactory());
        return schedulerFactoryBean;
    }

    @Bean
    public JobAutowiredBeanFactory jobAutowiredBeanFactory() {
        return new JobAutowiredBeanFactory();
    }

    @Bean
    public javax.validation.Validator localValidatorFactoryBean() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        localValidatorFactoryBean.setValidationMessageSource(messageSource());
        return localValidatorFactoryBean;
    }
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasenames("classpath:org/hibernate/validator/ValidationMessages");
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
        reloadableResourceBundleMessageSource.setCacheSeconds(60);
        return reloadableResourceBundleMessageSource;
    }

    @Bean(name = "myRestTemplate")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        List<HttpMessageConverter<?>> list = new ArrayList<>();
        list.add(new FormHttpMessageConverter());
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("utf-8"));
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_HTML);
        stringHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        list.add(stringHttpMessageConverter);
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setCharset(Charset.forName("utf-8"));
        mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypes.add(MediaType.TEXT_HTML);
        mediaTypes.add(new MediaType("text", "html", Charset.forName("UTF-8")));
        fastJsonHttpMessageConverter.setFeatures(QuoteFieldNames, WriteDateUseDateFormat);
        list.add(fastJsonHttpMessageConverter);
        restTemplate.setMessageConverters(list);
        return restTemplate;
    }


}
