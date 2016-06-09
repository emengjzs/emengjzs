package cn.edu.nju.software.jzs.service.base.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emengjzs on 2016/4/8.
 */
public class ExecuteResult {

    private Process process;

    private boolean success;

    private BufferedReader logout;
    private BufferedReader error;

    private List<ReadLineListener> readLineListener = new ArrayList<ReadLineListener>();

    private List<CommandFinishListener> commandFinishListeners = new ArrayList<>();

    private List<BeforeRunListener> beforeRunListeners = new ArrayList<>();
    public ExecuteResult() {


    }


    public ExecuteResult(Process process) {
        this.process = process;
        initLogout();


    }

    public void setProcess(Process process) {
        this.process = process;
        initLogout();
    }

    public void getResult() {
        try {
            System.out.println("=============try to End");
            int i = process.waitFor();
            System.out.println("============= End up");
            success = true;
            for (CommandFinishListener commandFinishListener : commandFinishListeners) {
                commandFinishListener.onFinish(this);
            }
            System.out.println("============= End");
        } catch (InterruptedException e) {
            e.printStackTrace();
            exceptionHandle(e);
        } finally {
            if (process != null) {
                process.destroy();
                System.out.println("============= Destory ");
            }
        }
    }

    private void initLogout() {
        try {
        logout = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"));

            error = new BufferedReader(new InputStreamReader(process.getErrorStream(), "gbk"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void exceptionHandle(Exception e) {
        success = false;
    }


    public void destroy() {
        process.destroy();

    }

    public BufferedReader getLogout() {
        return logout;
    }

    public BufferedReader getError() {
        return error;
    }

    public ExecuteResult register(BeforeRunListener listener) {
        beforeRunListeners.add(listener);
        return this;
    }

    public ExecuteResult register(ReadLineListener listener) {
        this.readLineListener.add(listener);
        return this;
    }

    public ExecuteResult register(CommandFinishListener listener) {
        this.commandFinishListeners.add(listener);
        return this;
    }
    // ...
    public void run(CommandLogFetcher commandLogFetcher) throws IOException {
        commandLogFetcher.fetch(this, readLineListener, beforeRunListeners);
    }
}
