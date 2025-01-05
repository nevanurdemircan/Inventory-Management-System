package dto;

import java.util.ArrayList;
import java.util.List;

public class BillCreateDto {
    private int retailerId;

    private List<BillProductCreateDto> products = new ArrayList<>();

    public int getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(int retailerId) {
        this.retailerId = retailerId;
    }

    public List<BillProductCreateDto> getProducts() {
        return products;
    }

    public void setProducts(List<BillProductCreateDto> products) {
        this.products = products;
    }
}
