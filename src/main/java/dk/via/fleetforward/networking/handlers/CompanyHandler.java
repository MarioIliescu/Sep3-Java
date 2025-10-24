package dk.via.fleetforward.networking.handlers;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import dk.via.fleetforward.gRPC.Fleetforward;
import dk.via.fleetforward.gRPC.Fleetforward.CompanyProto;
import dk.via.fleetforward.gRPC.Fleetforward.ActionType;
import dk.via.fleetforward.services.company.CompanyService;

/**
 * The CompanyHandler class implements the FleetNetworkHandler interface and is responsible
 * for handling actions related to company management. It acts as an adapter between the
 * fleet network commands and the underlying CompanyService to perform operations on companies.
 */
public class CompanyHandler implements FleetNetworkHandler {

    private CompanyService companyService;

    public CompanyHandler(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Handles various actions related to a company based on the provided action type and payload.
     * This method delegates the operation to the appropriate handler or service method based on
     * the specified {@code ActionType}.
     *
     * @param actionType The type of action to perform. This can be one of the following:
     *                   {@code ACTION_CREATE}, {@code ACTION_GET}, {@code ACTION_UPDATE},
     *                   {@code ACTION_DELETE}, or {@code ACTION_LIST}. Other types will result
     *                   in an {@code IllegalArgumentException}.
     * @param payload    The input data required to perform the specified action. This should be
     *                   an instance of {@code CompanyProto}.
     * @return A {@code Message} object containing the result of the action. If the action is
     *         {@code ACTION_DELETE}, this method may return {@code null}.
     * @throws IllegalArgumentException if the provided action type is invalid or unsupported.
     */
    @Override
    public Message handle(ActionType actionType, Object payload) {
        Message proto = null;
        Any payloadAny = (Any) payload;
      CompanyProto request = null;
      try
      {
        request = payloadAny.unpack(CompanyProto.class);
      }
      catch (InvalidProtocolBufferException e)
      {
        throw new RuntimeException(e);
      }
      switch (actionType) {
            case ACTION_GET -> {
                proto = handleGet(request);
            }
            case ACTION_CREATE -> {
                proto = companyService.create(request);
            }
            case ACTION_UPDATE -> {
                proto = companyService.update(request);
            }
            case ACTION_DELETE -> {
                handleDelete(request);
            }
            case ActionType.ACTION_LIST -> {
                proto = Any.pack((Message) companyService.getAll());
            }
            default -> {
                throw new IllegalArgumentException("Invalid action type: " + actionType);
            }
        }
        //sometimes it will return null, no need to check for that
        //in case of delete
      if (proto == null) {
        proto = CompanyProto.newBuilder().build();
      }
      return Any.pack(proto) ;
    }

    /**
     * Handles the retrieval of a company record based on the provided request.
     * The method checks if the request contains either a valid company ID or an MC number.
     * If a valid ID is provided, the corresponding company record is fetched using the {@code companyService}.
     * If an MC number is provided instead, the method retrieves the matching company record.
     * If both are missing, an {@code IllegalArgumentException} is thrown.
     *
     * @param request The request object containing the details of the company to be retrieved.
     *                It should include either a valid company ID or an MC number.
     * @return The {@code CompanyProto} object containing the details of the retrieved company.
     * @throws IllegalArgumentException if neither the company ID nor the MC number is provided in the request.
     */
    private CompanyProto handleGet(CompanyProto request) {
        if (request.getId() != 0) {
            return companyService.getSingle(request.getId());
        } else if (!request.getMcNumber().isEmpty()) {
            return companyService.getSingle(request.getMcNumber());
        }
        else {
            throw new IllegalArgumentException("Must provide either an ID or MC number for ACTION_GET.");
        }
    }

    /**
     * Handles the deletion of a company record based on the provided request.
     * The method checks if the request contains either a valid company ID or an MC number
     * and deletes the corresponding company record using the {@code companyService}.
     *
     * @param request the request object containing the details of the company to be deleted.
     *                It should include either a valid company ID or an MC number.
     *                If both are missing, an {@code IllegalArgumentException} is thrown.
     */
    private void handleDelete(CompanyProto request) {
        if (request.getId() != 0) {
            companyService.delete(request.getId());
        } else if (!request.getMcNumber().isEmpty()) {
            companyService.delete(request.getMcNumber());
        }
        else {
            throw new IllegalArgumentException("Must provide either an ID or MC number for ACTION_DELETE.");
        }
    }
}

