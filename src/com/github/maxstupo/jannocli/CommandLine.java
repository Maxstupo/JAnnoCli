package com.github.maxstupo.jannocli;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.maxstupo.jannocli.annotation.Command;
import com.github.maxstupo.jannocli.annotation.ParamAliases;
import com.github.maxstupo.jannocli.annotation.ParamDescription;
import com.github.maxstupo.jannocli.annotation.ParamTypes;
import com.github.maxstupo.jannocli.cli.Cli;
import com.github.maxstupo.jannocli.cli.IPrint;
import com.github.maxstupo.jannocli.cli.SystemPrint;
import com.github.maxstupo.jannocli.response.DefaultResponses;
import com.github.maxstupo.jannocli.response.IResponses;

/**
 * @author Maxstupo
 *
 */
@Command(value = "help", aliases = "?")
public class CommandLine extends Cli {

    private final Map<String, CommandContainer> commands = new HashMap<>();

    private IResponses responses = new DefaultResponses();

    public CommandLine() {
        this(System.in, new SystemPrint());
    }

    public CommandLine(InputStream is, IPrint ps) {
        super(is, ps);
        register(this);
    }

    @Command(description = "Displays help", name = "Help")
    private void displayHelp(ExecutedCommand cmd) {

        int usageWidth = 0;
        int descWidth = 0;
        for (Entry<String, CommandContainer> entry : commands.entrySet()) {
            for (Entry<String, AnnotationCommand> entry2 : entry.getValue().getCommands().entrySet()) {
                if (entry2.getValue().isHidden())
                    continue;
                usageWidth = Math.max(usageWidth, entry2.getValue().getUsage().length());
                descWidth = Math.max(descWidth, entry2.getValue().getDescription().length());
            }
        }
        usageWidth += 2;

        final int totalWidth = usageWidth + descWidth + 5;
        final String helpText = " [ Help ] ";

        // Print help title.
        cmd.cli.println(Util.strRepeat("-", totalWidth / 2 - helpText.length() / 2) + helpText + Util.strRepeat("-", totalWidth / 2 - helpText.length() / 2));

        for (Entry<String, CommandContainer> entry : commands.entrySet()) {
            for (Entry<String, AnnotationCommand> entry2 : entry.getValue().getCommands().entrySet()) {
                if (entry2.getValue().isHidden())
                    continue;
                cmd.cli.println(String.format(" %-" + usageWidth + "s - %s", entry2.getValue().getUsage(), entry2.getValue().getDescription()));
            }
        }
    }

    public void register(Object obj) throws IllegalArgumentException {
        Command commandInfo = obj.getClass().getAnnotation(Command.class);

        if (commandInfo == null)
            throw new IllegalArgumentException("The given class must have the Command annotation - " + obj.getClass().getName());

        if (commandInfo.value().isEmpty())
            throw new IllegalArgumentException("Command anotation must have value() set for object -" + obj.getClass().getName());

        CommandContainer container = new CommandContainer(obj, commandInfo);

        for (Method method : obj.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (!AnnotationCommand.hasCorrectParams(method))
                continue;

            Command subCommandInfo = method.getAnnotation(Command.class);
            if (subCommandInfo == null)
                continue;

            ParamTypes filter = method.getAnnotation(ParamTypes.class);
            ParamAliases aliases = method.getAnnotation(ParamAliases.class);
            ParamDescription descriptions = method.getAnnotation(ParamDescription.class);

            AnnotationCommand command = new AnnotationCommand(container, method, subCommandInfo, filter, aliases, descriptions);

            if (subCommandInfo.value().isEmpty() && subCommandInfo.aliases().length == 1 && subCommandInfo.aliases()[0].isEmpty()) {
                if (!container.hasRootCommand()) {
                    container.setRootCommand(command);
                } else {
                    throw new IllegalArgumentException("Root command for " + obj.getClass().getName() + " already set!");
                }
            } else {
                if (!container.addCommand(command))
                    throw new IllegalArgumentException("A sub-command for " + obj.getClass().getName() + " already has the keyword - " + command.getKeyword());
            }
        }

        if (!container.hasRootCommand())
            throw new IllegalArgumentException("All registered objects must have a root command - " + obj.getClass().getName());
        commands.put(commandInfo.value(), container);
    }

    public void parse(String line) {
        processInput(getOut(), line);
    }

    @Override
    protected void processInput(IPrint ps, String line) {
        String[] tokens = Util.split(line);
        // System.out.println("Tokens: " + Arrays.toString(tokens));

        if (tokens.length > 0) {
            CommandContainer container = getCommandContainer(tokens[0]);

            if (container != null) {
                if (tokens.length > 1) {

                    if (tokens[1].equals("?")) {
                        container.displayHelp(ps);
                    } else {

                        AnnotationCommand command = container.getCommand(tokens[1]);
                        if (command != null) {
                            if (tokens.length > 2 && tokens[2].equals("?")) {
                                command.displayHelp(ps);
                            } else {
                                if (!command.invoke(ps, Util.shiftArray(2, tokens)))
                                    command.displayHelp(ps);
                            }
                        } else {
                            if (!container.invoke(ps, Util.shiftArray(1, tokens)))
                                container.displayHelp(ps);
                        }
                    }
                } else {
                    if (!container.invoke(ps, Util.shiftArray(1, tokens)))
                        container.displayHelp(ps);
                }
            } else {
                ps.println(Util.replaceReferences(responses.getUnknownCommandResponse(), tokens[0]));
            }
        } else {
            ps.println(responses.getNothingEnteredResponse());
        }
    }

    public CommandContainer getCommandContainer(String keyword) {
        CommandContainer container = commands.get(keyword);
        if (container != null)
            return container;

        for (Entry<String, CommandContainer> entry : commands.entrySet()) {
            String[] aliases = entry.getValue().getAliases();

            for (String alias : aliases) {
                if (keyword.equals(alias))
                    return entry.getValue();
            }
        }
        return null;
    }

    public IResponses getResponses() {
        return responses;
    }

    public void setResponses(IResponses responses) {
        this.responses = responses;
    }

}
