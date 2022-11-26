package com.company;

public class Main {
    public static void tableOrder(){
        TableOrdersManager om = new TableOrdersManager();
        Order o = new TableOrder();
        o.add(new Drink(12, "Cola", "Cocacolaslal", DrinkTypeEnum.JUICE));
        o.add(new Dish(1, "Ca", "Cocacl"));
        o.add(new Drink(2, "a", "olaslal", DrinkTypeEnum.JUICE));
        om.add(o, 0);
        o = om.getOrder(0);
        for (String s:
                o.itemsNames()) {
            System.out.println(s);
        }
        o.add(new Drink(12, "Cola", "Cocacolaslal", DrinkTypeEnum.JUICE));
        System.out.println("Цена всего " + o.costTotal());
        o.remove("Cola");
        System.out.println("Цена всего " + o.costTotal());
    }
    public static void intOrder(){
        InternetOrdersManager om = new InternetOrdersManager();
        Order o = new InternetOrder();
        o.add(new Drink(12, "Cola", "Cocacolaslal", DrinkTypeEnum.JUICE));
        o.add(new Dish(1, "Kuriza", "Cocasdsdscl"));
        o.add(new Drink(2, "Baikal", "olaslal", DrinkTypeEnum.JUICE));
        System.out.println("Заказ:");
        for (String s:
                o.itemsNames()) {
            System.out.println(s);
        }
        System.out.println("Количество в заказе: " + o.itemsQuantity());
        System.out.println("Стоимость: " + o.costTotal());
        om.add(o);
        o = om.order();
        for (String s:
                o.itemsNames()) {
            System.out.println(s);
        }
        o.add(new Drink(12, "Cola", "Cocacolaslal", DrinkTypeEnum.JUICE));
        System.out.println("Цена всего " + o.costTotal());
        o.remove("Cola");
        System.out.println("Цена всего " + o.costTotal());
    }
    public static void main(String[] args) {
        new RestourantApp();
    }
}
