package killbot24.com.chat;

import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TranslatableText;
import org.spongepowered.api.text.format.TextColors;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

/**
 * Created by tjbur on 01/06/2020.
 */
public class Logic extends Chat {
    data dat = new data();
    Lan lan = new Lan();

    datastorage store = new datastorage();
    private String[] blocked;
    private String[] curse={"fuck","shit","cunt"};
    private String[] muted;
    public boolean Checkchat(Player player,String message,String type) {


        Boolean prevent = false;
      message=message.toLowerCase();

                muted=dat.getmute();
                try{
        for (int i = 0; i < muted.length; i++) {// checks if player is muted
            if (player.getName().equals(muted[i])){
               prevent=true;
                lan.muted(player); //message to player
            }

        }}catch (Exception e){
                    // no mutes found
                }
        if (prevent==false){
       // Sponge.getServer().getBroadcastChannel().send(Text.of(blocked[0] + " blocked"));
                   blocked= dat.getBlocked();
          message=  StringUtils.deleteWhitespace(message);
            for (int i = 0; i < blocked.length; i++) {
              //  getLogger().info(blocked[i]+" "+message);
                if (message.contains(blocked[i])) {
                    prevent = true;

                    try {
                        lan.muted(player); //message to player
                        lan.staff(player, message, blocked[i],type);      // sends warning to staff
                        store.addmute(player); //mutes player
                        store.report(player.getName(),blocked[i],message,type);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        }}


        return prevent;

    }
    public void Listplayers(Player player){ // lists active mutes to staff
        List<String>  players=null;
        store.Readmute();
        try{
        players  = (List<String>) dat.getplayersmuted();}
    catch (Exception e){
        Text mute = Text.of(TextColors.RED, TranslatableText.builder("No actively muted players").toText());
        player.sendMessage(mute);
    }
    try {
        Text mute = Text.of(TextColors.RED,"Players muted-" ,TranslatableText.builder(players.toString()).toText());
        player.sendMessage(mute);
    }catch (Exception e){
        Text mute = Text.of(TextColors.RED, TranslatableText.builder("No actively muted players").toText());
        player.sendMessage(mute);
    }
    }

    public void Listplayersa(){ // lists active mutes to console
        List<String>  players=null;
        try{
            store.Readmute();
            players  = (List<String>) dat.getplayersmuted();

            if (players.size()==0){
                getLogger().info("No actively muted players");
            }else {
                getLogger().info(String.valueOf(players));
            }
        }
        catch (Exception e){
          //  Text mute = Text.of(TextColors.RED, TranslatableText.builder("No actively muted players").toText());
           getLogger().info("No actively muted players");
        }
    }
    public void Serverstart(){
        store.Readmute();
    }
    public String familymode(String message){
        //for family screening
        Boolean prevent=false;
        for (int i = 0; i < curse.length; i++) {
        if (message.contains(curse[i])) {
            message.replace(curse[i],"");
        }}
        return message;
    }
}
