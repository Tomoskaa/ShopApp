package mk.finki.ukim.wp.aud.service;

import mk.finki.ukim.wp.aud.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

    List<Manufacturer> findAll();

    Optional<Manufacturer> findById(Long id);

    Optional<Manufacturer> save(String name);

    List<Manufacturer> listAll();

    void deleteById(Long id);
}
