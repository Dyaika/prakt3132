package com.company;

public interface Order{
    boolean add(MenuItem item);
    String[] itemsNames();
    int itemsQuantity();
    int itemQuantity(String itemName);
    MenuItem[] getItems();
    boolean remove(String itemName);
    boolean remove(MenuItem item);
    int removeAll(String itemName);
    int removeAll(MenuItem item);
    int costTotal();
    MenuItem[] sortedItemsByCostDesc();
    Customer getCustomer();
    void setCustomer(Customer customer);
}
