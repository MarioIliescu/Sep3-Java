package dk.via.fleetforward.networking.handlers;

import com.google.protobuf.Message;
import dk.via.fleetforward.gRPC.Fleetforward;
import dk.via.fleetforward.services.company.CompanyService;

public class CompanyHandler implements FleetNetworkHandler {
    private CompanyService companyService;
    public CompanyHandler(CompanyService companyService) {
        this.companyService = companyService;
    }
    @Override
    public Message handle(Fleetforward.ActionType actionType, Object payload) {
        return null;
    }
}
