package cn.edu.nju.software.jzs.service.base.cmd;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by emengjzs on 2016/4/8.
 */
@Service
public class CommandLogFetcher {


    @Async
    public Future<ExecuteResult> fetch(ExecuteResult result, List<ReadLineListener> listenerList, List<BeforeRunListener> beforeRunListeners) {
        String lineStr;
        BufferedReader logout = result.getLogout();
        AsyncResult<ExecuteResult> objectAsyncResult = new AsyncResult<>(result);
        for (BeforeRunListener beforeRunListener : beforeRunListeners) {
            beforeRunListener.onBegin();
        }
        try {
            while ((lineStr = logout.readLine()) != null) {
                for (ReadLineListener readLineListener : listenerList) {
                    readLineListener.onReadLine(lineStr);
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                logout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedReader error = result.getError();
        try {
            while ((lineStr = error.readLine()) != null) {
                System.out.println(lineStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                error.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        objectAsyncResult.addCallback(new ListenableFutureCallback<ExecuteResult>() {
            @Override
            public void onFailure(Throwable ex) {
            }

            @Override
            public void onSuccess(ExecuteResult result) {
                // result.getResult();
            }
        });
        return objectAsyncResult;
    }

}
