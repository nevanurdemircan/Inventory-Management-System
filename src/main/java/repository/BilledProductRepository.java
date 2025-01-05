package repository;

import entity.BilledProduct;

import java.util.List;

public interface BilledProductRepository {
    BilledProduct save(BilledProduct billedProduct);

    List<BilledProduct> findAllByBillId(int id);
}
