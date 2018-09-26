package ee.cs.ut.wad2018.viinavaatlus.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SellerDTO {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String email;
    private String website;
    private MultipartFile imageFile;

}
