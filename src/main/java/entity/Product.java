package entity;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private double discount;

    public Product() {
    }

    public Product(int id, String name, int stockQuantity, double price, double discount) {
        this.id = id;
        this.name = name;
        this.quantity = stockQuantity;
        this.price = price;
        this.discount = discount;
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
}
