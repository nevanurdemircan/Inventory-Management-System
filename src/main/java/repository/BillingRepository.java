package repository;

import entity.Bill;

import java.util.List;

public interface BillingRepository {
    List<Bill> findAlBySupplierId(int supplierId);
    Bill save(Bill bill);
}
