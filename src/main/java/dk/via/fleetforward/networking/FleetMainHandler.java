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

@GRpcService
/**
 * @author Mario
 * @implNote extends the gpc service to provide the implementation for the gRPC service
 */
public class FleetMainHandler extends FleetServiceGrpc.FleetServiceImplBase {
    private final ServiceProvider serviceProvider;

    public FleetMainHandler(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void sendRequest(Request request, StreamObserver<Response> responseObserver) {
        Response.Builder responseBuilder = Response.newBuilder();
            // Route request based on HandlerType
            FleetNetworkHandler handler = switch (request.getHandler()) {
                case HANDLER_COMPANY -> serviceProvider.getCompanyHandler();
                default -> throw new IllegalArgumentException("Unknown handler type");
            };
            //Message is the protobuf object, this was written in blood.
            Message result = handler.handle(request.getAction(), request.getPayload());
            Response response = Response.newBuilder()
                    .setStatus(StatusType.STATUS_OK)
                    .setPayload(Any.pack((Message) result))//convert result to protobuf message
                    .build();
            sendResponseWithHandleException(responseObserver,response);
    }

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
    private void sendResponseWithHandleException(StreamObserver<Response> responseObserver,Response response)
    {
            try {
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } catch (ClassCastException e) {
                sendGrpcError(responseObserver, StatusType.STATUS_INVALID_PAYLOAD, "Invalid request");

            } catch (Exception e) {
                sendGrpcError(responseObserver,StatusType.STATUS_ERROR, e.getMessage());
            }
    }
}

