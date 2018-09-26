package ee.cs.ut.wad2018.viinavaatlus.repositories;

import ee.cs.ut.wad2018.viinavaatlus.entities.Seller;
import ee.cs.ut.wad2018.viinavaatlus.entities.SellerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SellerImageRepository extends JpaRepository<SellerImage, Long> {

    // FIXME: This needs to not pull every Seller detail.
    @Query(value = "SELECT img FROM SellerImage img JOIN img.seller s WHERE s.id = :seller_id")
    Optional<SellerImage> findBySellerId(@Param("seller_id") Long sellerId);

}
