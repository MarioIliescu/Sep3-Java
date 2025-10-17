package dk.via.fleetforward.networking;

import dk.via.fleetforward.gRPC.Fleetforward.ActionType;

public interface FleetNetworkHandlers {
    Object handle(ActionType actionType, Object payload);
}
