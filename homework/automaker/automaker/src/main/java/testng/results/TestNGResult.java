package testng.results;



import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.*;

/**
 * Represents all the results gathered for a single build (or a single suite,
 * while parsing the test results)
 *
 * @author nullin
 * @author farshidce
 */
public class TestNGResult extends BaseResult implements Serializable {

    private static final long serialVersionUID = -3491974223665601995L;
    private List<TestNGTestResult> testList = new ArrayList<TestNGTestResult>();
    private List<MethodResult> passedTests = new ArrayList<MethodResult>();
    private List<MethodResult> failedTests = new ArrayList<MethodResult>();
    private List<MethodResult> skippedTests = new ArrayList<MethodResult>();
    private List<MethodResult> failedConfigurationMethods = new ArrayList<MethodResult>();
    private List<MethodResult> skippedConfigurationMethods = new ArrayList<MethodResult>();
    private long startTime;
    private long endTime;
    private int passCount;
    private int failCount;
    private int skipCount;
    private int failedConfigCount;
    private int skippedConfigCount;
    private Map<String, PackageResult> packageMap = new HashMap<String, PackageResult>();

    /**
     * @param name input name is ignored
     * @deprecated don't use this constructor
     */
    public TestNGResult(String name) {
        super(name);
    }

    public TestNGResult() {
        super("");
    }

    @Override
    public String getTitle() {
        return getDisplayName();
    }

    @Override
    public List<MethodResult> getFailedTests() {
        return failedTests;
    }

    @Override
    public List<MethodResult> getPassedTests() {
        return passedTests;
    }

    @Override
    public List<MethodResult> getSkippedTests() {
        return skippedTests;
    }

    @JSONField(serialize = false)
    public List<MethodResult> getFailedConfigs() {
        return failedConfigurationMethods;
    }
    @JSONField(serialize = false)
    public List<MethodResult> getSkippedConfigs() {
        return skippedConfigurationMethods;
    }

    /**
     * Gets the total number of passed tests.
     */
    public int getPassCount() {
        return passCount;
    }

    /**
     * Gets the total number of failed tests.
     */
    public int getFailCount() {
        return failCount;
    }

    /**
     * Gets the total number of skipped tests.
     */
    public int getSkipCount() {
        return skipCount;
    }

    @JSONField(serialize = false)
    public List<TestNGTestResult> getTestList() {
        return testList;
    }

    public int getTotalCount() {
        return super.getTotalCount();
    }

    @Override
    public float getDuration() {
        return (float) (endTime - startTime) / 1000f;
    }

    @JSONField(serialize = false)
    public int getFailedConfigCount() {
        return failedConfigCount;
    }
    @JSONField(serialize = false)
    public int getSkippedConfigCount() {
        return skippedConfigCount;
    }
    @JSONField(serialize = false)
    public Collection<PackageResult> getPackageList() {
        return packageMap.values();
    }
    @JSONField(serialize = false)
    public Map<String, PackageResult> getPackageMap() {
        return packageMap;
    }

    public Set<String> getPackageNames() {
        return packageMap.keySet();
    }

    /**
     * Adds only the <test>s that already aren't part of the list
     *
     * @param testList
     */
    //TODO: whats going on here? why unique?
    public void addUniqueTests(List<TestNGTestResult> testList) {
        Set<TestNGTestResult> tmpSet = new HashSet<TestNGTestResult>(this.testList);
        tmpSet.addAll(testList);
        this.testList = new ArrayList<TestNGTestResult>(tmpSet);
    }


    @Override
    public String toString() {
        return String.format("TestNGResult {" +
                        "totalTests=%d, " +
                        "failedTests=%d, skippedTests=%d, failedConfigs=%d, " +
                        "skippedConfigs=%d}", //name,
                passCount + failCount + skipCount, failCount,
                skipCount, failedConfigCount,
                skippedConfigCount);
    }

    /**
     * Updates the calculated fields
     */
    @Override
    public void tally() {
        failedConfigCount = failedConfigurationMethods.size();
        skippedConfigCount = skippedConfigurationMethods.size();
        failCount = failedTests.size();
        passCount = passedTests.size();
        skipCount = skippedTests.size();

        packageMap.clear();
        for (TestNGTestResult _test : testList) {
            for (ClassResult _class : _test.getClassList()) {
                String pkg = _class.getPkgName();
                if (packageMap.containsKey(pkg)) {
                    List<ClassResult> classResults = packageMap.get(pkg).getChildren();
                    if (!classResults.contains(_class)) {
                        classResults.add(_class);
                    }
                } else {
                    PackageResult tpkg = new PackageResult(pkg);
                    tpkg.getChildren().add(_class);
                    tpkg.setParent(this);
                    packageMap.put(pkg, tpkg);
                }
            }
        }

        startTime = Long.MAX_VALUE;
        endTime = 0;
        for (PackageResult pkgResult : packageMap.values()) {
            pkgResult.tally();
            if (this.startTime > pkgResult.getStartTime()) {
                startTime = pkgResult.getStartTime(); //cf. ClassResult#tally()
            }
            if (this.endTime < pkgResult.getEndTime()) {
                endTime = pkgResult.getEndTime();
            }
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public BaseResult getParent() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return getName();
    }

    @Override
    @JSONField(serialize = false)
    public Collection<PackageResult> getChildren() {
        return packageMap.values();
    }

    @Override
    public boolean hasChildren() {
        return !packageMap.isEmpty();
    }
}
