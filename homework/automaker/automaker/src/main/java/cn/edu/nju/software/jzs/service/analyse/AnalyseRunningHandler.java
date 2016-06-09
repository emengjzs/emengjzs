package cn.edu.nju.software.jzs.service.analyse;

import cn.edu.nju.software.jzs.service.base.cmd.ExecuteResult;
import cn.edu.nju.software.jzs.service.base.cmd.ReadLineListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * Created by emengjzs on 2016/4/30.
 */
public class AnalyseRunningHandler implements ReadLineListener {
    final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CountDownLatch threadSignal = new CountDownLatch(1);
    private final CountDownLatch endSignal = new CountDownLatch(1);
    private final int TIME_OUT = 5;
    private volatile long timestamp;
    private List<MethodInfo> list = new LinkedList<>();
    private boolean ready = false;
    private final String START_SINGAL = "START";
    private ExecuteResult executeResult;


    public AnalyseRunningHandler(ExecuteResult executeResult) {
        this.executeResult = executeResult;
        executeResult.register(this);
    }


    @Override
    public void onReadLine(String str) {
        log.debug("+" + str);
        timestamp = System.currentTimeMillis();
        if (ready) {
            list.add(JSON.parseObject(str, MethodInfo.class));
        }
        else if (str.equals(START_SINGAL)) {
            log.debug("Start get!!");
            ready = true;
            threadSignal.countDown();
            startTimer();
        }
    }

    public void waitToBeginHttpRequest() throws InterruptedException {
        log.debug("wait for command run to catch signal");
        threadSignal.await();
        log.debug("ready！");
    }

    public void waitToFinishFetchLog() throws InterruptedException {
        log.debug("wait for end signal");
        endSignal.await();
        log.debug("ready！");
    }

    public List<MethodInfo> endRunning() throws InterruptedException {
        waitToFinishFetchLog();
        executeResult.destroy();
        return list;
    }

    Timer timer;

    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if ((System.currentTimeMillis() - timestamp) > TIME_OUT * 1000) {
                    endSignal.countDown();
                    this.cancel();
                    timer.cancel();

                }
            }
        }, 0, TIME_OUT * 1000);
    }


}
