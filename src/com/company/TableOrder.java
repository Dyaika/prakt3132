package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class TableOrder implements Order{
    private int size;
    private MenuItem[] items;
    private Customer customer;


    @Override
    public boolean add(MenuItem item) {
        for (int i = 0; i < size; i++){
            if (items[i] == null){
                items[i] = item;
                return true;
            }
        }
        return false;
    }

    @Override
    public String[] itemsNames() {
        HashMap<MenuItem, String> dif = new HashMap<>();
        for (int i = 0; i < size; i++){
            if (items[i] != null){
                dif.put(items[i], items[i].getName());
            }
        }
        ArrayList<String> pre_res = new ArrayList<>(dif.values());
        String[] res = new String[pre_res.size()];
        for (int i = 0; i < pre_res.size(); i++){
            res[i] = pre_res.get(i);
        }
        return res;
    }

    @Override
    public int itemsQuantity() {
        int count = 0;
        for (int i = 0; i < size; i++){
            if (items[i] != null){
                count++;
            }
        }
        return count;
    }

    @Override
    public int itemQuantity(String itemName) {
        int count = 0;
        for (int i = 0; i < size; i++){
            if (items[i] != null && items[i].getName().equals(itemName)){
                count++;
            }
        }
        return count;
    }

    @Override
    public MenuItem[] getItems() {
        HashMap<MenuItem, String> dif = new HashMap<>();
        for (int i = 0; i < size; i++){
            if (items[i] != null){
                dif.put(items[i], items[i].getName());
            }

        }
        ArrayList<MenuItem> pre_res = new ArrayList<>(dif.keySet());
        MenuItem[] res = new MenuItem[pre_res.size()];
        for (int i = 0; i < pre_res.size(); i++){
            res[i] = pre_res.get(i);
        }
        return res;
    }

    @Override
    public boolean remove(String itemName) {
        for (int i = 0; i < size; i++){
            if (items[i] != null && items[i].getName().equals(itemName)){
                items[i] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(MenuItem item) {
        for (int i = 0; i < size; i++){
            if (items[i] != null && items[i].equals(item)){
                items[i] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public int removeAll(String itemName) {
        int count = 0;
        for (int i = 0; i < size; i++){
            if (items[i] != null && items[i].getName().equals(itemName)){
                items[i] = null;
                count++;
            }
        }
        return count;
    }

    @Override
    public int removeAll(MenuItem item) {
        int count = 0;
        for (int i = 0; i < size; i++){
            if (items[i] != null && items[i].equals(item)){
                items[i] = null;
                count++;
            }
        }
        return count;
    }

    @Override
    public int costTotal() {
        int cost = 0;
        for (MenuItem i:
             items) {
            if (i != null){
                cost += i.getCost();
            }
        }
        return cost;
    }

    @Override
    public MenuItem[] sortedItemsByCostDesc() {
        MenuItem[] res = getItems();
        int n = res.length;
        if (n > 0){
            MenuItem cur;
            for (int i = 1; i < n; i++){
                cur = res[i];
                int j = i - 1;
                while (j >= 0 && cur.compareTo(res[j]) < 0){
                    res[j + 1] = res[j];
                    j--;
                }
                res[j+1] = cur;
            }
        }
        return res;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public TableOrder() {
        this.size = 100;
        items = new MenuItem[size];
        this.customer = Customer.NOT_MATURE_UNKNOWN_CUSTOMER;
    }

    public TableOrder(int size, Customer customer) {
        this.size = size;

        items = new MenuItem[size];
        this.customer = customer;
    }

    public TableOrder(int size) {
        this.size = size;
        items = new MenuItem[size];
        customer = Customer.NOT_MATURE_UNKNOWN_CUSTOMER;
    }
}
