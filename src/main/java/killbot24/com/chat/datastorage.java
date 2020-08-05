package killbot24.com.chat;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.network.PlayerConnection;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TranslatableText;
import org.spongepowered.api.text.format.TextColors;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static sun.audio.AudioPlayer.player;

/**
 * Created by tjbur on 06/06/2020.
 */
public class datastorage extends Chat{
    data dat=new data();
    private List<?> lmutes;
    public void unmutec(final String suspect) throws IOException {
        final File file = new File(getfile().getAbsoluteFile(), "Active-mutes.yml");
        final FileWriter fw = new FileWriter(file);
        final BufferedWriter bw = new BufferedWriter(fw);
        try {
            final List<?> inputa = Files.readAllLines(file.toPath());
            inputa.remove(suspect);

            for (int i = 0; i < inputa.size(); ++i) {
                bw.write(inputa.get(i).toString());
            }
        }
        catch (Exception e) {
            getLogger().info("[Warning] Issue editing file in unmute");
        }
        this.getLogger().info(suspect + " is unmuted");
        this.Readmute();
    }

    public void readreportc(final String suspect) throws FileNotFoundException {
        final File file = new File(getfile().getAbsoluteFile() + "/Warnings", suspect + ".yml");
        final Scanner myReader = new Scanner(file);
        try {
            final List<?> infraction = Files.readAllLines(file.toPath());
            String[] user = new String[infraction.size()];
            user = infraction.toArray(user);
            this.getLogger().info("Player's infraction");
            for (int i = 0; i < user.length; ++i) {
                getLogger().info( user[i]);
            }
            myReader.close();
        }
        catch (Exception e) {
            getLogger().info("No record of player");
        }
    }

    public void Readmute() {
        final File file = new File(getfile().getAbsoluteFile(), "Active-mutes.yml");
        Scanner myReader = null;
        try {
            file.createNewFile();
        }
        catch (IOException e3) {
            getLogger().info("Error in createing active mute file");
        }
        try {
            myReader = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (Exception e2) {
                getLogger().info("Error in createing active mute file");
                e2.printStackTrace();
            }
        }
        else {
            try {
                String[] mutes;


                lmutes = Files.readAllLines(file.toPath());
               mutes = new String[lmutes.size()];
                mutes = (String[]) lmutes.toArray(mutes);
                dat.setmutes(mutes);
                dat.setMuteplayers(lmutes);
            }
            catch (Exception b) {
                this.getLogger().info("[Chat-Filter] " + b.getStackTrace());
            }
        }
        myReader.close();
    }

    public void addmute(final Player player) throws IOException {
        final File file = new File(getfile().getAbsoluteFile(), "Active-mutes.yml");
        final PrintWriter out = new PrintWriter(new FileWriter(file, false));
        try {
            out.write("\n" + player.getName() + "\n");
            out.close();
            this.Readmute();
        }
        catch (Exception e) {
            this.getLogger().info("[Warning] Issue writeing file");
        }
    }

    public void unmute(final String player, final Player sender) throws IOException {
        final File file = new File(getfile().getAbsoluteFile(), "Active-mutes.yml");
        final PrintWriter out = new PrintWriter(new FileWriter(file, true));
        final Scanner myReader = new Scanner(file);
        final FileWriter fw = new FileWriter(file);
        final BufferedWriter bw = new BufferedWriter(fw);
        try {
            final List<?> inputa = Files.readAllLines(file.toPath());
            inputa.remove(player);
            for (int i = 0; i < inputa.size(); ++i) {
                bw.write(inputa.get(i).toString());
            }
        }
        catch (Exception e) {
            this.getLogger().info("[Warning] Issue editing file in unmute");
        }

        Text message = Text.of(TextColors.RED, TranslatableText.builder(player+" is unmuted"));
        sender.sendMessage(message);
        getLogger().info( player + " is unmuted");
        this.Readmute();
    }

    public void report(final String player, final String trigger, final String warning, final String Type) throws IOException {
        createfolder();
        final File file = new File(getfile().getAbsoluteFile() + "/Warnings", player + ".yml");
        final PrintWriter out = new PrintWriter(new FileWriter(file, true));
        final String timeStamp = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e1) {
                getLogger().info(player + "'s data file could not be created!");
                e1.printStackTrace();
            }
        }
        else {
            try {
                out.write("\n" + timeStamp + "[Trigger word]:" + trigger + " [Warning type]: " + Type + " [Message]: " + warning);
                out.close();
            }
            catch (Exception e2) {
                this.getLogger().info("[Warning] Issue writeing file");
            }
        }
    }

    public void readreport(final String suspect, final Player sender) throws IOException {
        final File file = new File(getfile().getAbsoluteFile() + "/Warnings", suspect + ".yml");
        final Scanner myReader = new Scanner(file);
        try {
            final List<?> infraction = Files.readAllLines(file.toPath());   // reads file
            String[] user = new String[infraction.size()];
            user = infraction.toArray(user);
         //   sender.sendMessage(TextColors.GOLD+"Player's infraction");
            Text messagea = Text.of(TextColors.RED, TranslatableText.builder("Player's infraction"));
            sender.sendMessage(messagea);
            for (int i = 0; i < user.length; ++i) {
          //      sender.sendMessage(TextColors.GRAY + user[i]);
                Text message = Text.of(TextColors.RED, TranslatableText.builder(user[i]));
                sender.sendMessage(message);
            }
            myReader.close();
        }
        catch (Exception e) {
            Text message = Text.of(TextColors.RED, TranslatableText.builder("No record of player"));
            sender.sendMessage(message);

        }
    }

    public void createfolder() {
        final File warningfolder = new File(getfile().getAbsoluteFile(), "Warnings");
        if (!warningfolder.exists()) {
            warningfolder.mkdir();
        }
    }
}
