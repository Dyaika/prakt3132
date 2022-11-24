package com.company;

public final class Drink extends MenuItem implements Alcoholable {
    private double alcoholVol;
    DrinkTypeEnum type;
    public Drink(int cost, String name, String description, int alcoholVol) {
        super(cost, name, description);
        this.alcoholVol = alcoholVol;
    }
    public Drink(int price, String name, String description) {
        super(price, name, description);
        this.alcoholVol = 0;
    }

    public DrinkTypeEnum getType(){
        return type;
    }
    @Override
    public boolean isAlcoholicDrink() {
        return alcoholVol > 0;
    }

    @Override
    public double getAlcoholVol() {
        return alcoholVol;
    }
}
