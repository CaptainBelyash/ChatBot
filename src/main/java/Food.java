public class Food {
    private String name;
    private int price;
    private int saturation; //насыщение - то, на сколько увеличивается сытость
    private int amount;

    public Food(String name, int price, int saturation, int amount) {
        this.name = name;
        this.price = price;
        this.saturation = saturation;
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public int getSaturation() {
        return saturation;
    }

    public int getAmount(){
        return amount;
    }

    public String getName(){
        return name;
    }

    public void increaseAmount() {
        amount += 1;
    }

    public void reduceAmount(){
        amount -= 1;
    }
}
