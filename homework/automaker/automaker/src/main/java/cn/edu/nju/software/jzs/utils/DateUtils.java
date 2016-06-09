package cn.edu.nju.software.jzs.utils;

import java.util.Date;

/**
 * Created by emengjzs on 2016/4/6.
 */
public class DateUtils {



    public static Long timeDiff(Date s, Date e) {
        return e.getTime() - s.getTime();
    }
}
