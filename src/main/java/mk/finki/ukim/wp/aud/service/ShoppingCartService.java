package mk.finki.ukim.wp.aud.service;

import mk.finki.ukim.wp.aud.model.Product;
import mk.finki.ukim.wp.aud.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    List<Product> listAllProductsInShoppingCart(Long cartId);

    ShoppingCart getActiveShoppingCart(String username);

    ShoppingCart addProductToShoppingCart(String username, Long productId);

    ShoppingCart removeProductFromShoppingCart(String username, Long productId);

    void changeStatusToFinishedOfActiveShoppingCart(String username);

    List<Product> getAllProductsInUserActiveShoppingCard(String username);
}
