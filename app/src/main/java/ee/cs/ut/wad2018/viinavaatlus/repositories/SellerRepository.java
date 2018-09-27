package ee.cs.ut.wad2018.viinavaatlus.repositories;

import ee.cs.ut.wad2018.viinavaatlus.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    // SELECT * FROM sellers;
    List<Seller> findAll();

    @Query(value = "SELECT * FROM sellers WHERE id = :id", nativeQuery = true)
    Optional<Seller> findById(@Param("id") Long id);

    boolean existsByOwnerId(String ownerId);

}
