package dk.via.fleetforward.services.company;

import dk.via.fleetforward.gRPC.Fleetforward;
import dk.via.fleetforward.gRPC.Fleetforward.CompanyProto;
import org.springframework.stereotype.Service;

/**
 * @author Mario
 * @version 1.0.0
 */
@Service
public interface CompanyService {
    //CRUD operations
    /**
     * Create a new company
     * @param payload The company to create
     * @return The created company
     */
    CompanyProto create(CompanyProto payload);

    /**
     * Update an existing company
     * @param payload The company to update
     * @return The updated company
     */
    CompanyProto update(CompanyProto payload);

    /**
     * Get a single company by its mcNumber
     * @param mcNumber The mcNumber of the company to get
     * @return The company or null if not found
     */
    CompanyProto getSingle(String mcNumber);

    /**
     * Get a single company by its id
     * @param id The id of the company to get
     * @return The company or null if not found
     */
    CompanyProto getSingle(int id);

    /**
     * Delete a company by its mcNumber
     * @param mcNumber The mcNumber of the company to delete
     */
    void delete(String mcNumber);

    /**
     * Delete a company by its id
     * @param id The id of the company to delete
     */
    void delete(int id);

    /**
     * Get all companies
     * @return An iterable of companies
     */
    Fleetforward.CompanyProtoList getAll();
}

