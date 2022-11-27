package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RestaurantApp extends JFrame {
    private static final Font fnt = new Font("Calibri",Font.BOLD,20);
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
            new Drink(70, "Чай", "Настоящий чай", DrinkTypeEnum.GREEN_TEA),
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

        public OrderView(Order order) {
            this.order = order;
            this.button = new JButton(String.valueOf(order.getId()));
            System.out.println("добавлен заказ #" + order.getId());
            this.button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JPanel par = (JPanel)button.getParent();
                    if (isInternetOrder){
                        if (order.getId() == ((InternetOrdersManager)ordersManager).order().getId()){
                            System.out.println("удален заказ #" + order.getId());
                            button.setText("Удален");
                            par.remove(button);
                            ((InternetOrdersManager)ordersManager).remove();
                        } else {
                            JOptionPane.showMessageDialog(null, "Можно удалить только самый ранний заказ");
                            System.out.println("Самый ранний #" + ((InternetOrdersManager)ordersManager).order().getId() + "; текущий #" + order.getId());
                        }
                    } else {
                        button.setText("Удален");
                        par.remove(button);
                        System.out.println("удален заказ #" + ((TableOrder)order).getId());
                        ((TableOrdersManager)ordersManager).remove(order);
                    }
                    par.repaint();
                    par.setVisible(true);
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

    //конструктор
    public RestaurantApp(){
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
            }
        });
    }

    //добавление интернет-заказов
    private void addInternetOrderButton(Order order){
        OrderView cur = new OrderView(order);
        ((InternetOrdersManager)ordersManager).add(order);
        orderCount++;
        //order_buttons.add(cur);
        for_orders.add(cur.button);
        cur.button.repaint();
        for_orders.repaint();
        for_orders.getParent().repaint();
        for_orders.getParent().getParent().repaint();
        repaint();
        setVisible(true);

    }

    //заглушка добавления заказов в заведении
    private void addTableOrderButton(Order order){

        OrderView cur = new OrderView(order);
        ((TableOrdersManager)ordersManager).add(order, ((TableOrdersManager)ordersManager).freeTableNumber());
        orderCount++;
        //order_buttons.add(cur);
        for_orders.add(cur.button);
        cur.button.repaint();
        for_orders.repaint();
        for_orders.getParent().repaint();
        for_orders.getParent().getParent().repaint();
        repaint();
        setVisible(true);
    }

    //интернет заказы
    private void internetOrdersManagerView(){
        container.removeAll();
        container.setVisible(false);
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
        JLabel is_mature_label = new JLabel("Сколько вам лет?");
        JTextField is_mature = new JTextField();
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
        container.setVisible(true);


        container.add(submit, BorderLayout.SOUTH);
        container.add(container1, BorderLayout.NORTH);
        setVisible(true);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (firstname.getText().length() > 0 && lastname.getText().length() > 0 && is_mature.getText().length() > 0 &&
                        city.getText().length() > 0 && zip.getText().length() > 0 && street.getText().length() > 0 &&
                        number.getText().length() > 0 && letter.getText().length() > 0 && apartment.getText().length() > 0){
                    if (isNumber(number.getText()) && isNumber(zip.getText()) && letter.getText().length() == 1 && isNumber(is_mature.getText())){
                        Customer customer = new Customer(
                                firstname.getText(),
                                lastname.getText(),
                                Integer.parseInt(is_mature.getText()),
                                new Address(
                                        city.getText(),
                                        Integer.parseInt(zip.getText()),
                                        street.getText(),
                                        Integer.parseInt(number.getText()),
                                        letter.getText().charAt(0),
                                        Integer.parseInt(apartment.getText())));
                        Order order = new InternetOrder();
                        order.setCustomer(customer);
                        //((InternetOrdersManager)ordersManager).add(order);//!!!
                        fillOrder(order);
                    } else {
                        JOptionPane.showMessageDialog(null, "Проверьте корректность ввода");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Заполните все поля");
                }
            }
        });
    }

    //в заведении заказы
    private void tableOrdersManagerView(){
        container.removeAll();
        container.setVisible(false);
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
        container.setVisible(true);
        setVisible(true);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((TableOrdersManager)ordersManager).freeTableNumber() != -1){
                    Customer customer;
                    if (is_mature.isSelected()){
                        customer = Customer.MATURE_UNKNOWN_CUSTOMER;
                    } else {
                        customer = Customer.NOT_MATURE_UNKNOWN_CUSTOMER;
                    }
                    Order order = new TableOrder();
                    order.setCustomer(customer);
                    JOptionPane.showMessageDialog(null, "Ваш столик №" + ((TableOrdersManager)ordersManager).freeTableNumber());
                    //((InternetOrdersManager)ordersManager).add(order);//!!!
                    fillOrder(order);
                } else {
                    JOptionPane.showMessageDialog(null, "Подождите, пока столик освободится");
                }

            }
        });
    }

    //проверка на ввод числа
    private static boolean isNumber(String str){
        boolean res = false;
        if (str.length() > 0){
            int n = str.length();
            for (int i = 0; i < n; i++){
                if (str.charAt(i) < '0' || str.charAt(i) > '9'){
                    return res;
                }
            }
            res = true;
        }
        return res;
    }

    //создает меню выбора продуктов и корзину
    private void fillOrder(Order order){
        JPanel container1 = new JPanel(new GridLayout(1,2));
        JPanel menu_items_panel = new JPanel(new GridLayout(15, 1));
        JPanel cart_items_panel = new JPanel(new GridLayout(15, 1));

        JLabel temp_label = new JLabel("Меню");
        temp_label.setFont(RestaurantApp.fnt);
        menu_items_panel.add(temp_label);
        menu_items_panel.setBackground(Color.CYAN);
        temp_label = new JLabel("Корзина");
        temp_label.setFont(RestaurantApp.fnt);
        cart_items_panel.add(temp_label);
        cart_items_panel.setBackground(Color.PINK);
        JScrollPane scrollPane1 = new JScrollPane(menu_items_panel);
        container1.add(scrollPane1);
        JScrollPane scrollPane2 = new JScrollPane(cart_items_panel);
        container1.add(scrollPane2);
        container.removeAll();
        container.setLayout(new BorderLayout());
        container.add(container1, BorderLayout.NORTH);
        JButton make_order_button = new JButton("Заказать");
        container.add(make_order_button, BorderLayout.SOUTH);
        fillMenu((!isInternetOrder && order.getCustomer().getAge() >= 18), menu_items_panel, cart_items_panel, order, this);
        updateCart(cart_items_panel, order, this);
        make_order_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (order.costTotal() == 0){
                    JOptionPane.showMessageDialog(null, "Жаль что вы ушли, так и не завершив заказ :-(");
                    if (isInternetOrder){
                        internetOrdersManagerView();
                    } else {
                        tableOrdersManagerView();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Спасибо за заказ, оплатите " + order.costTotal() + "₽");
                    if (isInternetOrder){
                        addInternetOrderButton(order);
                        internetOrdersManagerView();
                    } else {
                        addTableOrderButton(order);
                        tableOrdersManagerView();
                    }
                }
            }
        });
    }

    //заполняем меню доступными item
    private static void fillMenu(boolean include_alco, JPanel menu, JPanel cart, Order order, Component all_content){
        for (MenuItem item:
             RestaurantApp.dish) {
            addItemToMenu(item, menu, cart, order, all_content);
        }
        for (MenuItem item:
                RestaurantApp.non_alco) {
            addItemToMenu(item, menu, cart, order, all_content);
        }
        if (include_alco){
            System.out.println("Заказ совершает совершеннолетний");
            for (MenuItem item:
                    RestaurantApp.alco) {
                addItemToMenu(item, menu, cart, order, all_content);
            }
        }
    }

    //добавляет item в меню
    private static void addItemToMenu(MenuItem item, JPanel menu, JPanel cart, Order order, Component all_content){

        JPanel menu_item = new JPanel(new BorderLayout());
        JButton plus = new JButton("+");
        JButton minus = new JButton("-");
        JLabel item_name = new JLabel(item.getName() + " " + item.getCost() + "₽");
        plus.setBackground(Color.GREEN);
        minus.setBackground(Color.RED);
        menu_item.add(minus, BorderLayout.WEST);
        menu_item.add(item_name, BorderLayout.CENTER);
        menu_item.add(plus, BorderLayout.EAST);
        menu.add(menu_item);
        all_content.setVisible(true);
        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order.add(item);
                updateCart(cart, order, all_content);
            }
        });
        minus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order.remove(item.getName());
                updateCart(cart, order, all_content);
            }
        });
    }

    //наполняет корзину тем что в заказе
    private static void updateCart(JPanel cart, Order order, Component all_content){
        MenuItem[] positions = order.sortedItemsByCostDesc();
        cart.setVisible(false);
        cart.removeAll();
        cart.setVisible(true);
        all_content.setVisible(true);
        JLabel cart_label = new JLabel("Корзина");
        cart_label.setFont(RestaurantApp.fnt);
        cart.add(cart_label);
        if (positions.length == 0){
            cart.add(new JLabel("Пусто"));
        } else {
            //System.out.println();
            for (MenuItem position:
                 positions) {
                cart.add(new JLabel(position.getName() + " " + order.itemQuantity(position.getName()) + "шт " + order.itemQuantity(position.getName()) * position.getCost() + "₽"));
                //System.out.println(position.getName() + " " + order.itemQuantity(position.getName()) + "шт " + order.itemQuantity(position.getName()) * position.getCost() + "₽");
            }
        }
        cart.add(new JLabel("Всего " + order.costTotal() + "₽"));
        cart.getParent().setVisible(true);
        all_content.setVisible(true);
    }

}
