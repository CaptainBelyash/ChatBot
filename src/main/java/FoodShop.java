import java.util.HashMap;

public class FoodShop {
    private HashMap<String, Food> assortment;

    public FoodShop() {
        assortment = new HashMap<String, Food>();
        putFood("SimpleFood", 1, 1);
        putFood("CoolFood", 5, 10);
    }

    private void putFood(String name, int price, int saturation){
        assortment.put(name, new Food(name, price, saturation, 1));
    }

    public Food buy(String foodName) {
        return assortment.get(foodName);
    }

    public String showAssortment() {
        StringBuilder assortment = new StringBuilder("Ассортимент продуктов:");
        for (var foodName: this.assortment.keySet()){
            var food = this.assortment.get(foodName);
            assortment.append("\n" + foodName + " Цена: " + food.getPrice() + " Насыщение: " + food.getSaturation());
        }
        return assortment.toString();
    }

    public HashMap<String, Food> getAssortment() {
        return assortment;
    }
}
