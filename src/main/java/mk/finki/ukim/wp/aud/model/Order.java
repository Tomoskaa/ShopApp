package mk.finki.ukim.wp.aud.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "products_orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    private String deliveryAddress;

    private LocalDateTime timestamp;

    @ManyToMany
    private List<Product> products;


    public Order(User user, String deliveryAddress) {
        this.user = user;
        this.timestamp = LocalDateTime.now();
        this.deliveryAddress = deliveryAddress;
        this.products = new ArrayList<>();
    }
}