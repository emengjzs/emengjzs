package testng.util;

/**
 * Created by emengjzs on 2016/4/14.
 */

import org.jvnet.localizer.ResourceBundleHolder;
public class Messages {

    private final static ResourceBundleHolder holder = ResourceBundleHolder.get(Messages.class);
    public static String Util_day(Object arg1) {
        return holder.format("Util.day", arg1);
    }
    public static String Util_hour(Object arg1) {
        return holder.format("Util.hour", arg1);
    }

    public static String Util_minute(Object arg1) {
        return holder.format("Util.minute", arg1);
    }

    public static String Util_year(Object arg1) {
        return holder.format("Util.year", arg1);
    }
    public static String Util_month(Object arg1) {
        return holder.format("Util.month", arg1);
    }
    public static String Util_second(Object arg1) {
        return holder.format("Util.second", arg1);
    }
    public static String Util_millisecond(Object arg1) {
        return holder.format("Util.millisecond", arg1);
    }
}
