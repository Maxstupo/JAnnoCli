package com.github.maxstupo.jannocli.cli;

/**
 * This class is a basic implementation of {@link IPrint} using the standard {@link System#out} stream.
 * 
 * @author Maxstupo
 */
public class SystemPrint implements IPrint {

    @Override
    public void println(String msg) {
        System.out.println(msg);
    }

    @Override
    public void print(String msg) {
        System.out.print(msg);
    }

}
