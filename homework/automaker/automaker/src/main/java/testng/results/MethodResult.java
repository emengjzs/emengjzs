package testng.results;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Handles result pertaining to a single test method
 */
@SuppressWarnings("serial")
public class MethodResult extends BaseResult {

    //pass, fail or skip status
    private String status;
    //test description if any from @Test annotation
    private String description;
    //is configuration method or not
    private boolean isConfig;
    //duration in seconds
    private float duration;
    //Exception thrown on running this test method (if any)
    private MethodResultException exception;
    //start time
    private long startedAt;
    //end time
    private long endedAt;
    //a test instance name if one is provided using ITest interface
    private String testInstanceName;
    //name of the <test> containing this method
    private String parentTestName;
    //name of the <suite> containing this method
    private String parentSuiteName;
    //groups this test method is part of
    private List<String> groups;
    //parameters passed into this test (if any)
    private List<String> parameters;
    // already stored with lines separated using <br/>
    private String reporterOutput;

    /**
     * unique id for this test's run (helps associate the test method with
     * related configuration methods)
     */
    private String testRunId;

    /**
     * unique id for this test method
     */
    private String testUuid;

    public MethodResult(String name,
                        String status,
                        String description,
                        String duration,
                        long startedAt,
                        String isConfig,
                        String testRunId,
                        String parentTestName,
                        String parentSuiteName,
                        String testInstanceName) {
        super(name);
        this.status = status;
        this.description = description;
        // this uuid is used later to group the tests and config-methods together
        this.testRunId = testRunId;
        this.testInstanceName = testInstanceName;
        this.parentTestName = parentTestName;
        this.parentSuiteName = parentSuiteName;
        this.startedAt = startedAt;

        try {
            long durationMs = Long.parseLong(duration);
            //more accurate end time when test took less than a second to run
            this.endedAt = startedAt + durationMs;
            this.duration = (float) durationMs / 1000f;
        } catch (NumberFormatException e) {
            System.err.println("Unable to parse duration value: " + duration);
        }

        if (isConfig != null) {
         /*
          * If is-config attribute is present on test-method,
          * it's always set to true
          */
            this.isConfig = true;
        }
    }

    public void setTestUuid(String testUuid) {
        this.testUuid = testUuid;
    }

    /**
     * @return name of the <test> tag that this method is part of
     */
    public String getParentTestName() {
        return parentTestName;
    }

    /**
     * @return name of the suite this method is part of
     */
    public String getParentSuiteName() {
        return parentSuiteName;
    }

    public String getTestRunId() {
        return testRunId;
    }

    public Date getStartedAt() {
        return new Date(startedAt);
    }

    public long getStartTime() {
        return startedAt;
    }

    public long getEndTime() {
        return endedAt;
    }

    public MethodResultException getException() {
        return exception;
    }

    public void setException(MethodResultException exception) {
        this.exception = exception;
    }

    /*
        Overriding to add testUuid to name if a testUuid is present.
        This is applicable only in cases of DataProvider tests
     */
    @Override
    public String getSafeName() {
        String name = getName();
        if (this.testUuid != null) {
            name += "_" + this.testUuid;
        }
        return safe(name);
    }

    //special case for methods so overriding this method here

    public TestResult findCorrespondingResult(String id) {
        return getSafeName().equals(id) ? this : null;
    }


    @Override
    public float getDuration() {
        return duration;
    }


    public String getStatus() {
        return status;
    }

    @Override

    public String getDescription() {
        return description;
    }

    public List<String> getGroups() {
        return groups;
    }


    public List<String> getParameters() {
        return parameters;
    }

    /**
     * Added only to expose possible exception via  .../api/xxx
     *
     * @return String representation of the exception
     */
    @Override
    public String getErrorStackTrace() {
        if (exception != null) {
            return exception.toString();
        }
        return null;
    }

    /**
     * If there was an error or a failure, this is the text from the message.
     */
    @Override
    public String getErrorDetails() {
        if (exception != null) {
            return exception.getMessage();
        }
        return null;
    }


    /**
     * Added only to expose class name as part of method result via  .../api/xxx
     *
     * @return String representation of the exception
     */
    public String getClassName() {
        return getParent().getName();
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    @JSONField(serialize = false)
    public boolean isConfig() {
        return isConfig;
    }


    /**
     * Gets the method result, if any, from the given set of test results. Searches
     * for method result that matches the url of this method
     *
     * @param results test results
     * @return method result
     */
    private MethodResult getMethodResult(TestNGResult results) {
        Map<String, PackageResult> packageMap = results.getPackageMap();
        //get package name!
        String methodPackageName = getParent().getParent().getName();
        String methodClassName = getParent().getName();

        if (packageMap.containsKey(methodPackageName) &&
                packageMap.get(methodPackageName).getChildren() != null) {
            List<ClassResult> classResults =
                    packageMap.get(methodPackageName).getChildren();
            for (ClassResult classResult : classResults) {
                if (classResult.getName().equals(methodClassName)) {
                    List<MethodResult> methodResults;
                    if (this.isConfig) {
                        methodResults = classResult.getConfigurationMethods();
                    } else {
                        methodResults = classResult.getTestMethods();
                    }

                    if (methodResults != null) {
                        for (MethodResult methodResult : methodResults) {
                            if (methodResult.getUrl().equals(this.getUrl())) {
                                return methodResult;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Used to give different color based on test status
     *
     * @return
     */
    @JSONField(serialize = false)
    public Object getCssClass() {
        if (this.status != null) {
            if (this.status.equalsIgnoreCase("pass")) {
                return "result-passed";
            } else {
                if (this.status.equalsIgnoreCase("skip")) {
                    return "result-skipped";
                } else {
                    if (this.status.equalsIgnoreCase("fail")) {
                        return "result-failed";
                    }
                }
            }
        }
        return "result-passed";
    }

    /**
     *
     */
    public void setReporterOutput(String reporterOutput) {
        this.reporterOutput = reporterOutput;
    }

    /**
     * @return reporter output
     */
    public String getReporterOutput() {
        return reporterOutput;
    }

    @Override
    @JSONField(serialize = false)
    public Collection<? extends TestResult> getChildren() {
        return null;
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    public String getPackageName() {
        return ((ClassResult)this.getParent()).getPkgName();
    }
}
