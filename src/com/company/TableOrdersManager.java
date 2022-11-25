package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class TableOrdersManager implements OrdersManager{
    private Order[] orders;

    public TableOrdersManager() {
        orders = new TableOrder[10];
    }
    public void add(Order order, int tableNumber){
        if (orders[tableNumber] == null){
            orders[tableNumber] = order;
        }
    }
    public void addItem(MenuItem item, int tableNumber){
        if (orders[tableNumber] != null){
            orders[tableNumber].add(item);
        }
    }
    public int freeTableNumber(){
        int n = orders.length;
        for (int i = 0; i < n; i++){
            if (orders[i] == null){
                return i;
            }
        }
        return -1;
    }
    public int[] freeTableNumbers(){
        int n = orders.length;
        ArrayList<Integer> pre_res = new ArrayList<>();
        for (int i = 0; i < n; i++){
            if (orders[i] == null){
                pre_res.add(i);
            }
        }
        int[] res = new int[pre_res.size()];
        for (int i = 0; i < res.length; i++){
            res[i] = pre_res.get(i);
        }
        return res;
    }
    public Order getOrder(int tableNumber){
        return orders[tableNumber];
    }
    public void remove(int tableNumber){
        orders[tableNumber] = null;
    }
    public int remove(Order order){
        int n = orders.length;
        MenuItem[] to_del = order.sortedItemsByCostDesc();
        for(int i = 0; i < n; i++){
            if (orders[i] != null){
                MenuItem[] cur = orders[i].sortedItemsByCostDesc();
                if (order.getCustomer().equals(orders[i].getCustomer())){
                    if (order.costTotal() == orders[i].costTotal()){
                        if (to_del.length == cur.length){
                            orders[i] = null;
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }
    public int removeAll(Order order){
        int count = 0;
        while (remove(order) != -1){
            count++;
        }
        return count;
    }

    @Override
    public int itemsQuantity(String itemName) {
        int count = 0;
        for (int i = 0; i < orders.length; i++){
            if (orders[i] != null){
                count += orders[i].itemQuantity(itemName);
            }
        }
        return count;
    }

    @Override
    public int itemsQuantity(MenuItem item) {
        if (item != null){
            return itemsQuantity(item.getName());
        }
        return 0;
    }

    @Override
    public Order[] getOrders() {
        HashMap<Order, Customer> dif = new HashMap<>();
        for (int i = 0; i < orders.length; i++){
            if (orders[i] != null){
                dif.put(orders[i], orders[i].getCustomer());
            }

        }
        ArrayList<Order> pre_res = new ArrayList<>(dif.keySet());
        Order[] res = new Order[pre_res.size()];
        for (int i = 0; i < pre_res.size(); i++){
            res[i] = pre_res.get(i);
        }
        return res;
    }

    @Override
    public int orderCostSummary() {
        int cost = 0;
        for (int i = 0; i < orders.length; i++){
            if (orders[i] != null){
                cost += orders[i].costTotal();
            }
        }
        return cost;
    }

    @Override
    public int orderQuantity() {
        int count = 0;
        for (int i = 0; i < orders.length; i++){
            if (orders[i] != null){
                count += orders[i].itemsQuantity();
            }
        }
        return count;
    }
}
