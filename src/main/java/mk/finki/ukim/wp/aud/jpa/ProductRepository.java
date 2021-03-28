package mk.finki.ukim.wp.aud.jpa;


import mk.finki.ukim.wp.aud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    void deleteByName(String name);

    List<Product> findAllByNameIgnoreCaseContaining(String name);
}
