package jzs.test.base.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import jzs.test.base.annotation.PropertyConfiguration;

/**
 * Created by emengjzs on 2016/4/15.
 */
public class PropertiesConfigurationBeanProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        try {
        	if (bean.getClass().getAnnotation(PropertyConfiguration.class) != null) {
        		return PropertiesConfigFactory.getInstance(bean.getClass());
        	}
            
        } catch (Exception e) {
            return bean;
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
