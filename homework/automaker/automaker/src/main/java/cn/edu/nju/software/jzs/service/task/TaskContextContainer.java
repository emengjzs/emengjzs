package cn.edu.nju.software.jzs.service.task;

import cn.edu.nju.software.jzs.service.TaskRunContext;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by emengjzs on 2016/4/6.
 */
@Component
public class TaskContextContainer {

    private static final int REMOVE_CONTEXT_DELAY = 2500;
    private Map<Long, TaskRunContext> center;

    Timer timer = new Timer();

    public TaskContextContainer() {
        center = new ConcurrentHashMap<>();
    }

    public void submit(TaskRunContext tc) {
        center.put(tc.getRunHistory().getId(), tc);
    }


    public void finish(final TaskRunContext tc) {
        TimerTask removeContextTimer = new TimerTask() {
            @Override
            public void run() {
                center.remove(tc.getRunHistory().getId());
                this.cancel();
            }
        };
        timer.schedule(removeContextTimer, REMOVE_CONTEXT_DELAY);
    }

    public TaskRunContext getTaskRunContext(Long runHistoryId) {
        return this.center.get(runHistoryId);
    }

    @PreDestroy
    void destroy() {
        this.timer.cancel();
    }
}



