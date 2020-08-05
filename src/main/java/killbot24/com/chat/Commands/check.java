package killbot24.com.chat.Commands;

import killbot24.com.chat.Logic;
import killbot24.com.chat.data;
import killbot24.com.chat.datastorage;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.filter.cause.Root;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by tjbur on 22/06/2020.
 */
public class check implements CommandExecutor {
    Logic log=new Logic();
    data dat= new data();
    datastorage store = new datastorage();
    @Override
    public CommandResult execute(@Root CommandSource commandSource,CommandContext commandContext) throws CommandException {

        Optional<User> user = commandContext.<User>getOne("player");
        if (commandSource instanceof ConsoleSource) {
            try {
                store.readreportc(user.get().getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }  if (commandSource instanceof Player) {
            Player players = (Player) commandSource;

            try {
                store.readreport(user.get().getName(), players);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return CommandResult.success();
    }
}
