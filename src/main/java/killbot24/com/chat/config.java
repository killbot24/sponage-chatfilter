package killbot24.com.chat;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

/**
 * Created by tjbur on 01/06/2020.
 */
public class config {
    private static File configFile;
    private static ConfigurationLoader<CommentedConfigurationNode> configManager;
    private static CommentedConfigurationNode config;



    public static void init(File rootDir)
    {
        configFile = new File(rootDir, "config.conf");
        configManager = HoconConfigurationLoader.builder().setPath(configFile.toPath()).build();

    }

    public static void load()
    {
        try
        {
            if (!configFile.exists())
            {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
                config = configManager.load();
                configManager.save(config);
            }
            config = configManager.load();
        }
        catch (IOException e)
        {

            e.printStackTrace();
            return;
        }
        final TypeToken<List> token = new TypeToken<List>() {};
        List list = new ArrayList<>();

        // check integrity
        list.add("nigger");list.add("fag");

        config.getNode("Blocked").setComment("Blocked words");
        Utils.ensureString(config.getNode("Blocked"),list.toString());
        config.getNode("Mute").setComment("The message displayed to players on mute");
        Utils.ensureString(config.getNode("Mute"),"Mute message");







        save();
    }

    public static void save()
    {
        try
        {
            configManager.save(config);
        }
        catch (IOException e)
        {

        }
    }
    public static CommentedConfigurationNode getNode(String... path)
    {
        return config.getNode((Object[]) path);
    }


    public static class Utils
    {
        public static void ensureString(CommentedConfigurationNode node, String def)
        {
            if (node.getString() == null)
            {
                node.setValue(def);
            }
        }

        public static void ensurePositiveNumber(CommentedConfigurationNode node, Number def)
        {
            if (!(node.getValue() instanceof Number) || node.getDouble(-1) < 0)
            {
                node.setValue(def);
            }
        }

        public static void ensureBoolean(CommentedConfigurationNode node, boolean def)
        {
            if (!(node.getValue() instanceof Boolean))
            {
                node.setValue(def);
            }
        }
    }
}
