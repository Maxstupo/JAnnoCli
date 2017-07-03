package com.github.maxstupo.jannocli;

import com.github.maxstupo.jannocli.cli.IPrint;

/**
 * This class is a wrapper given to the command logic (method) of an executed command.
 * 
 * @author Maxstupo
 */
public class ExecutedCommand {

    /** The implementation for the console. Useful for command logic to share a common output. */
    public final IPrint cli;

    /** The parsed parameters of the executed command. */
    public final Parameters parameters;

    /**
     * If set true during execution of command logic, the help of this command will be displayed through the {@link #cli} object.
     * <p>
     * Usually set true if parameters are the incorrect type or are out-of-range.
     */
    public boolean displayHelp;

    /**
     * Create a new {@link ExecutedCommand} object.
     * 
     * @param cli
     *            the output implementation.
     * @param parameters
     *            the parsed parameters.
     */
    public ExecutedCommand(IPrint cli, Parameters parameters) {
        this.cli = cli;
        this.parameters = parameters;
    }
}
