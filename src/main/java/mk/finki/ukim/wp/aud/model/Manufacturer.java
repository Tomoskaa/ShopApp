package mk.finki.ukim.wp.aud.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "manufacturers")
public class Manufacturer {

    @Id
    private Long id;

    private String name;

    public Manufacturer() {
    }

    public Manufacturer(String name) {
        this.name = name;
    }
}
