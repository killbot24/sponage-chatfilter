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

import java.io.IOException;
import java.util.Optional;

/**
 * Created by tjbur on 22/06/2020.
 */
public class unmute implements CommandExecutor {
    Logic log=new Logic();
    data dat= new data();
    datastorage store = new datastorage();
    @Override
    public CommandResult execute(CommandSource commandSource, CommandContext commandContext) throws CommandException {


            Optional<User> user = commandContext.<User>getOne("player");
            String player = user.get().getName();
        if (commandSource instanceof ConsoleSource) {
            try {
                store.unmutec(player);
                store.logunmute("console",player);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } if (commandSource instanceof Player) {
            Player players = (Player) commandSource;
            try {

                store.unmute(player, players);
                store.logunmute(players.getName(),player);

            } catch (IOException e) {
                e.printStackTrace();
            }}


        return CommandResult.success();
    }
}
