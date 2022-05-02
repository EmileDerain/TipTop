package etu.toptip.views;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import etu.toptip.R;
import etu.toptip.activities.MainActivity;
import etu.toptip.controller.NotificationsController;
import etu.toptip.model.NotificationsModel;

public class NotificationsView implements Observer {
    private NotificationsController notificationsController;
    private final View root;
    int notificationId=0;

    public NotificationsView(RelativeLayout root) {
        this.root = root;
        this.setListeners();
    }

    private void setListeners() {
        final Button showNotificationButton = root.findViewById(R.id.BtnAjouterBP);
        showNotificationButton.setOnClickListener(click -> notificationsController.newNotification());
    }

    public void setController(NotificationsController notificationsController) {
        this.notificationsController = notificationsController;
    }

    /**
     * Show the notification with a given text, and given image
     *
     * @param img  Image in bitmap format
     * @param text Text (String) to display on the notification
     */
    private void showNotificationWithImage(final String text, final Bitmap img) {
        final String title = "TIP TOP";
        final String channelId = "notification.channel2";
        final int flags = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;
        Context context = root.getContext();

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        Intent intent = new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, flags);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);

        builder.setSmallIcon(R.drawable.logo);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setContentIntent(pendingIntent);
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(img));
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.getNotificationChannel(channelId) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId, "notification.bonPlan", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription("Channel des nouveaux bons plans");
                notificationChannel.enableVibration(true);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(0xFFFF0000);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Notification notification = builder.build();
        Objects.requireNonNull(notificationManager).notify(notificationId++, notification);
    }

    /**
     * Create a thread which display a notification
     */
    public void newNotification(final String text, Bitmap img) {
        //final Bitmap img = notificationsController.fetchImage(url);
        new Thread(() -> showNotificationWithImage(text, img)).start();
    }


    @Override
    public void update(Observable observable, Object o) {
        final String text = NotificationsModel.getInstance().getNotificationText();
        final Bitmap img = NotificationsModel.getInstance().getNotificationImage();
        this.newNotification(text, img);

        if (observable.hasChanged()) {
            this.newNotification("t", img);
        }
    }
}
