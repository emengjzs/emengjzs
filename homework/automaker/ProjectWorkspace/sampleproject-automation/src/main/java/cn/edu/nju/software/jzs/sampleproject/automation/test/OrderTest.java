package cn.edu.nju.software.jzs.sampleproject.automation.test;

import cn.edu.nju.software.jzs.sampleproject.automation.config.URLSet;
import com.alibaba.fastjson.JSONObject;
import jzs.test.base.context.AbstractDataContextTest;
import jzs.test.base.context.Data;
import jzs.test.base.context.DataSet;
import org.testng.Assert;

/* 使用用例， 需继承AbstractDataContextTest类 */
public class OrderTest extends AbstractDataContextTest {

    /*注入app数据为JavaBean至方法中*/
    public void testDataContextWithBean1(@Data("app") TestObject req) {
        Assert.assertEquals(req.getId(), 1212);
    }

    @DataSet(  /*数据测试集*/
        data = { @Data("order_date_test1"), @Data("order_date_test2") },
        baseOn = "order_base"  /*数据模板*/
    )
    // 测试订单提交时，若结束时间早于开始时间则返回失败信息
    public void testOrderDate(JSONObject req) {
        Assert.assertNotNull(req);
        Assert.assertFalse(
            httpService.postForJSONResponse(URLSet.ORDER_SUBMIT, req)
                .isSuccess()
        );
    }
}
