package mk.finki.ukim.wp.aud.web.controller;

import mk.finki.ukim.wp.aud.model.Order;
import mk.finki.ukim.wp.aud.model.Product;
import mk.finki.ukim.wp.aud.service.OrderService;
import mk.finki.ukim.wp.aud.service.ShoppingCartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;

    public OrderController(OrderService orderService, ShoppingCartService shoppingCartService) {
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/place")
    public String placeOrder(Model model, HttpServletRequest req) {
        String username = req.getRemoteUser();
        List<Product> products = shoppingCartService.getAllProductsInUserActiveShoppingCard(username);

        if (products.isEmpty()) {
            model.addAttribute("hasError", true);
            return "redirect:/balloons?error=NoBalloonsToOrder";
        }

        model.addAttribute("products", products);

        model.addAttribute("bodyContent", "order-details");
        return "master-template";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/place")
    public String makeOrder(@RequestParam String deliveryAddress, Model model, HttpServletRequest req) {
        String username = req.getRemoteUser();
        List<Product> products = shoppingCartService.getAllProductsInUserActiveShoppingCard(username);


        if (products.isEmpty()) {
            model.addAttribute("hasError", true);
            return "redirect:/products?error=NoProductsToOrder";
        }

        orderService.placeOrder(username, deliveryAddress, products);

        return "redirect:/products";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/placed")
    public String getPlacedOrdersForCurrentUser(Model model, HttpServletRequest req) {
        String username = req.getRemoteUser();

        List<Order> placedOrders = orderService.getPlacedOrdersForUser(username);

        if (placedOrders.isEmpty()) {
            model.addAttribute("hasError", true);
            return "redirect:/products?error=No placed orders";
        }

        model.addAttribute("orders", placedOrders);


        model.addAttribute("bodyContent", "orders-placed");
        return "master-template";

    }
}
