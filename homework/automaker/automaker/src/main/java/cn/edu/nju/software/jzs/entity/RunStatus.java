package cn.edu.nju.software.jzs.entity;

/**
 * Created by emengjzs on 2016/4/5.
 */
public enum RunStatus {
    NOT_READY(0),
    RUNNING(1),
    PAUSE(2),
    WARN(3),
    SUCCESS(4),
    FAIL(5);
    Integer index;

    RunStatus(Integer index) {
     this.index = index;
    }

}
