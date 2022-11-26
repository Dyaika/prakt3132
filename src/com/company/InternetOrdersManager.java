package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class InternetOrdersManager implements OrdersManager{
    public class QueueNode{
        public QueueNode next;
        public QueueNode prev;
        public Order value;

        public QueueNode(QueueNode next, QueueNode prev, Order value) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
    }
    private QueueNode head;
    private QueueNode tail;
    private int size;

    public InternetOrdersManager() {
        size = 0;
        head = null;
        tail = null;
    }
    public boolean add(Order order){
        if (order == null){
            return false;
        }
        if (size == 0){
            head = new QueueNode(null, null, order);
            tail = head;
        }
        else {
            QueueNode temp = tail;
            tail = new QueueNode(null, tail, order);
            temp.next = tail;
        }
        size++;
        return true;
    }

    public Order remove(){
        Order res = null;
        if (size > 0){
            if (size == 1){
                res = head.value;
                head = null;
                tail = null;
                size = 0;
            } else {
                res = head.value;
                head = head.next;
                head.prev = null;
                size--;
            }
        }
        return res;
    }
    public Order order(){
        Order res = null;
        if (size > 0){
            res = head.value;
        }
        return res;
    }

    @Override
    public int itemsQuantity(String itemName) {
        int count = 0;
        QueueNode cur = head;
        for (int i = 0; i < size; i++){
            if (cur.value != null){
                count += cur.value.itemQuantity(itemName);
            }
            cur = cur.next;
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
        QueueNode cur = head;
        for (int i = 0; i < size; i++){
            if (cur.value != null){
                dif.put(cur.value, cur.value.getCustomer());
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
        QueueNode cur = head;
        for (int i = 0; i < size; i++){
            if (cur.value != null){
                cost += cur.value.costTotal();
            }
        }
        return cost;
    }

    @Override
    public int orderQuantity() {
        int count = 0;
        QueueNode cur = head;
        for (int i = 0; i < size; i++){
            if (cur.value != null){
                count += cur.value.itemsQuantity();
            }
        }
        return count;
    }
}
