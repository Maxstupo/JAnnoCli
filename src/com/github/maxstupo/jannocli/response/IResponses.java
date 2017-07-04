package com.github.maxstupo.jannocli.response;

/**
 * This interface provides methods for message responses used within the command-line.
 * 
 * @author Maxstupo
 */
public interface IResponses {

    /**
     * The message for an unknown command. Use {0} for the command that is unknown.
     * 
     * @return the message for an unknown command.
     */
    String getUnknownCommandResponse();

    /**
     * The message for nothing entered into the command line.
     * 
     * @return the message for nothing entered into the command line.
     */
    String getNothingEnteredResponse();

}
