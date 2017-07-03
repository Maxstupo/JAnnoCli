package example.com.github.maxstupo.jannocli;

import com.github.maxstupo.jannocli.CommandLine;
import com.github.maxstupo.jannocli.ExecutedCommand;
import com.github.maxstupo.jannocli.annotation.Command;
import com.github.maxstupo.jannocli.annotation.ParamAliases;
import com.github.maxstupo.jannocli.annotation.ParamDescription;
import com.github.maxstupo.jannocli.annotation.ParamTypes;

/**
 * @author Maxstupo
 *
 */
public class Main {

    public static void main(String[] args) {
        try {
            CommandLine commandLine = new CommandLine();
            commandLine.register(new ClientCommand());

            commandLine.start();
            commandLine.parse("?");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Command(value = "client", aliases = "clients") // description and name are ignored for root command on class.
    public static class ClientCommand {

        @Command(name = "List", description = "Display a list of clients connected.")
        public void list(ExecutedCommand command) {
            command.cli.println("Listing");
        }

        @Command(name = "Status", value = "status", aliases = "info", description = "Displays the client printer status.")
        @ParamTypes({ Level.class })
        @ParamDescription({ "The status level." })
        public void status(ExecutedCommand command) {
            System.out.println(command.parameters.get(0, Level.class));
        }

        @Command(name = "Print", value = "print", description = "Print the given gcode file with the selected printer.")
        @ParamTypes({ String.class })
        @ParamAliases({ "file" })
        @ParamDescription({ "The gcode file to print." })
        public void print(ExecutedCommand command) {
            System.out.println(command.parameters.get("file"));
        }

        @Command(name = "Select", value = "select", description = "Select a printer.")
        @ParamTypes({ Integer.class })
        @ParamAliases({ "id" })
        @ParamDescription({ "The id of the client." })
        public void select(ExecutedCommand command) {

        }

    }

    public static enum Level {
        MINIMAL,
        NORMAL,
        DETAILED;
    }
}
