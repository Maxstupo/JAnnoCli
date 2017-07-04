package com.github.maxstupo.jannocli.response;

import com.github.maxstupo.jannocli.CommandLine;

/**
 * The default responses used in {@link CommandLine}.
 * 
 * @author Maxstupo
 */
public class DefaultResponses implements IResponses {

    @Override
    public String getUnknownCommandResponse() {
        return "No command called '{0}' found!";
    }

    @Override
    public String getNothingEnteredResponse() {
        return "For help with commands type '?' or 'help'";
    }
}
