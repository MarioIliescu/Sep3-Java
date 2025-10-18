package dk.via.fleetforward.services.company;
import dk.via.fleetforward.gRPC.Fleetforward.CompanyProto;
import dk.via.fleetforward.model.Company;
import dk.via.fleetforward.repositories.database.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * @author Mario
 * @version 1.0.0
 * Company service implementation for database operations
 * @implNote This class is a Spring component and is instantiated by Spring<br>
 * This class is transactional (makes changes to the database)
 * @see CompanyService
 */
@Service("database")
public class CompanyServiceDatabase implements CompanyService{

    private final CompanyRepository companyRepository;

    /**
     * Constructor
     * @param companyRepository The company repository for database operations using Spring Data JPA
     */
    public CompanyServiceDatabase(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    /**
     * {@inheritDoc}
     *
     * @param payload {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Transactional
    public CompanyProto create(CompanyProto payload) {
        Company company = new Company();
        company.setMcNumber(payload.getMcNumber());
        company.setCompanyName(payload.getCompanyName());
        Company created = companyRepository.save(company);
        return CompanyProto.newBuilder()
                .setId(created.getId())
                .setMcNumber(created.getMcNumber())
                .setCompanyName(created.getCompanyName()).build();
    }
    /**
     * {@inheritDoc}
     *
     * @param payload {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    @Transactional
    public CompanyProto update(CompanyProto payload) {
        Company existing = companyRepository.findById(payload.getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        existing.setMcNumber(payload.getMcNumber());
        existing.setCompanyName(payload.getCompanyName());

        Company updated = companyRepository.save(existing);

        return CompanyProto.newBuilder()
                .setId(updated.getId())
                .setMcNumber(updated.getMcNumber())
                .setCompanyName(updated.getCompanyName())
                .build();
    }
    /**
     * {@inheritDoc}
     *
     * @param mcNumber {@inheritDoc}
     * @return {@inheritDoc}
     * @implNote Optional is in case the company is not found in the database to ensure null safety.
     */
    @Override
    public CompanyProto getSingle(String mcNumber) {
        Optional<Company> fetched = companyRepository.findByMcNumber(mcNumber); //null safety
        Company company = fetched.orElseThrow(() -> new RuntimeException("Company not found"));
        return CompanyProto.newBuilder()
                .setId(company.getId())
                .setMcNumber(company.getMcNumber())
                .setCompanyName(company.getCompanyName())
                .build();
    }
    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     * @implNote Optional is in case the company is not found in the database to ensure null safety.
     */
    @Override
    public CompanyProto getSingle(int id) {
        Optional<Company> fetched = companyRepository.findById(id); //null safety
        Company company = fetched.orElseThrow(() -> new RuntimeException("Company not found"));
        return CompanyProto.newBuilder()
                .setId(company.getId())
                .setMcNumber(company.getMcNumber())
                .setCompanyName(company.getCompanyName())
                .build();
    }
    /**
     * {@inheritDoc}
     *
     * @param mcNumber {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(String mcNumber) {
      companyRepository.deleteByMcNumber(mcNumber);
    }

    /**
     * {@inheritDoc}
     *
     * @param id {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(int id) {
       companyRepository.deleteById(id);
    }
    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Iterable<CompanyProto> getAll() {
        List<Company> companies = companyRepository.findAll();
        Iterable<CompanyProto> companiesProto;
        companiesProto = companies.stream().map(company -> CompanyProto.newBuilder()
                .setId(company.getId())
                .setMcNumber(company.getMcNumber())
                .setCompanyName(company.getCompanyName())
                .build()).toList();
        return companiesProto;
    }
}
