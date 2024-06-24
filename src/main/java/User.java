public class User {
    private final String username;
    private String name;
    private String phone;
    private String address;
    private double balance;
    private final boolean isAdmin;

    public User(String username, String name, String phone, String address, double balance, boolean isAdmin) {
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
