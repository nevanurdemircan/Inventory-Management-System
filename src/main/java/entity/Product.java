package entity;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private double discount;

    private int supplierId;

    public Product() {
    }

    public Product(int id, String name, int quantity, double price, double discount, int supplierId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.supplierId = supplierId;
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
}
