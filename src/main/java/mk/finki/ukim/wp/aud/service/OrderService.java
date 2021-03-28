package mk.finki.ukim.wp.aud.service;

import mk.finki.ukim.wp.aud.model.Order;
import mk.finki.ukim.wp.aud.model.Product;

import java.util.List;

public interface OrderService {

    Order placeOrder(String username, String deliveryAddress, List<Product> products);

    List<Order> getPlacedOrdersForUser(String username);

}