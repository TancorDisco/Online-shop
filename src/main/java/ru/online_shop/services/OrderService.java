package ru.online_shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.online_shop.models.Order;
import ru.online_shop.models.Person;
import ru.online_shop.models.Product;
import ru.online_shop.repositories.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PersonService personService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService, PersonService personService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.personService = personService;
    }

    public void createOrder(Person person) {
        List<Product> productsInTheOrder = productService.findByPerson(person);
        Order order = new Order();
        order.setStatus("IN PROCESS");
        order.setPrice(productService.getTotalPrice(person));
        order.setDeliveryInfo("-");

        order.setPerson(person);
        person.getOrders().add(order);
        orderRepository.save(order);

        order.setProductsInTheOrder(productsInTheOrder);
        productsInTheOrder.forEach(product -> product.getOrders().add(order));
        orderRepository.save(order);

        personService.removeAllFromTheCart(person);
    }

    public List<Order> findAllByPerson(Person authUser) {
        return orderRepository.findAllByPerson(authUser);
    }
}
