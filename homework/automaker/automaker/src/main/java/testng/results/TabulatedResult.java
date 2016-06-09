package testng.results;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Collection;

/**
 * Created by emengjzs on 2016/4/14.
 */
public abstract class TabulatedResult extends TestResult {

    /**
     * Gets the child test result objects.
     *
     * @see TestObject#getParent()
     */
    public abstract Collection<? extends TestResult> getChildren();

    public abstract boolean hasChildren();

    @JSONField(serialize = false)
    public String getChildTitle() {
        return "";
    }
}
