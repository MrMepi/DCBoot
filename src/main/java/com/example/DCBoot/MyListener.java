package com.example.DCBoot;

import org.apache.commons.lang3.time.DateUtils;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyListener implements MessageCreateListener {

    List<BOSS> listBoss = Arrays.asList(
            new BOSS("test","00:06"),
            new BOSS("Drzewiec","01:28"),
            new BOSS("Hugo","05:05"),
            new BOSS("Des","05:05"),
            new BOSS("Tortolla","05:05"),
            new BOSS("Archont","04:05"),
            new BOSS("Zwierzobij","04:05"),
            new BOSS("Szczur","02:05"),
            new BOSS("Edward","04:05"),
            new BOSS("Szaman","03:25"),
            new BOSS("BJ","00:10"),
            new BOSS("M1","02:00"),
            new BOSS("M2","02:00"),
            new BOSS("KC","01:57"),
            new BOSS("MI","02:00"),
            new BOSS("F","02:00"),
            new BOSS("W","02:00"),
            new BOSS("ZS","00:20"),
            new BOSS("BGW","01:10"),
            new BOSS("BGK","02:00"),
            new BOSS("BGT","02:00"),
            new BOSS("WA","02:00"),
            new BOSS("DA","02:00"),
            new BOSS("KT","02:00"),
            new BOSS("WJ","01:48"),
            new BOSS("KP","02:00"),
            new BOSS("Ż","00:20"),
            new BOSS("ZK","02:00"),
            new BOSS("KB","02:00")
            );


    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        TimerTask task = null;
        if(getText(messageCreateEvent.getMessageContent())[0].equalsIgnoreCase("!boss")) {
             task = new TimerTask() {
                public void run() {
                    if (getText(messageCreateEvent.getMessageContent())[0].equalsIgnoreCase("!boss")) {
                        messageCreateEvent.getChannel().sendMessage("@TIMERY "+ getText(messageCreateEvent.getMessageContent())[2]);
                    }
                }
            };
        } else if (getText(messageCreateEvent.getMessageContent())[0].equalsIgnoreCase("!!boss")){
             task = new TimerTask() {
                public void run() {
                    if (getText(messageCreateEvent.getMessageContent())[0].equalsIgnoreCase("!!boss")) {
                        messageCreateEvent.getChannel().sendMessage("@everyone "+getText(messageCreateEvent.getMessageContent())[2]);
                    }
                }
            };
        }
        Timer timer = new Timer("Timer");
        String delay = "";
        Date start = null;
        try {
            if(getText(messageCreateEvent.getMessageContent()).length > 1) {
                delay = listBoss.stream().filter(b -> b.name.equals(getText(messageCreateEvent.getMessageContent())[2])).findFirst().get().time;
                start = parseDateD(getText(messageCreateEvent.getMessageContent())[1]);
            }
        }catch (ArrayIndexOutOfBoundsException e){

        }
        catch (Exception e) {

        }
        if(!delay.isEmpty() && timer != null) {
            String[] m = delay.split(":");
            int minut = (Integer.parseInt(m[0]) * 60) + (Integer.parseInt(m[1]) - 5);
            if (start != null) {

                timer.schedule(task, DateUtils.addMinutes(start, minut).getTime() - start.getTime());
                if(getText(messageCreateEvent.getMessageContent()).length > 1) {
                    messageCreateEvent.getChannel().sendMessage(getText(messageCreateEvent.getMessageContent())[2] + " "
                            + (DateUtils.addMinutes(start, minut).getHours() - 1) + ":" + DateUtils.addMinutes(start, minut).getMinutes());
                }
            }
        }
    }

    private static Date parseDateD(String text)
            throws ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm",
                Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.parse(text);
    }

    public String[] getText(String message){
        return message.split(" ");
    };

    private static long parseDate(String text)
            throws ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm",
                Locale.GERMANY);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.parse(text).getTime();
    }

    public class BOSS{
        String name;
        String time;

        public BOSS(String name, String time) {
            this.name = name;
            this.time = time;
        }
    }
}
