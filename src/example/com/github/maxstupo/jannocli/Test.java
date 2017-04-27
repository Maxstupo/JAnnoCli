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

@Command(value = "user", aliases = "list") // Name & description are ignored.
public class Test {

    public static void main(String[] args) {

        CommandLine cli = new CommandLine();
        cli.register(new Test());
        cli.start();
        cli.getCommandContainer("user").getCommand("add").invoke(cli.getOut(), new String[] { "Michael", "18" });

    }

    @Command(name = "List Users", description = "List all users dasd afasf asd a")
    public void listUsers(ExecutedCommand cmd) {
        cmd.cli.println("Printing all users...");
    }

    /**
     * @param ps
     * @param cmd
     */
    @Command(name = "Add User", value = "add", aliases = { "new" }, description = "Adds a new user.")
    @ParamTypes({ String.class, Integer.class })
    @ParamAliases({ "name", "age" })
    @ParamDescription({ "The name of the user", "The age of the user" })
    public void addUser(ExecutedCommand cmd) {

        if (cmd.parameters.getInt("age") < 18) {
            cmd.displayHelp = true;
        } else {
            cmd.cli.println("Adding user: " + cmd.parameters.get("name") + " age is " + cmd.parameters.getInt("age"));
        }
    }

}
