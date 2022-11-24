package com.company;

public class Main {

    public static void main(String[] args) {
        TableOrdersManager om = new TableOrdersManager();
        Order o = new TableOrder();
        o.add(new Drink(12, "Cola", "Cocacolaslal"));
        o.add(new Dish(1, "Ca", "Cocacl"));
        o.add(new Drink(2, "a", "olaslal"));
        om.add(o, 0);
        o = om.getOrder(0);
        for (String s:
             o.itemsNames()) {
            System.out.println(s);
        }
    }
}
