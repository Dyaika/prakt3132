package com.company;

public interface OrdersManager {
    int itemsQuantity(String itemName);
    int itemsQuantity(MenuItem item);
    Order[] getOrders();
    int orderCostSummary();
    int orderQuantity();
}
