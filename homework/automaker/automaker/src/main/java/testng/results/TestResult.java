package testng.results;/*
 * The MIT License
 *
 * Copyright (c) 2009, Yahoo!, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */




import java.util.Collection;

import static java.util.Collections.emptyList;


/**
 * A class that represents a general concept of a test result, without any
 * language or implementation specifics.
 * Subclasses must add @Exported annotation to the fields they want to export.
 *
 * @since 1.343
 */
public abstract class TestResult extends TestObject {





    /**
     * Request that the result update its counts of its children. Does not
     * require a parent action or owner or siblings. Subclasses should
     * implement this, unless they are *always* in a tallied state.
     */
    public void tally() {
    }

    /**
     * Sets the parent test result
     * @param parent
     */
    public void setParent(TestObject parent) {
    }

    /**
     * Gets the human readable title of this result object.
     */
    public /* abstract */ String getTitle(){
        return "";
    }



    /**
     * Time it took to run this test. In seconds.
     */
    public /* abstract */ float getDuration() {
        return 0.0f;
    }

    /**
     * Gets the total number of passed tests.
     */
    public /* abstract */ int getPassCount() {
        return 0;
    }

    /**
     * Gets the total number of failed tests.
     */
    public /* abstract */ int getFailCount() {
        return 0;
    }


    /**
     * Gets the total number of skipped tests.
     */
    public /* abstract */ int getSkipCount() {
        return 0;
    }




    /**
     * Gets the "children" of this test result that failed
     * @return the children of this test result, if any, or an empty collection
     */
    public Collection<? extends TestResult> getFailedTests() {
        return emptyList();
    }


    /**
     * Gets the "children" of this test result that passed
     * @return the children of this test result, if any, or an empty collection
     */
    public Collection<? extends TestResult> getPassedTests() {
        return emptyList();
    }

    /**
     * Gets the "children" of this test result that were skipped
     * @return the children of this test result, if any, or an empty list
     */
    public Collection<? extends TestResult> getSkippedTests() {
        return emptyList();
    }

    /**
     * If this test failed, then return the build number
     * when this test started failing.
     */
    public int getFailedSince() {
        return 0;
    }



    /**
     * The stdout of this test.
     */
    public String getStdout() {
        return "";
    }

    /**
     * The stderr of this test.
     */
    public String getStderr() {
        return "";
    }

    /**
     * If there was an error or a failure, this is the stack trace, or otherwise null.
     */
    public String getErrorStackTrace() {
        return "";
    }

    /**
     * If there was an error or a failure, this is the text from the message.
     */
    public String getErrorDetails() {
        return "";
    }

    /**
     * @return true if the test was not skipped and did not fail, false otherwise.
     */
    public boolean isPassed() {
        return ((getSkipCount() == 0) && (getFailCount() == 0));
    }

    public String toPrettyString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("Name: ").append(this.getName()).append(", ");
        sb.append("Total Count: ").append(this.getTotalCount()).append(", ");
        sb.append("Fail: ").append(this.getFailCount()).append(", ");
        sb.append("Skipt: ").append(this.getSkipCount()).append(", ");
        sb.append("Pass: ").append(this.getPassCount()).append(",\n");
        sb.append("Test Result Class: " ).append(this.getClass().getName()).append(" }\n");
        return sb.toString();
    }


}
