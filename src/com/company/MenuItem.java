package com.company;

public class MenuItem {
    private int cost;
    private String name;
    private String description;
    public MenuItem(int cost, String name, String description) {
        this.cost = cost;
        this.name = name;
        this.description = description;
    }

    public int getCost(){
        return cost;
    }
    public String getName(){
        return name;
    }
    String getDescription(){
        return description;
    }
    public int compareTo(MenuItem menuItem){
        if (getCost() == menuItem.getCost()) {
            return getName().compareTo(menuItem.getName());
        } else if (getCost() > menuItem.getCost()){
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null){
            if (name.equals(((MenuItem)obj).getName())){
                if (description.equals(((MenuItem)obj).getDescription())){
                    if (cost == ((MenuItem)obj).getCost()){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
