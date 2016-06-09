package testng.results;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Base class that takes care of all the common functionality of the different kinds of
 * test results.
 */
@SuppressWarnings("serial")
public abstract class BaseResult extends TabulatedResult implements ModelObject, Serializable {


    //name of this result
    protected final String name;
    //parent result for this result
    protected BaseResult parent;

    public BaseResult(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    @JSONField(serialize = false)
    public BaseResult getParent() {
        return parent;
    }

    public void setParent(BaseResult parent) {
        this.parent = parent;
    }


    @Override
    public String getTitle() {
        return getName();
    }

    public String getDisplayName() {
        return getName();
    }


}
