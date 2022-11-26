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
    private JLabel in_cur_order;
    private OrdersManager ordersManager;
    private boolean isInternetOrder = false;
    private ArrayList<OrderView> order_buttons;
    private static Dish[] dish = {
            new Dish(120, "Курица", "Настоящая курица"),
            new Dish(50, "Яблоко", "Настоящее яблоко"),
            new Dish(40, "Апельсин", "Настоящий апельсин"),
            new Dish(400, "Торт", "Настоящий торт"), };
    private static Drink[] non_alco = {
            new Drink(90, "Сок", "Настоящий сок", DrinkTypeEnum.JUICE),
            new Drink(100, "Кофе", "Настоящий кофе", DrinkTypeEnum.COFFEE),
            new Drink(70, "Чай зеленый", "Настоящий чай", DrinkTypeEnum.GREEN_TEA),
            new Drink(90, "Молоко", "Настоящее молоко", DrinkTypeEnum.MILK)};
    private static Drink[] alco = {
            new Drink(90, "Пиво", "Настоящее пиво", 5, DrinkTypeEnum.BEER),
            new Drink(300, "Вино", "Настоящее вино", 1, DrinkTypeEnum.WINE),//саша помоги какие тут градусы
            new Drink(400, "Водка", "Подделка", 40, DrinkTypeEnum.VODKA),
            new Drink(500, "Бренди", "Настоящий бренди", 1, DrinkTypeEnum.BRANDY)};
    //класс для отображения заказов
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
                internetOrdersManagerView();
            }
        });
        tableO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordersManager = new TableOrdersManager();
                container.removeAll();
                container.repaint();
                isInternetOrder = false;
                tableOrdersManagerView();
                addTableOrdersTest();
            }
        });
    }

    //заглушка добавления интернет-заказов
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
            cur.repaint();
            for_orders.repaint();
            for_orders.getParent().repaint();
            for_orders.getParent().getParent().repaint();
            repaint();
            setVisible(true);
        }
    }

    //заглушка добавления заказов в заведении
    private void addTableOrdersTest(){
        JButton cur;
        for (int i = 0; i < 5; i++){
            ((TableOrdersManager)ordersManager).add(new TableOrder(), i);
            orderCount++;
            order_buttons.add(new OrderView(((TableOrdersManager)ordersManager).getOrder(i), orderCount));
            cur = order_buttons.get(i).getButton();
            for_orders.add(cur);
            cur.repaint();
            for_orders.repaint();
            for_orders.getParent().repaint();
            for_orders.getParent().getParent().repaint();
            repaint();
            setVisible(true);
        }
    }

    //интернет заказы
    private void internetOrdersManagerView(){
        container.setLayout(new BorderLayout());
        JButton submit = new JButton("Подтвердить");

        JPanel container1 = new JPanel(new GridLayout(9,2));
        JLabel firstname_label = new JLabel("Имя");
        JTextField firstname = new JTextField();
        container1.add(firstname_label);
        container1.add(firstname);
        JLabel lastname_label = new JLabel("Фамилия");
        JTextField lastname = new JTextField();
        container1.add(lastname_label);
        container1.add(lastname);
        JLabel is_mature_label = new JLabel("Вам больше 18?");
        JCheckBox is_mature = new JCheckBox();
        container1.add(is_mature_label);
        container1.add(is_mature);
        JLabel city_label = new JLabel("Город");
        JTextField city = new JTextField();
        container1.add(city_label);
        container1.add(city);
        JLabel zip_label = new JLabel("Индекс");
        JTextField zip = new JTextField();
        container1.add(zip_label);
        container1.add(zip);
        JLabel street_label = new JLabel("Улица");
        JTextField street = new JTextField();
        container1.add(street_label);
        container1.add(street);
        JLabel number_label = new JLabel("Номер дома");
        JTextField number = new JTextField();
        container1.add(number_label);
        container1.add(number);
        JLabel letter_label = new JLabel("Корпус");
        JTextField letter = new JTextField();
        container1.add(letter_label);
        container1.add(letter);
        JLabel apartment_label = new JLabel("Квартира");
        JTextField apartment = new JTextField();
        container1.add(apartment_label);
        container1.add(apartment);



        container.add(submit, BorderLayout.SOUTH);
        container.add(container1, BorderLayout.NORTH);
        //container1.repaint();
        //container.repaint();
        //repaint();
        setVisible(true);
    }

    //в заведении заказы
    private void tableOrdersManagerView(){
        container.setLayout(new BorderLayout());
        JButton submit = new JButton("Подтвердить");

        JPanel container1 = new JPanel(new GridLayout(1,2));
        JLabel is_mature_label = new JLabel("Вам больше 18?");
        JCheckBox is_mature = new JCheckBox();
        container1.add(is_mature_label);
        container1.add(is_mature);



        container.add(submit, BorderLayout.SOUTH);
        container.add(container1, BorderLayout.NORTH);
        //container1.repaint();
        //container.repaint();
        //repaint();
        setVisible(true);
    }
}
