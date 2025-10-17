package dk.via.fleetforward.repositories.database;

import dk.via.fleetforward.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer>{
    Optional<Company> findByMcNumber(String mcNumber);
    void deleteByMcNumber(String mcNumber);
}
