import java.util.HashMap;

public class FoodShop {
    private static HashMap<String, Food> assortment;

    public FoodShop() {
        assortment = new HashMap<String, Food>();
        putFood("SimpleFood", 1, 1);
        putFood("CoolFood", 5, 10);
    }

    private void putFood(String name, int price, int saturation){
        assortment.put(name, new Food(name, price, saturation));
    }

    public Food buy(String foodName) {
        return assortment.get(foodName);
    }

    public String showAssortment() {
        StringBuilder result = new StringBuilder("Ассортимент продуктов:");
        for (var foodName: assortment.keySet()){
            var food = assortment.get(foodName);
            result.append("\n" + foodName + " Цена: " + food.getPrice() + " Насыщение: " + food.getSaturation());
        }
        return result.toString();
    }

    public HashMap<String, Food> getAssortment() {
        return assortment;
    }
}
