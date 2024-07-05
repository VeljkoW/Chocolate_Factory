package beans;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Cart {
    private int id;
    private HashMap<Integer, Integer> chocolates; // Use Chocolate ID as key
    private int userId;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<Integer, Integer> getChocolates() {
        return chocolates;
    }

    public void setChocolates(HashMap<Integer, Integer> chocolates) {
        this.chocolates = chocolates;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Cart() {
        super();
    }

    public Cart(int id, HashMap<Integer, Integer> chocolates, double price, int userId) {
        super();
        this.id = id;
        this.chocolates = chocolates;
        this.price = price;
        this.userId = userId;
    }
}