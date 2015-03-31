package de.leonadi.potify;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.common.base.Joiner;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import de.leonadi.potify.models.Plant;

/**
 * Created by leon on 31.03.15.
 */
public class PlantReminder extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        List<Plant> plants = Plant.listAll(Plant.class);
        List<String> duePlants = new ArrayList<>();
        for (Plant plant : plants) {
            if (plant.getNextWaterNeeded().isBeforeNow())
                duePlants.add(plant.getName());
        }
        if (duePlants.size() > 0) {
            NotificationCompat.Builder nb = new NotificationCompat.Builder(context);
            nb.setSmallIcon(R.mipmap.ic_launcher);
            nb.setContentTitle(duePlants.size() + " plants need water");
            nb.setContentText(Joiner.on(", ").join(duePlants));
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            nb.setSound(alarmSound);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, nb.build());
        }
    }
}
