package marketplaceserverapi.models;

public class OrderItem {

    private int productId;
    private double price;
    private int quantity;
    private double discount;

    public OrderItem() {}

    public OrderItem(int productId, double price, int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.discount = 0;
    }

    public int getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
