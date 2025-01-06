## Postman API Collection

Bu projede kullanılan Postman API Koleksiyonu aşağıdaki klasörde bulunabilir:

- `postman/inventory-management-system.postman_collection.json`

- Her bir klasör, farklı işlemleri gerçekleştiren endpoint'leri içermektedir:

Authentication (Auth)
Register: Kullanıcı kaydı yapılır. Kullanıcının bilgileri alınarak sistemde bir kullanıcı oluşturulur.
Login: Kullanıcının giriş yapmasını sağlar. 

Fatura Yönetimi (Bill)
Save: Yeni bir fatura kaydı oluşturulur.
findAllBySupplierId: Belirtilen supplierId (tedarikçi ID) ile ilişkilendirilmiş faturaları getirir.
findAllByRetailerId: Belirtilen retailerId (satıcı ID) ile ilişkilendirilmiş faturaları getirir.
Create: Birden fazla ve farklı türde ürünün yer aldığı yeni bir fatura oluşturulur.
Confirm: Gönderilen id parametresine göre belirtilen faturanın onaylanmasını sağlar.

Kullanıcı Yönetimi (User)
Save: Yeni bir kullanıcı oluşturur.
findAll: Sistemde kayıtlı tüm kullanıcıları listeler.
findById: Gönderilen id parametresine göre belirli bir kullanıcıyı getirir.
Update: Kullanıcının bilgilerini günceller (örneğin isim, e-posta, telefon numarası vb.).

Ürün Yönetimi (Product)
Save: Yeni bir ürün kaydı oluşturur.
findAllByProductLike: name ve supplierId parametrelerine göre ürün veya ürün gruplarını arar. Kullanıcıların belirli bir tedarikçinin ürünleri arasında arama yapmasını sağlar.
Update: Ürünün herhangi bir özelliğini (örneğin adı, fiyatı, açıklaması vb.) günceller.
