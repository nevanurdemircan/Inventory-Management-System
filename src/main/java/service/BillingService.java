package service;

import entity.Bill;
import repository.BillingRepository;
import repository.impl.BillingRepositoryImpl;

import java.util.List;

public class BillingService {
    BillingRepository billingRepository = new BillingRepositoryImpl();


    public List<Bill> findAllBySupplierId(int supplierId) {
        return billingRepository.findAlBySupplierId(supplierId);
    }

    public Bill save(Bill bill) {
        return billingRepository.save(bill);
    }
}
