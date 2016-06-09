package cn.edu.nju.software.jzs.service.deploy;

/**
 * Created by emengjzs on 2016/4/11.
 */
public enum BuildType {

    WAR,
    JAR;

    public String toString() {
        return this.name().toLowerCase();
    }

}
