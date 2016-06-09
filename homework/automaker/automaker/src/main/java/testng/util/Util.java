package testng.util;

/**
 * Created by emengjzs on 2016/4/14.
 */
public class Util {

    // Constant number of milliseconds in various time units.
    private static final long ONE_SECOND_MS = 1000;
    private static final long ONE_MINUTE_MS = 60 * ONE_SECOND_MS;
    private static final long ONE_HOUR_MS = 60 * ONE_MINUTE_MS;
    private static final long ONE_DAY_MS = 24 * ONE_HOUR_MS;
    private static final long ONE_MONTH_MS = 30 * ONE_DAY_MS;
    private static final long ONE_YEAR_MS = 365 * ONE_DAY_MS;

    public static String getTimeSpanString(long duration) {
        // Break the duration up in to units.
        long years = duration / ONE_YEAR_MS;
        duration %= ONE_YEAR_MS;
        long months = duration / ONE_MONTH_MS;
        duration %= ONE_MONTH_MS;
        long days = duration / ONE_DAY_MS;
        duration %= ONE_DAY_MS;
        long hours = duration / ONE_HOUR_MS;
        duration %= ONE_HOUR_MS;
        long minutes = duration / ONE_MINUTE_MS;
        duration %= ONE_MINUTE_MS;
        long seconds = duration / ONE_SECOND_MS;
        duration %= ONE_SECOND_MS;
        long millisecs = duration;

        if (years > 0)
            return makeTimeSpanString(years, Messages.Util_year(years), months, Messages.Util_month(months));
        else if (months > 0)
            return makeTimeSpanString(months, Messages.Util_month(months), days, Messages.Util_day(days));
        else if (days > 0)
            return makeTimeSpanString(days, Messages.Util_day(days), hours, Messages.Util_hour(hours));
        else if (hours > 0)
            return makeTimeSpanString(hours, Messages.Util_hour(hours), minutes, Messages.Util_minute(minutes));
        else if (minutes > 0)
            return makeTimeSpanString(minutes, Messages.Util_minute(minutes), seconds, Messages.Util_second(seconds));
        else if (seconds >= 10)
            return Messages.Util_second(seconds);
        else if (seconds >= 1)
            return Messages.Util_second(seconds+(float)(millisecs/100)/10); // render "1.2 sec"
        else if(millisecs>=100)
            return Messages.Util_second((float)(millisecs/10)/100); // render "0.12 sec".
        else
            return Messages.Util_millisecond(millisecs);
    }

    private static String makeTimeSpanString(long bigUnit,
                                             String bigLabel,
                                             long smallUnit,
                                             String smallLabel) {
        String text = bigLabel;
        if (bigUnit < 10)
            text += ' ' + smallLabel;
        return text;
    }

}
