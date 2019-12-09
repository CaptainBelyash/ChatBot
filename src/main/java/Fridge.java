import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Fridge {
    private HashMap<String, Food> products;
    private HashMap<String, AtomicInteger> amounts;

    public Fridge(){
        products = new HashMap<String, Food>();
        amounts = new HashMap<String, AtomicInteger>();
    }

    public boolean containsFood(String foodName) {
        return products.containsKey(foodName);
    }

    public Food getFood(String foodName) {
        amounts.get(foodName).decrementAndGet();
        var food = products.get(foodName);
        if (amounts.get(foodName).get() == 0) {
            amounts.remove(foodName);
            products.remove(foodName);
        }
        return food;
    }

    public void putFood(Food food) {
        if (products.containsKey(food.getName()))
            amounts.get(food.getName()).incrementAndGet();
        else {
            products.put(food.getName(), food);
            amounts.put(food.getName(), new AtomicInteger(1));
        }
    }

    public String getAssortment() {
        StringBuilder assortment = new StringBuilder("Ассортимент продуктов:");
        for (var foodName: products.keySet()){
            var food = products.get(foodName);
            var amount = amounts.get(foodName);
            assortment.append("\n" + foodName + " Насыщение: " + food.getSaturation() + "Количество: " + amount);
        }
        return assortment.toString();
    }

    public int getAmount(String foodName) {
        return amounts.get(foodName).get();
    }
}
