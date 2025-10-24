package dk.via.fleetforward.networking.handlers;

import com.google.protobuf.Message;
import dk.via.fleetforward.gRPC.Fleetforward.ActionType;
import org.springframework.stereotype.Service;

/**
 * FleetNetworkHandler interface for handling actions from the fleet network
 */
@Service
public interface FleetNetworkHandler {
    Message handle(ActionType actionType, Object payload);
}
