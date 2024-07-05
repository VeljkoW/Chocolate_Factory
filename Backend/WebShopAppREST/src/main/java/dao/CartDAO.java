package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import Utilities.Reader;
import beans.Cart;
import beans.Chocolate;

public class CartDAO {
    private List<Cart> carts = new ArrayList<>();
    private Gson gson = new GsonBuilder().registerTypeAdapter(HashMap.class, new HashMapAdapter()).create();

    private String contextpath;

    public CartDAO(String a) {
        contextpath = a.split(".metadata")[0] + "WebShopAppREST\\src\\main\\resources\\data\\carts.json";
        carts = getAll();
    }

    public Cart getById(int id) {
        if (carts == null) {
            return null;
        }
        return carts.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public Cart getByUserId(int userId) {
        if (carts == null) {
            return null;
        }
        return carts.stream().filter(t -> t.getUserId() == userId).findFirst().orElse(null);
    }

    public List<Cart> getAll() {
        try {
            String json = Reader.readFileAsString(contextpath);
            Type listType = new TypeToken<List<Cart>>() {
            }.getType();
            List<Cart> jsondata = gson.fromJson(json, listType);
            if (jsondata != null) {
                carts = jsondata;
            }
            return carts;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean add(Cart cart) throws IOException {
        cart.setId(cart.hashCode());
        carts.add(cart);
        return write();
    }

    public boolean addChocolate(int cartId, Chocolate chocolate) throws IOException {
        Cart cartToUpdate = carts.stream().filter(c -> c.getId() == cartId).findFirst().orElse(null);
        if (cartToUpdate != null) {
            // Update chocolates in the cart
            if (cartToUpdate.getChocolates() == null) {
                cartToUpdate.setChocolates(new HashMap<>());
            }
            cartToUpdate.getChocolates().merge(chocolate.getId(), 1, Integer::sum);
            return write();
        }
        return false;
    }

    public boolean deleteById(int id) {
        Cart cartToRemove = carts.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
        if (cartToRemove != null) {
            carts.remove(cartToRemove);
            return write();
        }
        return false;
    }

    public boolean update(Cart updatedCart) {
        int index = carts.indexOf(updatedCart);
        if (index != -1) {
            carts.set(index, updatedCart);
            return write();
        }
        return false;
    }

    public boolean write() {
        String json = gson.toJson(carts);
        try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static class HashMapAdapter implements com.google.gson.JsonSerializer<HashMap<Integer, Integer>> {
        @Override
        public JsonElement serialize(HashMap<Integer, Integer> map, Type type,
                com.google.gson.JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                jsonObject.addProperty(entry.getKey().toString(), entry.getValue());
            }
            return jsonObject;
        }
    }
}