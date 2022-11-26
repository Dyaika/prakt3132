package com.company;

public final class Drink extends MenuItem implements Alcoholable {
    private double alcoholVol;
    DrinkTypeEnum type;
    public Drink(int cost, String name, String description, int alcoholVol, DrinkTypeEnum type) {
        super(cost, name, description);
        this.type = type;
        this.alcoholVol = alcoholVol;
    }
    public Drink(int price, String name, String description, DrinkTypeEnum type) {
        super(price, name, description);
        this.alcoholVol = 0;
        this.type = type;
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
