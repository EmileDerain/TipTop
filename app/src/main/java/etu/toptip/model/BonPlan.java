package etu.toptip.model;

public class BonPlan {
    private String date;
    private Integer image;
    private int idUser;
    private int idPlace;

    public BonPlan(String date, Integer image, int idUser, int idPlace, String description) {
        this.date = date;
        this.image = image;
        this.idUser = idUser;
        this.idPlace = idPlace;
        this.description = description;
    }

    private String description;

    public String getDate() {
        return date;
    }

    public Integer getImage() {
        return image;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public String getDescription() {
        return description;
    }
}
