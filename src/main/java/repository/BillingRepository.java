package repository;

import entity.Bill;

import java.util.List;

public interface BillingRepository {
    List<Bill> findAllBySupplierId(int supplierId);

    List<Bill> findAllByRetailerId(int retailerId);

    Bill save(Bill bill);

    void updateConfirm(int id, boolean confirm);
}
