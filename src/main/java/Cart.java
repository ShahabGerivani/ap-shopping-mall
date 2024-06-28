import java.util.HashMap;
import java.util.LinkedHashMap;

public class Cart {
    private final int id;
    private final String userUsername;
    private LinkedHashMap<Product, Integer> productsAndCount;
    private double total;

    public Cart(int id, String userUsername, LinkedHashMap<Product, Integer> productsAndCount) {
        this.id = id;
        this.userUsername = userUsername;
        this.productsAndCount = productsAndCount;
    }

    public int getId() {
        return id;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public HashMap<Product, Integer> getProductsAndCount() {
        return productsAndCount;
    }

    public void setProductsAndCount(LinkedHashMap<Product, Integer> productsAndCount) {
        this.productsAndCount = productsAndCount;
    }

    public double getTotal() {
        calculateAndSetTotal();
        return total;
    }

    private void calculateAndSetTotal() {
        total = 0;
        for (Product product : productsAndCount.keySet()) {
            total += product.getPrice() * productsAndCount.get(product);
        }
    }
}
