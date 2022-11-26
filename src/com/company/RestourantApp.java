package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RestourantApp extends JFrame {
    private static int orderCount = 0;
    private JPanel for_orders, for_choice;
    private JScrollPane scr_for_orders;
    private JPanel container;
    private OrdersManager ordersManager;
    private boolean isInternetOrder = false;
    private ArrayList<OrderView> order_buttons;
    public class OrderView{
        private Order order;
        private int number;
        private JButton button;

        public OrderView(Order order, int number) {
            this.order = order;
            this.number = number;
            this.button = new JButton(String.valueOf(this.number));
            if (isInternetOrder){
                System.out.println("добавлен заказ #" + ((InternetOrder)order).getId());
            } else {
                System.out.println("добавлен заказ #" + ((TableOrder)order).getId());
            }
            this.button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JPanel par = (JPanel)button.getParent();
                    if (isInternetOrder){
                        if (((InternetOrder)order).getId() == ((InternetOrder)((InternetOrdersManager)ordersManager).order()).getId()){
                            System.out.println("удален заказ #" + ((InternetOrder)order).getId());
                            button.setText("Удален");
                            par.remove(button);
                            ((InternetOrdersManager)ordersManager).remove();
                        } else {
                            JOptionPane.showMessageDialog(null, "Можно удалить только самый ранний заказ");
                        }
                    } else {
                        button.setText("Удален");
                        par.remove(button);
                        System.out.println("удален заказ #" + ((TableOrder)order).getId());
                        ((TableOrdersManager)ordersManager).remove(order);
                    }
                    par.repaint();
                }
            });
        }

        public Order getOrder() {
            return order;
        }

        public int getNumber() {
            return number;
        }

        public JButton getButton() {
            return button;
        }
    }
    public RestourantApp(){
        super("CFK");
        setLayout(new GridLayout(1, 2));
        order_buttons = new ArrayList<>();
        for_orders = new JPanel(new GridLayout(30, 1));
        for_orders.add(new JLabel("Заказы"));
        for_choice = new JPanel();
        scr_for_orders = new JScrollPane(for_orders);


        add(scr_for_orders);
        add(for_choice);
        internetOrTable();

        this.setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    //меню выбора типа менеджера заказов
    private void internetOrTable(){
        JButton internetO = new JButton("Интернет-заказ");
        JButton tableO = new JButton("Заказ в заведении");
        container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(new Color(222, 222, 0));
        container.add(internetO, BorderLayout.NORTH);
        container.add(tableO, BorderLayout.SOUTH);
        for_choice.add(container, CENTER_ALIGNMENT);
        internetO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordersManager = new InternetOrdersManager();
                container.removeAll();
                container.repaint();
                isInternetOrder = true;
                addInternetOrdersTest();
            }
        });
        tableO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordersManager = new TableOrdersManager();
                container.removeAll();
                container.repaint();
                isInternetOrder = false;
                addTableOrdersTest();
            }
        });
    }
    private void addInternetOrdersTest(){
        JButton cur;
        InternetOrder tempOrder;
        for (int i = 0; i < 5; i++){
            tempOrder = new InternetOrder();
            ((InternetOrdersManager)ordersManager).add(tempOrder);
            orderCount++;
            order_buttons.add(new OrderView(tempOrder, orderCount));
            cur = order_buttons.get(i).getButton();
            for_orders.add(cur);
            System.out.println(orderCount);
            cur.repaint();
            for_orders.repaint();
            for_orders.getParent().repaint();
            for_orders.getParent().getParent().repaint();
            repaint();
            setVisible(true);
        }
    }
    private void addTableOrdersTest(){
        JButton cur;
        for (int i = 0; i < 5; i++){
            ((TableOrdersManager)ordersManager).add(new TableOrder(), i);
            orderCount++;
            order_buttons.add(new OrderView(((TableOrdersManager)ordersManager).getOrder(i), orderCount));
            cur = order_buttons.get(i).getButton();
            for_orders.add(cur);
            System.out.println(orderCount);
            cur.repaint();
            for_orders.repaint();
            for_orders.getParent().repaint();
            for_orders.getParent().getParent().repaint();
            repaint();
            setVisible(true);
        }
    }
}
