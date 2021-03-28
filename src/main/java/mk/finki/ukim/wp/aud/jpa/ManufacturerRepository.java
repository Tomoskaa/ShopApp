package mk.finki.ukim.wp.aud.jpa;

import mk.finki.ukim.wp.aud.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

}
