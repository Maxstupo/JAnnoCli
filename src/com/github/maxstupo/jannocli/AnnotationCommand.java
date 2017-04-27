package com.github.maxstupo.jannocli;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.github.maxstupo.jannocli.annotation.Command;
import com.github.maxstupo.jannocli.annotation.ParamAliases;
import com.github.maxstupo.jannocli.annotation.ParamDescription;
import com.github.maxstupo.jannocli.annotation.ParamTypes;
import com.github.maxstupo.jannocli.cli.IPrint;

/**
 * @author Maxstupo
 *
 */
public class AnnotationCommand {

    private final CommandContainer container;
    private final Method method;

    private final Class<?>[] paramTypes;
    private final String[] paramAliases;
    private final String[] paramDescriptions;

    private final String keyword;
    private final String[] aliases;
    private final String name;
    private final String description;

    public AnnotationCommand(CommandContainer container, Method method, Command info, ParamTypes filter, ParamAliases paramAliases, ParamDescription paramDescriptions) {
        this.container = container;
        this.method = method;
        this.name = info.name();
        this.keyword = info.value();
        this.description = info.description();
        this.aliases = info.aliases();
        this.paramDescriptions = (paramDescriptions != null) ? paramDescriptions.value() : new String[0];
        this.paramTypes = (filter != null) ? filter.value() : new Class[0];
        this.paramAliases = (paramAliases != null) ? paramAliases.value() : new String[0];
    }

    public boolean invoke(IPrint ps, String[] parameters) {
        if (!Parameters.check(parameters, paramTypes)) // TODO: Add optional flag for parameters
            return false;

        Parameters params = Parameters.parse(parameters, paramTypes, paramAliases);
        ExecutedCommand command = new ExecutedCommand(ps, params);

        try {
            method.invoke(container.getOwner(), command);
            if (command.displayHelp)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean hasCorrectParams(Method m) {
        Class<?>[] types = m.getParameterTypes();

        if (types.length == 1)
            return types[0].isAssignableFrom(ExecutedCommand.class);
        return false;
    }

    public String getKeyword() {
        return keyword;
    }

    public CommandContainer getContainer() {
        return container;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String[] getParamDescriptions() {
        return paramDescriptions;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void displayHelp(IPrint ps) {
        String title = " [ Command Help] ";

        int maxWidth = 0;
        maxWidth = Math.max(maxWidth, getName().length());
        maxWidth = Math.max(maxWidth, ("Description: " + getDescription()).length());
        maxWidth = Math.max(maxWidth, getKeyword().length());
        maxWidth = Math.max(maxWidth, String.join(",", getAliases()).length());
        maxWidth = Math.max(maxWidth, getUsage().length());

        if (paramDescriptions != null) {
            for (int i = 0; i < paramDescriptions.length; i++) {
                String value;
                if (paramAliases != null && i < paramAliases.length) {
                    value = paramAliases[i];
                } else {
                    value = i + "";
                }
                maxWidth = Math.max(maxWidth, ("  <" + value + ">" + " - " + paramDescriptions[i]).length());
            }
        }

        maxWidth += 2;

        ps.println(Util.strRepeat("-", maxWidth / 2 - title.length() / 2) + title + Util.strRepeat("-", maxWidth / 2 - title.length() / 2));
        ps.println("Name: " + getName());
        ps.println("Description: " + getDescription());
        ps.println("Keyword: " + getKeyword());
        ps.println("Aliases: " + String.join(",", getAliases()));
        ps.println("Usage: " + getUsage());

        if (paramDescriptions != null) {
            ps.println("Parameters:");
            for (int i = 0; i < paramDescriptions.length; i++) {
                String value;
                if (paramAliases != null && i < paramAliases.length) {
                    value = paramAliases[i];
                } else {
                    value = i + "";
                }
                ps.println("  <" + value + ">" + " - " + paramDescriptions[i]);
            }
        }
    }

    public String getUsage() {
        String params = "";
        for (int i = 0; i < Math.max(paramTypes != null ? paramTypes.length : 0, paramAliases != null ? paramAliases.length : 0); i++) {
            String value;
            if (paramAliases != null && i < paramAliases.length) {
                value = paramAliases[i];
            } else {
                value = i + "";
            }
            params += " <" + value + ">";

        }
        return container.getKeyword() + " " + getKeyword() + "" + params;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(aliases);
        result = prime * result + ((container == null) ? 0 : container.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + Arrays.hashCode(paramAliases);
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
        AnnotationCommand other = (AnnotationCommand) obj;
        if (!Arrays.equals(aliases, other.aliases))
            return false;
        if (container == null) {
            if (other.container != null)
                return false;
        } else if (!container.equals(other.container))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (keyword == null) {
            if (other.keyword != null)
                return false;
        } else if (!keyword.equals(other.keyword))
            return false;
        if (method == null) {
            if (other.method != null)
                return false;
        } else if (!method.equals(other.method))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (!Arrays.equals(paramAliases, other.paramAliases))
            return false;
        return true;
    }

}
