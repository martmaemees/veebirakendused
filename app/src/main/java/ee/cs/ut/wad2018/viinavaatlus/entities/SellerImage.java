package ee.cs.ut.wad2018.viinavaatlus.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "sellers_images")
public class SellerImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 500)
    @NotNull
    private String fileName;

    @NotNull
    private byte[] fileBody;

    @NotNull
    private String fileType;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "image")
    private Seller seller;

    public SellerImage(MultipartFile imageFile) throws IOException {
        if (!imageFile.getContentType().equals(MediaType.IMAGE_JPEG_VALUE)
                && !imageFile.getContentType().equals(MediaType.IMAGE_PNG_VALUE)) {
            throw new InvalidMimeTypeException(imageFile.getContentType(), "Only PNG and JPEG files are supported.");
        }

        this.fileName = imageFile.getName();
        this.fileBody = imageFile.getBytes();
        this.fileType = imageFile.getContentType();
    }
}
