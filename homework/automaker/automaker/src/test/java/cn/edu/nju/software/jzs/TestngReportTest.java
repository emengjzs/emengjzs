package cn.edu.nju.software.jzs;

import com.alibaba.fastjson.JSON;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testng.parser.ResultsParser;
import testng.results.MethodResult;
import testng.results.PackageResult;
import testng.results.TestNGResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by emengjzs on 2016/4/14.
 */
public class TestngReportTest {

    Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void testTestngXmlWithExistingResultXml() {
        URL resource = CommonUtil.getResource(Constants.TESTNG_XML_PRECHECKINS);
        Assert.assertNotNull(resource);
        TestNGResult results = CommonUtil.getResults(resource.getFile());
        Assert.assertFalse("Collection shouldn't have been empty", results.getTestList().isEmpty());
        log.debug(JSON.toJSONString(results, true));
    }

    @Test
    public void testTestngXmlWithSameTestNameDiffSuites() {
        URL resource = CommonUtil.getResource(Constants.TESTNG_XML_SAME_TEST_NAME);
        Assert.assertNotNull(resource);
        TestNGResult results = CommonUtil.getResults(resource.getFile());
        Assert.assertFalse("Collection shouldn't have been empty", results.getTestList().isEmpty());
        Assert.assertEquals(2, results.getTestList().size());
        results.tally();
        Assert.assertEquals(1, results.getPackageNames().size());
        Assert.assertEquals(3, results.getPackageMap().values().iterator().next().getChildren().size());
        Assert.assertEquals(4, results.getPassCount());
        Assert.assertEquals(4, results.getPassedTests().size());
        log.debug(JSON.toJSONString(results, true));
    }

    @Test
    public void testTestngXmlWithExistingResultXmlGetsTheRightDurations() {
        URL resource = CommonUtil.getResource(Constants.TESTNG_XML_DATAPROVIDER);
        Assert.assertNotNull(resource);
        TestNGResult results = CommonUtil.getResults(resource.getFile());
        Assert.assertFalse("Collection shouldn't have been empty", results.getTestList().isEmpty());

        // This test assumes that there is only 1 package in
        // sample-testng-dp-result that contains tests that add to 12 ms
        results.tally();
        Map<String, PackageResult> packageResults = results.getPackageMap();
        Assert.assertEquals(1, packageResults.values().size());
        PackageResult result = packageResults.values().iterator().next();
        Assert.assertEquals("org.jenkins", result.getName());
        //durations are all in seconds
        Assert.assertEquals(0.009f, result.getDuration());
        log.debug(JSON.toJSONString(results, true));
    }

    @Test
    public void testTestngXmlWithNonExistingResultXml() {
        TestNGResult results = CommonUtil.getResults("/invalid/path/to/file/new-test-result.xml");
        Assert.assertTrue("Collection should have been empty. Number of results : "
                + results.getTestList().size(), results.getTestList().isEmpty());
        log.debug(JSON.toJSONString(results, true));
    }

    @Test
    public void parseTestNG() throws IOException {
        TestNGResult results = CommonUtil.getResults(CommonUtil.getResource(Constants.TESTNG_XML_TESTNG).getFile());
        results.tally();
        FileWriter fe = new FileWriter(new File("a.json"));
        fe.write(JSON.toJSONString(results, true));
        fe.flush();
        fe.close();
    }

    @Test
    public void testParseEmptyException() {
        TestNGResult results = CommonUtil.getResults(CommonUtil.getResource(Constants.TESTNG_XML_EMPTY_EXCEPTION).getFile());
        results.tally();
        Assert.assertEquals(1, results.getPassCount());
        MethodResult mr = results.getPassedTests().get(0);
        Assert.assertEquals("$java.lang.IllegalStateException$$EnhancerByMockitoWithCGLIB$$c0ded2d3",
                mr.getException().getExceptionName());
        log.debug(JSON.toJSONString(results, true));
    }

    @Test
    public void testDateParser() throws ParseException {
        //example of date format used in testng report
        String dateString = "2010-07-20T11:49:17Z";
        SimpleDateFormat sdf = new SimpleDateFormat(ResultsParser.DATE_FORMAT);
        sdf.parse(dateString);
    }

    @Test
    public void testReporterOutputForMethods() throws ParseException {
        String filename = Constants.TESTNG_XML_REPORTER_LOG_OUTPUT;
        URL resource = TestngReportTest.class.getClassLoader().getResource(filename);
        Assert.assertNotNull(resource);
        TestNGResult results = CommonUtil.getResults(resource.getFile());
        Assert.assertFalse("Collection shouldn't have been empty", results.getTestList().isEmpty());
        Assert.assertEquals(1, results.getTestList().size());
        results.tally();
        Assert.assertEquals(1, results.getPackageNames().size());
        Assert.assertEquals(1, results.getPackageMap().values().iterator().next().getChildren().size());
        Assert.assertEquals(1, results.getPassCount());
        Assert.assertEquals(1, results.getPassedTests().size());
        Assert.assertEquals(1, results.getFailedTests().size());
        Assert.assertNotNull(results.getFailedTests().get(0).getException());
        Assert.assertNotNull(results.getFailedTests().get(0).getReporterOutput());
        Assert.assertEquals("Some Reporter.log() statement<br/>Another Reporter.log() statement<br/>",
                results.getFailedTests().get(0).getReporterOutput());
        Assert.assertNull(results.getPassedTests().get(0).getReporterOutput());
        log.debug(JSON.toJSONString(results, true));
    }

}