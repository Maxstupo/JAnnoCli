package com.github.maxstupo.jannocli;

import com.github.maxstupo.jannocli.cli.IPrint;

/**
 * @author Maxstupo
 *
 */
public class ExecutedCommand {

    public final IPrint cli;
    public final Parameters parameters;
    public boolean displayHelp;

    public ExecutedCommand(IPrint cli, Parameters parameters) {
        this.cli = cli;
        this.parameters = parameters;
    }
}
