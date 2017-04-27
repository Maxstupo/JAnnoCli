package com.github.maxstupo.jannocli.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Maxstupo
 *
 */
public abstract class Cli implements Runnable {

    private final InputStream is;
    private Thread thread;
    private boolean isRunning = false;
    protected final IPrint ps;

    public Cli() {
        this(System.in, new SystemPrint());
    }

    public Cli(InputStream is, IPrint ps) {
        this.is = is;
        this.ps = ps;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            while (isRunning)
                processInput(ps, br.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void processInput(IPrint ps, String line);

    public boolean start() {
        return start(null);
    }

    public boolean start(String threadName) {
        if (thread != null || isRunning)
            return false;

        isRunning = true;

        thread = new Thread(this);
        if (threadName != null)
            thread.setName(threadName);
        thread.start();
        return true;
    }

    public boolean stop() {
        if (thread == null || !isRunning)
            return false;

        isRunning = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread = null;
        return true;
    }

    public IPrint getOut() {
        return ps;
    }
}
