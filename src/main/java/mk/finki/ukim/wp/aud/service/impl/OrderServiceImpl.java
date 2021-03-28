package mk.finki.ukim.wp.aud.service.impl;


import mk.finki.ukim.wp.aud.jpa.OrderRepository;
import mk.finki.ukim.wp.aud.jpa.UserRepository;
import mk.finki.ukim.wp.aud.model.Order;
import mk.finki.ukim.wp.aud.model.Product;
import mk.finki.ukim.wp.aud.model.User;
import mk.finki.ukim.wp.aud.model.exceptions.InvalidAddressException;
import mk.finki.ukim.wp.aud.service.OrderService;
import mk.finki.ukim.wp.aud.service.ShoppingCartService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ShoppingCartService shoppingCartService,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Order placeOrder(String username, String deliveryAddress, List<Product> products) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (deliveryAddress.isEmpty()) {
            throw new InvalidAddressException();
        }

        Order order = new Order(user, deliveryAddress);
        order.getProducts().addAll(products);

        orderRepository.save(order);
        shoppingCartService.changeStatusToFinishedOfActiveShoppingCart(user.getUsername());
        return order;
    }

    @Override
    public List<Order> getPlacedOrdersForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return orderRepository.findAllByUser(user);
    }

}
