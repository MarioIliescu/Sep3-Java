package dk.via.fleetforward.startup;

import dk.via.fleetforward.networking.handlers.CompanyHandler;
import dk.via.fleetforward.networking.handlers.FleetNetworkHandler;
import dk.via.fleetforward.repositories.database.CompanyRepository;
import dk.via.fleetforward.services.company.CompanyServiceDatabase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author Mario
 * @version 1.0.0
 * The ServiceProvider class is a Spring-managed service that acts as a provider for other services and handlers.
 * It provides instances of service classes and handlers through its methods, utilizing the Spring ApplicationContext
 * for dependency injection.
 */
@Service
public class ServiceProvider {
    public CompanyServiceDatabase getCompanyService(){
        return GlobalContext.getBean(CompanyServiceDatabase.class);
    }
    public FleetNetworkHandler getCompanyHandler(){
        return new CompanyHandler(getCompanyService());
    }
}
