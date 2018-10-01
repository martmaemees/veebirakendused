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

    @Query(value = "SELECT COUNT(*) FROM sellers", nativeQuery = true)
    int countSellers();

    /**
     * Executes an INSERT statement in the database, saving the {@link Seller} to the sellers table.
     * If the Seller has a {@link ee.cs.ut.wad2018.viinavaatlus.entities.SellerImage} attached to it that isn't saved,
     * also executes an INSERT statement for that.
     *
     * SQL: insert into sellers (address, description, email, image_id, name, owner_id, phone, website)
     *      values (?, ?, ?, ?, ?, ?, ?, ?)
     * @return Returns the saved Seller entity with updated data.
     */
    @Override
    <S extends Seller> S saveAndFlush(S entity);

    boolean existsByOwnerId(String ownerId);

}
