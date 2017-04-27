package com.github.maxstupo.jannocli;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.maxstupo.jannocli.annotation.Command;
import com.github.maxstupo.jannocli.cli.IPrint;

/**
 * @author Maxstupo
 *
 */
public class CommandContainer {

    private final Object owner;

    private final String keyword;
    private final String[] aliases;

    private final Map<String, AnnotationCommand> commands = new HashMap<>();

    public CommandContainer(Object owner, Command info) {
        this.owner = owner;
        this.keyword = info.value();
        this.aliases = info.aliases();
    }

    public void displayHelp(IPrint ps) {

        String title = " [ Command Help] ";

        int maxWidth = 0;
        maxWidth = Math.max(maxWidth, getRootCommand().getName().length());
        maxWidth = Math.max(maxWidth, ("Description: " + getRootCommand().getDescription()).length());
        maxWidth = Math.max(maxWidth, getKeyword().length());
        maxWidth = Math.max(maxWidth, String.join(",", getAliases()).length());

        maxWidth += 2;
        // System.out.println("CommandContainer.displayHelp()");
        ps.println(Util.strRepeat("-", maxWidth / 2 - title.length() / 2) + title + Util.strRepeat("-", maxWidth / 2 - title.length() / 2));
        ps.println("Name: " + getRootCommand().getName());
        ps.println("Description: " + getRootCommand().getDescription());
        ps.println("Keyword: " + getKeyword());
        ps.println("Aliases: " + String.join(",", getAliases()));
        ps.println("");
        ps.println("Sub-commands:");
        for (Entry<String, AnnotationCommand> entry : commands.entrySet()) {
            if (entry.getKey() != null)
                ps.println("  - " + entry.getValue().getUsage());
        }

    }

    public boolean invoke(IPrint ps, String[] parameters) {
        return getRootCommand().invoke(ps, parameters);
    }

    public AnnotationCommand getCommand(String keyword) {
        AnnotationCommand command = commands.get(keyword);
        if (command != null)
            return command;

        for (Entry<String, AnnotationCommand> entry : commands.entrySet()) {
            String[] aliases = entry.getValue().getAliases();

            for (String alias : aliases) {
                if (keyword.equals(alias))
                    return entry.getValue();
            }
        }
        return null;
    }

    public void setRootCommand(AnnotationCommand command) {
        commands.put(null, command);
    }

    public boolean addCommand(AnnotationCommand command) {
        if (commands.containsKey(command.getKeyword()))
            return false;

        commands.put(command.getKeyword(), command);
        return true;
    }

    public AnnotationCommand getRootCommand() {
        return commands.get(null);
    }

    public boolean hasRootCommand() {
        return commands.containsKey(null);
    }

    public Map<String, AnnotationCommand> getCommands() {
        return Collections.unmodifiableMap(commands);
    }

    public Object getOwner() {
        return owner;
    }

    public String getKeyword() {
        return keyword;
    }

    public String[] getAliases() {
        return aliases;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(aliases);
        result = prime * result + ((commands == null) ? 0 : commands.hashCode());
        result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CommandContainer other = (CommandContainer) obj;
        if (!Arrays.equals(aliases, other.aliases))
            return false;
        if (commands == null) {
            if (other.commands != null)
                return false;
        } else if (!commands.equals(other.commands))
            return false;

        if (keyword == null) {
            if (other.keyword != null)
                return false;
        } else if (!keyword.equals(other.keyword))
            return false;

        return true;
    }

}
