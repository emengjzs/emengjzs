package testng.results;/*
 * The MIT License
 *
 * Copyright (c) 2004-2010, Sun Microsystems, Inc., Kohsuke Kawaguchi,
 * Tom Huybrechts, Yahoo!, Inc.
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


import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.MapMaker;
import testng.util.Util;

import java.util.*;
import java.util.logging.Logger;

/**
 * Base class for all test result objects.
 * For compatibility with code that expects this class to be in hudson.tasks.junit,
 * we've created a pure-abstract class, hudson.tasks.junit.TestObject. That
 * stub class is deprecated; instead, people should use this class.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class TestObject {

    private static final Logger LOGGER = Logger.getLogger(TestObject.class.getName());
    private volatile transient String id;


    /**
     * Reverse pointer of {@link TabulatedResult#getChildren()}.
     */
    public abstract TestObject getParent();


    public final String getId() {
        if (id == null) {
            StringBuilder buf = new StringBuilder();
            buf.append(getSafeName());

            TestObject parent = getParent();
            if (parent != null) {
                String parentId = parent.getId();
                if ((parentId != null) && (parentId.length() > 0)) {
                    buf.insert(0, '/');
                    buf.insert(0, parent.getId());
                }
            }
            id = buf.toString();
        }
        return id;
    }

    /**
     * Returns url relative to TestResult
     */

    public String getUrl() {
        return '/' + getId();
    }

    /**
     * Returns the top level test result data.
     *
     * @deprecated This method returns a JUnit specific class. Use
     * {@link #getTopLevelTestResult()} instead for a more general interface.
     */

    public TestResult getTestResult() {
        TestObject parent = getParent();

        return (parent == null ? null : getParent().getTestResult());
    }

    /**
     * Returns the top level test result data.
     */
    public TestResult getTopLevelTestResult() {
        TestObject parent = getParent();

        return (parent == null ? null : getParent().getTopLevelTestResult());
    }


    /**
     * Time took to run this test. In seconds.
     */
    public abstract float getDuration();

    /**
     * Returns the string representation of the {@link #getDuration()}, in a
     * human readable format.
     */
    @JSONField(serialize = false)
    public String getDurationString() {
        return Util.getTimeSpanString((long) (getDuration() * 1000));
    }


    public String getDescription() {
        return "";
    }


    public void setDescription(String description) {

    }


    /**
     * Gets the name of this object.
     */

    public/* abstract */ String getName() {
        return "";
    }

    /**
     * Gets the full name of this object.
     *
     * @since 1.594
     */
    public String getFullName() {
        StringBuilder sb = new StringBuilder(getName());
        if (getParent() != null) {
            sb.insert(0, " : ");
            sb.insert(0, getParent().getFullName());
        }
        return sb.toString();
    }


    /**
     * Gets the version of {@link #getName()} that's URL-safe.
     */

    public String getSafeName() {
        return safe(getName());
    }


    public String getSearchUrl() {
        return getSafeName();
    }

    /**
     * #2988: uniquifies a {@link #getSafeName} amongst children of the parent.
     */
    protected final String uniquifyName(Collection<? extends TestObject> siblings, String base) {
        synchronized (UNIQUIFIED_NAMES) {
            String uniquified = base;
            Map<TestObject, Void> taken = UNIQUIFIED_NAMES.get(base);
            if (taken == null) {
                taken = new WeakHashMap<TestObject, Void>();
                UNIQUIFIED_NAMES.put(base, taken);
            } else {
                Set<TestObject> similars = new HashSet<TestObject>(taken.keySet());
                similars.retainAll(new HashSet<TestObject>(siblings));
                if (!similars.isEmpty()) {
                    uniquified = base + '_' + (similars.size() + 1);
                }
            }
            taken.put(this, null);
            return uniquified;
        }
    }

    private static final Map<String, Map<TestObject, Void>> UNIQUIFIED_NAMES = new MapMaker().makeMap();

    /**
     * Replaces URL-unsafe characters.
     */
    public static String safe(String s) {
        // this still seems to be a bit faster than a single replace with regexp
        return s.replace('/', '_').replace('\\', '_').replace(':', '_').replace('?', '_').replace('#', '_').replace('%', '_');

        // Note: we probably should some helpers like Commons URIEscapeUtils here to escape all invalid URL chars, but then we
        // still would have to escape /, ? and so on
    }

    /**
     * Gets the total number of passed tests.
     */
    public abstract int getPassCount();

    /**
     * Gets the total number of failed tests.
     */
    public abstract int getFailCount();

    /**
     * Gets the total number of skipped tests.
     */
    public abstract int getSkipCount();

    /**
     * Gets the total number of tests.
     */

    public int getTotalCount() {
        return getPassCount() + getFailCount() + getSkipCount();
    }


    private static final long serialVersionUID = 1L;


    /**
     * Gets the name of this object.
     */

    /**
     * Gets the total number of passed tests.
     */


//    public abstract Object getDynamic(String token, StaplerRequest req,
//			StaplerResponse rsp);
//
//    public abstract  HttpResponse doSubmitDescription(
//			@QueryParameter String description) throws IOException,
//			ServletException;
}
