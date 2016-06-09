package cn.edu.nju.software.jzs.sample.service;

import org.springframework.stereotype.Service;

/**
 * Created by emengjzs on 2016/4/30.
 */
@Service
public class ComplexService {

    public void service()  {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
