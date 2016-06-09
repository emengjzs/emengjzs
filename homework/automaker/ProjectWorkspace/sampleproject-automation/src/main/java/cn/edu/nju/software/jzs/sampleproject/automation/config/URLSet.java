package cn.edu.nju.software.jzs.sampleproject.automation.config;

import jzs.test.base.http.URLInfo;

/**
 * Created by emengjzs on 2016/4/15.
 */
public enum URLSet implements URLInfo {
    ORDER_SUBMIT("/index");

    private String url;

    private URLSet(String url) {
        this.url = url;
    }

    @Override
    public String getURL() {
        return url;
    }
}
