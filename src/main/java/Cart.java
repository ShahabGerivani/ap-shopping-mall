import java.util.HashMap;

public class Cart {
    private final String userUsername;
    private HashMap<Product, Integer> productsAndCount;
    private double total;

    public Cart(String userUsername, HashMap<Product, Integer> productsAndCount) {
        this.userUsername = userUsername;
        this.productsAndCount = productsAndCount;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public HashMap<Product, Integer> getProductsAndCount() {
        return productsAndCount;
    }

    public void setProductsAndCount(HashMap<Product, Integer> productsAndCount) {
        this.productsAndCount = productsAndCount;
        calculateAndSetTotal();
    }

    public double getTotal() {
        return total;
    }

    private void calculateAndSetTotal() {
        total = 0;
        for (Product product : productsAndCount.keySet()) {
            total += product.getPrice() * productsAndCount.get(product);
        }
    }
}
