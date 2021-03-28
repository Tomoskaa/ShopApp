package mk.finki.ukim.wp.aud.jpa;

import mk.finki.ukim.wp.aud.model.Order;
import mk.finki.ukim.wp.aud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
    List<Order> findAllByUserName(String username);
}
