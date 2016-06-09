package cn.edu.nju.software.jzs.service.analyse;

/**
 * Created by emengjzs on 2016/4/30.
 */
public class MethodInfo {

    protected String clazzName;
    protected String methodName;
    protected Long duration;

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public boolean isEnd() {
        return this.duration != null;
    }

    public boolean isSameMethod(MethodInfo methodInfo) {
        return this.getMethodName().equals(methodInfo.getMethodName())
                && this.getClazzName().equals(methodInfo.getClazzName());
    }
}
