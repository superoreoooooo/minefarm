package org.oreoprojekt.minefarm.util;

import org.oreoprojekt.minefarm.Minefarm;

import java.text.SimpleDateFormat;
import java.util.Date;

public class mineFarmCurrentTimeUtilizer {

    private Minefarm plugin;

    public mineFarmCurrentTimeUtilizer(Minefarm plugin) {
        this.plugin = plugin;
    }
    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM월dd일 HH:mm");
        Date time = new Date();
        String RealTime = format.format(time);
        return RealTime;
    }
}
