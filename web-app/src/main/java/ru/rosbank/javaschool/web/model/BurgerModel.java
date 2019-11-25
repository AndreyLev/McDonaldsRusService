package ru.rosbank.javaschool.web.model;



public class BurgerModel extends ProductModel {

    public String getMeatType() {
        return meatType;
    }

    public void setMeatType(String meatType) {
        this.meatType = meatType;
    }

    private String meatType;

    public BurgerModel(int id,String name, int price, int quantity,  String imageURL, String meatType, String about) {
        super(id ,name,price, quantity, imageURL, about);
        this.meatType = meatType;
    }
}
