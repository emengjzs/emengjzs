package cn.edu.nju.software.jzs.service.analyse;

import cn.edu.nju.software.jzs.entity.AnalyseRecord;
import cn.edu.nju.software.jzs.entity.HTTPMethod;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by emengjzs on 2016/4/30.
 */
public class AnalyseContext {


    private String basePackage = "";
    private Set<String> modules;
    private String name;
    private int pid;
    private AnalyseRecord analyseRecord;
    private Map<String, Object> params;
    private HTTPMethod method;
    private String url;
    private String basePath;
    private long duration;
    public AnalyseContext() {
        analyseRecord = new AnalyseRecord();
    }

    public String getBasePackage() {
        return basePackage;
    }


    public String getBasePackageForScript() {
        return basePackage.replace(".", "\\\\.");
    }

    public String getModulesFoeScript() {
        return CollectionUtils.isEmpty(modules) ?
                "\\\\." : "\\\\." + String.format("(%s)",
                StringUtils.join(modules, "|").replace(".", "\\\\."));
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getModules() {
        return modules;
    }

    public void setModules(Set<String> modules) {
        this.modules = modules;
    }

    public void setModules(String... modules) {
        this.modules = new HashSet<String>(Arrays.asList(modules));
    }



    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public AnalyseRecord getAnalyseRecord() {
        return analyseRecord;
    }

    public void setAnalyseRecord(AnalyseRecord analyseRecord) {
        this.analyseRecord = analyseRecord;
    }

    public void setCreateTime() {
        this.analyseRecord.setCreateTime(new Date());
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public HTTPMethod getMethod() {
        return method;
    }

    public void setMethod(HTTPMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
