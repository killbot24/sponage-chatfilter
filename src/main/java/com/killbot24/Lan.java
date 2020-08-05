package com.killbot24;


import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;


import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.TranslatableText;
import org.spongepowered.api.text.format.TextColors;


/**
 * Created by tjbur on 08/06/2020.
 */
public class Lan extends Chat {
    data dat= new data();
    datastorage store = new datastorage();
    public void muted(Player player){

   // Text m=  Text.builder(dat.getmutemessage()).toText();
     Text mute = Text.of(TextColors.RED,TranslatableText.builder(dat.getmutemessage()).toText());
        player.sendMessage(mute);


    }
    public void staff(Player player,String message,String blocked,String type){
        Text text = Text.of(TextColors.DARK_GRAY,  "[",TextColors.AQUA,"Chat Filter",TextColors.DARK_GRAY,"]- ",TextColors.WHITE,player.getName(),TextColors.DARK_GRAY,"[",TextColors.GOLD,"Trigger",TextColors.DARK_GRAY,"]-",TextColors.WHITE,blocked,TextColors.DARK_GRAY,"[",TextColors.GOLD,"Source",TextColors.DARK_GRAY,"]-",TextColors.WHITE,type,TextColors.DARK_GRAY,"[",TextColors.DARK_GREEN,"Message",TextColors.DARK_GRAY,"]-",TranslatableText.builder(message).toText());
            // set text for message
        Sponge.getServer().getOnlinePlayers().stream().filter(pl -> pl.hasPermission("ct.base")).forEach(pl -> pl.sendMessage(text));   // messages all staff online


      }
    public void staffanvil(Player player,String message){
        Text text = Text.of(TextColors.DARK_GRAY,  "[",TextColors.AQUA,"Chat Filter",TextColors.DARK_GRAY,"]- ",TextColors.DARK_GRAY,  "[",TextColors.AQUA,"Anvil Alert",TextColors.DARK_GRAY,"]- ",TextColors.WHITE,player.getName(),"[",TextColors.DARK_GREEN,"Message",TextColors.DARK_GRAY,"]-",TranslatableText.builder(message).toText());
        // set text for message
        Sponge.getServer().getOnlinePlayers().stream().filter(pl -> pl.hasPermission("ct.base")).forEach(pl -> pl.sendMessage(text));   // messages all staff online


    }}




