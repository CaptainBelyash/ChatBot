public class Food {
    private String name;
    private int price;
    private int saturation; //насыщение - то, на сколько увеличивается сытость

    public Food(String name, int price, int saturation) {
        this.name = name;
        this.price = price;
        this.saturation = saturation;
    }

    public int getPrice() {
        return price;
    }

    public int getSaturation() {
        return saturation;
    }

    public String getName(){
        return name;
    }
}
