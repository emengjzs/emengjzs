package cn.edu.nju.software.jzs.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by emengjzs on 2016/4/30.
 */
@Service
public class MiddleService {

    @Autowired
    ComplexService complexService;

    void service() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void service2() {
        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        complexService.service();
    }
}



