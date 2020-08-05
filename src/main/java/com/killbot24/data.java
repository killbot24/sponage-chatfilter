package com.killbot24;

import org.slf4j.Logger;

import java.net.URL;
import java.util.List;

/**
 * Created by tjbur on 06/06/2020.
 */
public class data {
    private static String[] Blocked;    //blocked messages
    private static String[] mutes;      //muted players
    private static String Mutemessage;  //player mute message
    private static List<?> playersmuted;
    private static Logger logger;



    public void setBlocked(String[] input) {

        Blocked = input;
    }
    public String[] getBlocked() {
        return Blocked;}


    public void setMutemessage(String Mutemessage) {

        this.Mutemessage = Mutemessage;
    }
    public String getmutemessage() {
        return Mutemessage;}

    public void setmutes(String[] mutes) {

        this.mutes = mutes;
    }

    public String[] getmute() {
        return mutes;}
    public void setMuteplayers(List<?> players) {

        this.playersmuted = players;
    }
    public List<?> getplayersmuted() {
        return playersmuted;}

    public void setlogger(Logger a) {

        logger = a;
    }
    public Logger getLogger() {
        return logger;}


}

