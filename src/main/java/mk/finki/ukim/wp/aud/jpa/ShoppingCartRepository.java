package mk.finki.ukim.wp.aud.jpa;


import mk.finki.ukim.wp.aud.model.ShoppingCart;
import mk.finki.ukim.wp.aud.model.User;
import mk.finki.ukim.wp.aud.model.enumerations.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus status);

}
