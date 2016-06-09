package cn.edu.nju.software.jzs.handler;

/**
 * Created by emengjzs on 2016/4/4.
 */
public class Msg {

    private Object data;
    private boolean success;
    private String message = "";

    public static Msg of(Object o) {
        Msg m = new Msg();
        m.data = o;
        m.success = true;
        return m;
    }

    public static Msg res(String message) {
        Msg m = new Msg();
        m.success = true;
        m.message = message;
        return m;
    }

    public static Msg error(String message) {
        Msg m = new Msg();
        m.success = false;
        m.message = message;
        return m;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
