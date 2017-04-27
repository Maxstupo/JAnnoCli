package com.github.maxstupo.jannocli.cli;

/**
 * This interface allows for a custom implementation of {@link #print(String)} and {@link #println(String)} used by all commands and their logic.
 * 
 * @author Maxstupo
 */
public interface IPrint {

    /**
     * Prints the given string and goes to the next line.
     * 
     * @param line
     *            the string to print.
     */
    void println(String line);

    /**
     * Prints the given string.
     * 
     * @param line
     */
    void print(String line);

}
