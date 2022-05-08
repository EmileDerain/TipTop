package etu.toptip.model;

public class NotificationsModel {
    private static NotificationsModel instance;
    private String notificationText = "TopTip";
    private String notificationImage = "";

    private NotificationsModel() {
    }

    public static NotificationsModel getInstance() {
        if (instance == null) {
            instance = new NotificationsModel();
        }
        return instance;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNotificationImage() {
        return notificationImage;
    }

    public void setNotificationImage(String notificationImage) {
        this.notificationImage = notificationImage;
    }

}