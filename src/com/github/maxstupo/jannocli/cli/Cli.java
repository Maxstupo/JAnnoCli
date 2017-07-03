package com.github.maxstupo.jannocli.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This abstract class simplifies listening from an {@link InputStream}. This is done by calling {@link #processInput(IPrint, String)} whenever a line
 * is printed to the stream.
 * 
 * @author Maxstupo
 */
public abstract class Cli implements Runnable {

    private final InputStream is;
    private Thread thread;
    private boolean isRunning = false;

    // TODO: Replace with OutputStream.
    @SuppressWarnings("javadoc")
    protected final IPrint ps;

    /**
     * Create a new {@link Cli} object using {@link System#in} and {@link SystemPrint}.
     */
    public Cli() {
        this(System.in, new SystemPrint());
    }

    /**
     * Create a new {@link Cli}.
     * 
     * @param is
     *            the input stream to listen.
     * @param ps
     *            an output implementation.
     */
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

    /**
     * Called whenever input is read.
     * 
     * @param ps
     *            the output implementation.
     * @param line
     *            the input read.
     */
    protected abstract void processInput(IPrint ps, String line);

    /**
     * Start the thread to listen for stream input.
     * 
     * @return true if the thread started.
     */
    public boolean start() {
        return start(null);
    }

    /**
     * Start the thread to listen for stream input.
     * 
     * @param threadName
     *            the name of thread, or null to assign a name.
     * @return true if the thread started.
     */
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

    /**
     * Stops the thread listening for input.
     * 
     * @return true if the thread was stopped.
     */
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

    @SuppressWarnings("javadoc")
    public IPrint getOut() {
        return ps;
    }
}
