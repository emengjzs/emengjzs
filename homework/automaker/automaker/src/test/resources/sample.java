import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.println;


@BTrace
public class TimeAnalyzer {

    @OnMethod(
            clazz = "/cn\\.edu\\.nju\\.software\\.jzs\\.sample\\.(Service|Controller).*/",
            method = "/.*/",
            location = @Location(Kind.RETURN)
    )
    public static void onExecuteReturn(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod, @Duration long durationL) {
        println("{" +
                "\"duration\":" + durationL / 1000 + ", " +
                "\"methodName\":" + "\"" + probeMethod + "\"," +
                "\"ClazzName\":" + "\"" + probeClass + "\"," +
                "}");
    }

    @OnMethod(
            clazz = "/cn\\.edu\\.nju\\.software\\.jzs\\.sample\\.(Service|Controller).*/",
            method = "/.*/",
            location = @Location(Kind.ERROR)
    )
    public static void onExecuteErrorReturn(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod, @Duration long durationL) {
        println("{" +
                "\"duration\":" + durationL / 1000 + ", " +
                "\"methodName\":" + "\"" + probeMethod + "\"," +
                "\"ClazzName\":" + "\"" + probeClass + "\"" +
                "}");
    }


    @OnMethod(clazz = "/cn\\.edu\\.nju\\.software\\.jzs\\.sample\\.(Service|Controller).*/",
            method = "/.*/",
            location = @Location(Kind.ENTRY))
    public static void onExecuteBegin(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod) {
        println("{" +
                "\"methodName\":" + "\"" + probeMethod + "\"," +
                "\"ClazzName\":" + "\"" + probeClass + "\"" +
                "}");
    }
}
