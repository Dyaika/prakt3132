package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class InternetOrder implements Order{
    private static int id_count = 1;
    private int id;
    public static class ListNode{
        public ListNode(ListNode next, MenuItem value) {
            this.next = next;
            this.value = value;
        }

        public ListNode next;
        public MenuItem value;
    }
    private int size;
    private Customer customer;
    ListNode head, tail;

    public InternetOrder() {
        id = id_count;
        id_count++;
        int size = 0;
        head = null;
        tail = null;
        customer = Customer.NOT_MATURE_UNKNOWN_CUSTOMER;
    }

    @Override
    public boolean add(MenuItem item) {
        if (item != null){
            if (head == null){
                head = new ListNode(null, item);
                tail = head;
            } else {
                head = new ListNode(head, item);
            }
            size++;
            return true;
        }
        return false;
    }

    @Override
    public String[] itemsNames() {
        HashMap<MenuItem, String> dif = new HashMap<>();
        ListNode cur = head;
        for (int i = 0; i < size; i++){
            dif.put(cur.value, cur.value.getName());
            cur = cur.next;
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
        return size;
    }

    @Override
    public int itemQuantity(String itemName) {
        int count = 0;
        ListNode cur = head;
        for (int i = 0; i < size; i++){
            if (cur.value.getName().equals(itemName)){
                count++;
                cur = cur.next;
            }
        }
        return count;
    }

    @Override
    public MenuItem[] getItems() {
        HashMap<MenuItem, String> dif = new HashMap<>();
        ListNode cur = head;
        for (int i = 0; i < size; i++){
            dif.put(cur.value, cur.value.getName());
            cur = cur.next;
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
        if (size > 0){
            if (size == 1){
                if (head.value.getName().equals(itemName)){
                    head = null;
                    tail = null;
                    size = 0;
                    return true;
                }
            } else {
                if (head.value.getName().equals(itemName)){
                    head = head.next;
                    size--;
                    return true;
                }
                ListNode last = head;
                ListNode cur = last.next;
                for (int i = 1; i < size; i++){
                    if (cur.value.getName().equals(itemName)){
                        last.next = cur;
                        size--;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean remove(MenuItem item) {
        if (item == null){
            return false;
        }
        return remove(item.getName());
    }

    @Override
    public int removeAll(String itemName) {
        int count = 0;
        while (remove(itemName)) {
            count++;
        }
        return count;
    }

    @Override
    public int removeAll(MenuItem item) {
        int count = 0;
        while (remove(item)) {
            count++;
        }
        return count;
    }

    @Override
    public int costTotal() {
        int cost = 0;
        ListNode cur = head;
        for (int i = 0; i < size; i++){
            cost+= cur.value.getCost();
            cur = cur.next;
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

    public int getId() {
        return id;
    }
}
