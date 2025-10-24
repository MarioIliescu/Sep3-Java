package dk.via.fleetforward.gRPC;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.66.0)",
    comments = "Source: fleetforward.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FleetServiceGrpc {

  private FleetServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "dk.via.fleetforward.gRPC.FleetService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<dk.via.fleetforward.gRPC.Fleetforward.Request,
      dk.via.fleetforward.gRPC.Fleetforward.Response> getSendRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendRequest",
      requestType = dk.via.fleetforward.gRPC.Fleetforward.Request.class,
      responseType = dk.via.fleetforward.gRPC.Fleetforward.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<dk.via.fleetforward.gRPC.Fleetforward.Request,
      dk.via.fleetforward.gRPC.Fleetforward.Response> getSendRequestMethod() {
    io.grpc.MethodDescriptor<dk.via.fleetforward.gRPC.Fleetforward.Request, dk.via.fleetforward.gRPC.Fleetforward.Response> getSendRequestMethod;
    if ((getSendRequestMethod = FleetServiceGrpc.getSendRequestMethod) == null) {
      synchronized (FleetServiceGrpc.class) {
        if ((getSendRequestMethod = FleetServiceGrpc.getSendRequestMethod) == null) {
          FleetServiceGrpc.getSendRequestMethod = getSendRequestMethod =
              io.grpc.MethodDescriptor.<dk.via.fleetforward.gRPC.Fleetforward.Request, dk.via.fleetforward.gRPC.Fleetforward.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dk.via.fleetforward.gRPC.Fleetforward.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dk.via.fleetforward.gRPC.Fleetforward.Response.getDefaultInstance()))
              .setSchemaDescriptor(new FleetServiceMethodDescriptorSupplier("SendRequest"))
              .build();
        }
      }
    }
    return getSendRequestMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FleetServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FleetServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FleetServiceStub>() {
        @java.lang.Override
        public FleetServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FleetServiceStub(channel, callOptions);
        }
      };
    return FleetServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FleetServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FleetServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FleetServiceBlockingStub>() {
        @java.lang.Override
        public FleetServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FleetServiceBlockingStub(channel, callOptions);
        }
      };
    return FleetServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FleetServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FleetServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FleetServiceFutureStub>() {
        @java.lang.Override
        public FleetServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FleetServiceFutureStub(channel, callOptions);
        }
      };
    return FleetServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void sendRequest(dk.via.fleetforward.gRPC.Fleetforward.Request request,
        io.grpc.stub.StreamObserver<dk.via.fleetforward.gRPC.Fleetforward.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendRequestMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service FleetService.
   */
  public static abstract class FleetServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return FleetServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service FleetService.
   */
  public static final class FleetServiceStub
      extends io.grpc.stub.AbstractAsyncStub<FleetServiceStub> {
    private FleetServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FleetServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FleetServiceStub(channel, callOptions);
    }

    /**
     */
    public void sendRequest(dk.via.fleetforward.gRPC.Fleetforward.Request request,
        io.grpc.stub.StreamObserver<dk.via.fleetforward.gRPC.Fleetforward.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendRequestMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service FleetService.
   */
  public static final class FleetServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<FleetServiceBlockingStub> {
    private FleetServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FleetServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FleetServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public dk.via.fleetforward.gRPC.Fleetforward.Response sendRequest(dk.via.fleetforward.gRPC.Fleetforward.Request request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendRequestMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service FleetService.
   */
  public static final class FleetServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<FleetServiceFutureStub> {
    private FleetServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FleetServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FleetServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<dk.via.fleetforward.gRPC.Fleetforward.Response> sendRequest(
        dk.via.fleetforward.gRPC.Fleetforward.Request request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendRequestMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_REQUEST = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_REQUEST:
          serviceImpl.sendRequest((dk.via.fleetforward.gRPC.Fleetforward.Request) request,
              (io.grpc.stub.StreamObserver<dk.via.fleetforward.gRPC.Fleetforward.Response>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSendRequestMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              dk.via.fleetforward.gRPC.Fleetforward.Request,
              dk.via.fleetforward.gRPC.Fleetforward.Response>(
                service, METHODID_SEND_REQUEST)))
        .build();
  }

  private static abstract class FleetServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FleetServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return dk.via.fleetforward.gRPC.Fleetforward.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FleetService");
    }
  }

  private static final class FleetServiceFileDescriptorSupplier
      extends FleetServiceBaseDescriptorSupplier {
    FleetServiceFileDescriptorSupplier() {}
  }

  private static final class FleetServiceMethodDescriptorSupplier
      extends FleetServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    FleetServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (FleetServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FleetServiceFileDescriptorSupplier())
              .addMethod(getSendRequestMethod())
              .build();
        }
      }
    }
    return result;
  }
}
