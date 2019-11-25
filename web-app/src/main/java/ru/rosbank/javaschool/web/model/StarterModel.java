package ru.rosbank.javaschool.web.model;

public class StarterModel extends ProductModel{

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private int amount;

    public StarterModel(int id,String name, int price, int quantity,  String imageURL, int amount, String about) {
        super(id ,name,price, quantity, imageURL, about);
        this.amount = amount;
    }
}
