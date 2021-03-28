package mk.finki.ukim.wp.aud.service.impl;

import mk.finki.ukim.wp.aud.jpa.ShoppingCartRepository;
import mk.finki.ukim.wp.aud.jpa.UserRepository;
import mk.finki.ukim.wp.aud.model.Product;
import mk.finki.ukim.wp.aud.model.ShoppingCart;
import mk.finki.ukim.wp.aud.model.User;
import mk.finki.ukim.wp.aud.model.enumerations.ShoppingCartStatus;
import mk.finki.ukim.wp.aud.model.exceptions.ProductAlreadyInShoppingCartException;
import mk.finki.ukim.wp.aud.model.exceptions.ProductNotFoundException;
import mk.finki.ukim.wp.aud.model.exceptions.ShoppingCartNotFoundException;
import mk.finki.ukim.wp.aud.model.exceptions.UserNotFoundException;
import mk.finki.ukim.wp.aud.service.ProductService;
import mk.finki.ukim.wp.aud.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductService productService;


    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   UserRepository userRepository,
                                   ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Override
    public List<Product> listAllProductsInShoppingCart(Long cartId) {
        if (!this.shoppingCartRepository.findById(cartId).isPresent()) {
            throw new ShoppingCartNotFoundException(cartId);
        }
        return this.shoppingCartRepository.findById(cartId).get().getProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository.findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);

        Product product = this.productService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if(shoppingCart.getProducts().stream().filter(r -> r.getId().equals(productId))
        .collect(Collectors.toList()).size() > 0)
            throw new ProductAlreadyInShoppingCartException(productId, username);

        shoppingCart.getProducts().add(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<Product> getAllProductsInUserActiveShoppingCard(String username) {
        ShoppingCart activeShoppingCart = this.getActiveShoppingCart(username);
        return activeShoppingCart.getProducts();
    }

    @Override
    public void changeStatusToFinishedOfActiveShoppingCart(String username) {
        ShoppingCart activeShoppingCart = this.getActiveShoppingCart(username);

        activeShoppingCart.setStatus(ShoppingCartStatus.FINISHED);
        shoppingCartRepository.save(activeShoppingCart);
    }

    @Override
    public ShoppingCart removeProductFromShoppingCart(String username, Long balloonId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);

        Product product = this.productService.findById(balloonId)
                .orElseThrow(() -> new ProductNotFoundException(balloonId));

        shoppingCart.getProducts().remove(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
