package multibrandinfotech.developer.gmeagro.Model;

public class ItemList {
    private String item;
    private double quantity;
    private int price;
    private String discount;
    private double amount;

    public ItemList( String item, double quantity, int price, String discount, double amount) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.amount = amount;
    }
    public String getItem() {
        return item;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    public double getAmount() {
        return amount;
    }
}
