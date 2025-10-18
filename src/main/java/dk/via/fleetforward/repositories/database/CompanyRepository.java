package dk.via.fleetforward.repositories.database;

import dk.via.fleetforward.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer>{
    Optional<Company> findByMcNumber(String mcNumber);
    void deleteByMcNumber(String mcNumber);
}
