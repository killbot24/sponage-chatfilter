package com.killbot24;

import com.google.inject.Inject;
import com.killbot24.Commands.check;
import com.killbot24.Commands.list;
import com.killbot24.Commands.unmute;
import com.killbot24.Commands.reload;
import com.killbot24.listener.playerchatListener;
import ninja.leaping.configurate.ConfigurationNode;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.text.Text;


@Plugin(
        id = "chat-filter",
        name = "Chat-filter",
        description = "Chat filter",
        authors = {
                "killbot24"
        }
)
public class Chat {
    private static Chat plugin;
    private static File rootDir;
    private String[] Blocked;
    private String[] mutes;
    private ConfigurationNode rootNode;
    data dat= new data();


    @Inject
    public Logger logger;
    @Inject
    @ConfigDir(sharedRoot = true)
    private File defaultConfigDir;
    @Listener
    public void onInit(GameInitializationEvent event) throws IOException
    {
        plugin = this;

        rootDir = new File(defaultConfigDir, "chat-filter");
// Try loading some configuration settings for a welcome message to players
        // when they join!
        Sponge.getEventManager().registerListeners(this, new playerchatListener());
        //  Lang.init(rootDir);

        config.init(rootDir);



    }
    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        // Hey! The server has started!
        this.logger.info("Hello Chat filter here just blocking messages!!");
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



        /* delimiter */
        String delimiter = ",";
           String b= a.replace("[","");
       String c =b.replace("]","");
        String d=c.replace(" ","");
        /* given string will be split by the argument delimiter provided. */
        String[] tempArray = d.split(delimiter);
       // getLogger().info(tempArray[1]);
        dat.setlogger(logger);
        dat.setBlocked(tempArray);
        CommandSpec myCommandSpec = CommandSpec.builder()
                .description(Text.of("reloads chat filter"))
                .permission("ct.reload")
                .executor(new reload())
                .build();

        CommandSpec list = CommandSpec.builder()
                .description(Text.of("lists all active mutes"))
                .permission("ct.base")
                .executor(new list())
                .build();

        CommandSpec unmute = CommandSpec.builder()
                .description(Text.of("unmutes player"))
                .permission("ct.base")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
                .executor(new unmute())
                .build();

        CommandSpec check = CommandSpec.builder()
                .description(Text.of("checks player"))
                .permission("ct.base")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
                .executor(new check())
                .build();

        CommandSpec Main = CommandSpec.builder()
                .description(Text.of("All chat tools commands"))
                .child(unmute,"unmute") // links to command unmute
                .child(myCommandSpec,"reload")
                .child(list,"list")
                .child(check,"check")
                .build();

        Sponge.getCommandManager().register(plugin, myCommandSpec, "cttreload");
        Sponge.getCommandManager().register(plugin, unmute, "cttunmute");
        Sponge.getCommandManager().register(plugin, Main, "ctt");
        Sponge.getCommandManager().register(plugin, list, "cttlist");
        Sponge.getCommandManager().register(plugin, check, "cttcheck");

    }




    public static Chat getInstance()
    {
        return plugin;
    }
    public static Logger getLogger()
    {
        return getInstance().logger;
    }


    public File getfile() {
        return rootDir;}}

