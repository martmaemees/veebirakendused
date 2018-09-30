package ee.cs.ut.wad2018.viinavaatlus.dtos;

import ee.cs.ut.wad2018.viinavaatlus.entities.Seller;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class SellerDTO {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String email;
    private String website;
    private MultipartFile imageFile;

    public SellerDTO(Seller entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.address = entity.getAddress();
        this.phone = entity.getPhone();
        this.email = entity.getEmail();
        this.website = entity.getWebsite();
    }
}
