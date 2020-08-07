package killbot24.com.chat.Commands;

import killbot24.com.chat.Chat;
import killbot24.com.chat.Logic;
import killbot24.com.chat.config;
import killbot24.com.chat.data;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import java.util.List;
import java.util.function.Function;

/**
 * Created by tjbur on 10/06/2020.
 */
public class reload  extends Chat implements CommandExecutor {
        Logic log=new Logic();
        data dat= new data();
    private String[] Blocked;
    @Override
    public CommandResult execute(CommandSource commandSource, CommandContext commandContext) throws CommandException {
        Boolean bla=true;// to stop error
        config.load();




        String message= config.getNode("Mute").getValue().toString();
       // getLogger().info(message);
        dat.setMutemessage(message);

        //  this.logger.info(Blocked[0].toString()+" What ever array is. and lenght "+Blocked.length);

        Function<Object, String> stringTransformer = new Function<Object,String>() {
            public String apply(Object input) {
                if (input instanceof String) {
                    return (String) input;
                } else {
                    return null;
                }
            }
        };
        //  List<String> a = rootNode.getNode("Blocked").getList(stringTransformer);
        String a = (String) config.getNode("Blocked").getValue();
        //  Blocked=  new String[a.size()];
        // Blocked= a.toArray(this.Blocked);



        /* delimiter */
        String delimiter = ",";
        String b= a.replace("[","");
        String c =b.replace("]","");
        String d=c.replace(" ","");
        /* given string will be split by the argument delimiter provided. */
        String[] tempArray = d.split(delimiter);
       // getLogger().info(tempArray[1]);
        dat.setBlocked(tempArray);
        getLogger().info("Reloaded");
        return CommandResult.success();

    }
}
