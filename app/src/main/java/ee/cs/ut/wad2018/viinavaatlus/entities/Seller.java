package ee.cs.ut.wad2018.viinavaatlus.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity to represent the data of sellers.
 */
@SuppressWarnings("checkstyle:magicnumber")
@Getter
@Setter
@Entity
@Table(name = "sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 255)
    @NotNull
    private String name;

    @Lob
    private String description;

    @Length(max = 500)
    private String address;

    @Length(max = 255)
    private String phone;

    @Length(max = 255)
    private String email;

    @Length(max = 255)
    private String website;

}
