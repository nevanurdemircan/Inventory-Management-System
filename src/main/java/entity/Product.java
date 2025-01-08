package entity;

import java.util.List;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private double discount;

    private int supplierId;
    private List<String> imageUrlList;

    public Product() {
    }

    public Product( String name, int quantity, double price, double discount, int supplierId, List<String> imageUrlList) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.supplierId = supplierId;
        this.imageUrlList = imageUrlList;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }
}
