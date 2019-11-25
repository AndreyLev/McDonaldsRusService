package ru.rosbank.javaschool.web.model;


public class PotatoModel extends ProductModel {

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    private String size;

    public PotatoModel(int id,String name, int price, int quantity,  String imageURL, String size, String about) {
        super(id ,name,price, quantity, imageURL,about);
        this.size = size;
    }
}
