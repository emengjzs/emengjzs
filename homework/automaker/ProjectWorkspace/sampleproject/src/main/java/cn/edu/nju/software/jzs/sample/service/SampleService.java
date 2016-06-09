package cn.edu.nju.software.jzs.sample.service;

import cn.edu.nju.software.jzs.sample.vo.HelloVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by emengjzs on 2016/4/30.
 */
@Service
public class SampleService {

    @Autowired
    MiddleService m;

    @Autowired
    ComplexService complexService;

    public HelloVo service() {
        m.service();
        complexService.service();
        m.service2();
        HelloVo helloVo = new HelloVo();
        helloVo.setName("ddd");
        helloVo.setId(11L);
        return helloVo;
    }
}
