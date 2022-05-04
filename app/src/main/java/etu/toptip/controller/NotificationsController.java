package etu.toptip.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;

import etu.toptip.activities.AddBonPlanActivity;
import etu.toptip.model.NotificationsModel;
import etu.toptip.views.NotificationsView;

public class NotificationsController {
    private final NotificationsView notificationsView;
    //private AddBonPlanActivity addBonPlanActivity;

    public NotificationsController(NotificationsView notificationsView) {
        this.notificationsView = notificationsView;
        //this.addBonPlanActivity=addBonPlanActivity;
    }

    /**
     * Try to get the image as a Bitmap from a given link as parameter
     *
     * @param src link of the image to download
     * @return Bitmap image
     */
    public Bitmap fetchImage(final String src) {
        try {
            return BitmapFactory.decodeStream(new URL(src).openConnection().getInputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }


    public void newNotification() {
        NotificationsModel notificationsModel = NotificationsModel.getInstance();
        Observable e = new Observable();
        e.notifyObservers();
        notificationsModel.setNotificationText("TipTop");
        notificationsModel.setNotificationImage(notificationsView.getAddBonPlanActivity().getPicture());
        notificationsView.update(e, notificationsModel);
    }
}
