package service;

import dto.BillCreateDto;
import dto.BillProductCreateDto;
import entity.Bill;
import entity.BilledProduct;
import entity.Product;
import exception.AppException;
import exception.ExceptionError;
import manager.SingletonManager;
import repository.BilledProductRepository;
import repository.BillingRepository;
import repository.impl.BilledProductRepositoryImpl;
import repository.impl.BillingRepositoryImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BillingService {
    private final BillingRepository billingRepository = SingletonManager.getBean(BillingRepositoryImpl.class);
    private final ProductService productService = SingletonManager.getBean(ProductService.class);
    private final BilledProductRepository billedProductRepository = SingletonManager.getBean(BilledProductRepositoryImpl.class);

    public List<Bill> findAllBySupplierId(int supplierId) {
        return billingRepository.findAllBySupplierId(supplierId);
    }

    public List<Bill> findAllByRetailerId(int supplierId) {
        return billingRepository.findAllBySupplierId(supplierId);
    }

    public Bill save(Bill bill) {
        return billingRepository.save(bill);
    }

    public void create(BillCreateDto payload) {
        List<Integer> productIds = payload.getProducts().stream().map(BillProductCreateDto::getProductId).toList();
        List<Product> products = productService.findAllByIdIn(productIds);

        if (products.isEmpty()) {
            throw new AppException(ExceptionError.PRODUCT_NOT_FOUND);
        }

        int supplierId = products.get(0).getSupplierId();

        double billTotalPrice = 0;
        List<BilledProduct> billedProducts = new ArrayList<>();
        for (Product product : products) {
            int amount = payload.getProducts().stream()
                    .filter(i -> Objects.equals(i.getProductId(), product.getId()))
                    .findFirst()
                    .orElseThrow(() -> new AppException(ExceptionError.PRODUCT_NOT_FOUND))
                    .getAmount();

            double productTotalPrice = productService.calculatePrice(product, amount);
            billTotalPrice += productTotalPrice;

            BilledProduct billedProduct = new BilledProduct();
            billedProduct.setProductId(product.getId());
            billedProduct.setCurrentPrice(product.getPrice());
            billedProduct.setAmount(amount);
            billedProduct.setTotalPrice(productTotalPrice);
            billedProducts.add(billedProduct);
        }

        Bill bill = new Bill();
        bill.setSupplierId(supplierId);
        bill.setRetailerId(payload.getRetailerId());
        bill.setTotalPrice(billTotalPrice);
        bill.setDate(new Date());

        Bill savedBill = billingRepository.save(bill);

        billedProducts.forEach(i -> {
            i.setBillId(savedBill.getId());
            billedProductRepository.save(i);
        });

    }

    public void confirm(int id) {
        List<BilledProduct> billedProducts = billedProductRepository.findAllByBillId(id);

        for (BilledProduct billedProduct : billedProducts) {
            int amount = billedProduct.getAmount();
            int productId = billedProduct.getProductId();

            productService.reduceAmount(productId, amount);
        }

        billingRepository.updateConfirm(id, true);
    }
}
