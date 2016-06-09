package cn.edu.nju.software.jzs.sampleproject.automation.config;

import jzs.test.base.annotation.PropertyConfiguration;
import jzs.test.base.config.WebServiceConfig;

/**
 * Created by emengjzs on 2016/4/15.
 */
@PropertyConfiguration(value = "config.properties", prefix = "sample")
public class SampleProjectConfig implements WebServiceConfig {

    private String webHostUrl;

    @Override
    public String getWebHostUrl() {
        return webHostUrl;
    }

    public void setWebHostUrl(String webHostUrl) {
        this.webHostUrl = webHostUrl;
    }
}
