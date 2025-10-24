package dk.via.fleetforward.networking;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.google.protobuf.StringValue;
import dk.via.fleetforward.gRPC.FleetServiceGrpc;
import dk.via.fleetforward.gRPC.Fleetforward.Response;
import dk.via.fleetforward.gRPC.Fleetforward.Request;
import dk.via.fleetforward.gRPC.Fleetforward.StatusType;


import dk.via.fleetforward.networking.handlers.FleetNetworkHandler;
import dk.via.fleetforward.startup.ServiceProvider;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
/**
 * @author Mario
 * @version 1.0.0
 * The main handler for the gRPC service
 * @implNote extends the gpc service to provide the implementation for the gRPC service
 */
@GRpcService
public class FleetMainHandler extends FleetServiceGrpc.FleetServiceImplBase {
    private final ServiceProvider serviceProvider;

    public FleetMainHandler(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    /**
     * Handle the request from the client
     * @param request the request from the client
     * @param responseObserver the observer to send the response to
     */
    @Override
    public void sendRequest(Request request, StreamObserver<Response> responseObserver) {
        try {
            // Route request based on HandlerType
            FleetNetworkHandler handler = switch (request.getHandler()) {
                case HANDLER_COMPANY -> serviceProvider.getCompanyHandler();
                default -> throw new IllegalArgumentException("Unknown handler type");
            };
            // Message is the protobuf object
            Message result = handler.handle(request.getAction(), request.getPayload());
            // Only pack if not already an Any
            Any payload;
            if (result instanceof Any) {
                payload = (Any) result;
            } else {
                payload = Any.pack(result);
            }

            Response response = Response.newBuilder()
                    .setStatus(StatusType.STATUS_OK)
                    .setPayload(payload)
                    .build();
            sendResponseWithHandleException(responseObserver, response);

        } catch (Exception e) {
            sendGrpcError(responseObserver, StatusType.STATUS_ERROR, e.getMessage());
        }
    }

    /**
     * Send an error response to the client
     * @param observer the observer to send the response to
     * @param status the status of the error
     * @param errorMessage the error message to send to the client
     */
    private void sendGrpcError(StreamObserver<Response> observer, StatusType status, String errorMessage) {
        Any payload =Any.pack(StringValue.of(errorMessage));// convert error message to protobuf message
        //don't ask me who thought this was not complicated... I want to cry... current hour 3:32AM
        //Mario
        Response response = Response.newBuilder().
                setStatus(status).
                setPayload(payload)
                .build();
        observer.onNext(response);
        observer.onCompleted();
    }

    /**
     * Send a response to the client with a handle exception
     * @param responseObserver the observer to send the response to
     * @param response the response to send to the client
     */
    private void sendResponseWithHandleException(StreamObserver<Response> responseObserver, Response response)
    {
        try {
            responseObserver.onNext(response);
        } catch (ClassCastException e) {
            sendGrpcError(responseObserver, StatusType.STATUS_INVALID_PAYLOAD, "Invalid request");
            return; // 🚫 don't call onCompleted again
        } catch (Exception e) {
            sendGrpcError(responseObserver, StatusType.STATUS_ERROR, e.getMessage());
            return;
        }

        try {
            responseObserver.onCompleted();
        } catch (Exception e) {
            System.err.println("Error completing gRPC response: " + e.getMessage());
        }
    }

}

