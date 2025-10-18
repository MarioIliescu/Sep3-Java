package dk.via.fleetforward.startup;

import dk.via.fleetforward.networking.handlers.CompanyHandler;
import dk.via.fleetforward.networking.handlers.FleetNetworkHandler;
import dk.via.fleetforward.repositories.database.CompanyRepository;
import dk.via.fleetforward.services.company.CompanyServiceDatabase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ServiceProvider {
    private ApplicationContext context;
    public CompanyServiceDatabase getCompanyService(){
        return new CompanyServiceDatabase(context.getBean(CompanyRepository.class));
    }
    public FleetNetworkHandler getCompanyHandler(){
        return new CompanyHandler(getCompanyService());
    }
}
