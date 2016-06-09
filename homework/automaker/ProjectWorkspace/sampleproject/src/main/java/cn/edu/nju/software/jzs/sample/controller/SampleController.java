package cn.edu.nju.software.jzs.sample.controller;

import cn.edu.nju.software.jzs.sample.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by emengjzs on 2016/4/4.
 */
@RestController
public class SampleController {

    @Autowired
    SampleService sampleService;

    @RequestMapping("/index")
    public Object index() {
        return sampleService.service();
    }
}
