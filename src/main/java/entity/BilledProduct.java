package entity;

public class BilledProduct {
    private int id;
    private Bill bill;
    private int productId;
    private Double currentPrice;
    private Double amount;
    private Double totalPrice;

    public BilledProduct() {
    }

    public BilledProduct(int id, Bill bill, int productId, Double currentPrice, Double amount, Double totalPrice) {
        this.id = id;
        this.bill = bill;
        this.productId = productId;
        this.currentPrice = currentPrice;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
