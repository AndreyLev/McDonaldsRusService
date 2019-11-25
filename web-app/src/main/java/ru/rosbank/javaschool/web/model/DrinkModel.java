package ru.rosbank.javaschool.web.model;


public class DrinkModel extends ProductModel {

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    private String volume;

    public DrinkModel(int id,String name, int price, int quantity,  String imageURL, String volume, String about) {
        super(id ,name,price, quantity, imageURL,about);
        this.volume = volume;
    }
}
