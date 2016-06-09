package cn.edu.nju.software.jzs.service.analyse;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by emengjzs on 2016/4/30.
 */
public class MethodTree extends MethodInfo{





    private List<MethodTree> children = new LinkedList<MethodTree>();

    MethodTree parent;


    public boolean isRoot() {
        return parent == null;
    }


    public MethodTree() {

    }

    public MethodTree(MethodInfo methodInfo) {
        this.clazzName = methodInfo.clazzName;
        this.duration = methodInfo.duration;
        this.methodName = methodInfo.methodName;
    }

    public MethodTree(String className, String methodName) {
        this.setMethodName(methodName);
        this.setClazzName(className);
    }


    public void addChildren(MethodTree methodTree) {
        getChildren().add(methodTree);
        methodTree.parent = this;
    }

    public MethodTree parent() {
        return parent;
    }


    public List<MethodTree> getChildren() {
        return children;
    }

    public void setChildren(List<MethodTree> children) {
        this.children = children;
    }

    public String getPercentage() {
        if (this.parent != null && this.parent.getDuration() != null && this.getDuration() != null) {
            return String.format("%.4f", this.duration / (double) this.parent.getDuration());
        }
        return null;
    }

    public void setLastDuration(long time) {
        this.setDuration(time);
        if (this.getChildren() != null && this.getChildren().size() == 1) {
            this.getChildren().get(0).setDuration(time);
        }
    }


}
