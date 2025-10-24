package dk.via.fleetforward.gRPC;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.66.0)",
    comments = "Source: fleetforward.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FleetServiceProtoGrpc {

  private FleetServiceProtoGrpc() {}

  public static final java.lang.String SERVICE_NAME = "dk.via.fleetforward.gRPC.FleetServiceProto";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<dk.via.fleetforward.gRPC.Fleetforward.RequestProto,
      dk.via.fleetforward.gRPC.Fleetforward.ResponseProto> getSendRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendRequest",
      requestType = dk.via.fleetforward.gRPC.Fleetforward.RequestProto.class,
      responseType = dk.via.fleetforward.gRPC.Fleetforward.ResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<dk.via.fleetforward.gRPC.Fleetforward.RequestProto,
      dk.via.fleetforward.gRPC.Fleetforward.ResponseProto> getSendRequestMethod() {
    io.grpc.MethodDescriptor<dk.via.fleetforward.gRPC.Fleetforward.RequestProto, dk.via.fleetforward.gRPC.Fleetforward.ResponseProto> getSendRequestMethod;
    if ((getSendRequestMethod = FleetServiceProtoGrpc.getSendRequestMethod) == null) {
      synchronized (FleetServiceProtoGrpc.class) {
        if ((getSendRequestMethod = FleetServiceProtoGrpc.getSendRequestMethod) == null) {
          FleetServiceProtoGrpc.getSendRequestMethod = getSendRequestMethod =
              io.grpc.MethodDescriptor.<dk.via.fleetforward.gRPC.Fleetforward.RequestProto, dk.via.fleetforward.gRPC.Fleetforward.ResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dk.via.fleetforward.gRPC.Fleetforward.RequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  dk.via.fleetforward.gRPC.Fleetforward.ResponseProto.getDefaultInstance()))
              .setSchemaDescriptor(new FleetServiceProtoMethodDescriptorSupplier("SendRequest"))
              .build();
        }
      }
    }
    return getSendRequestMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FleetServiceProtoStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FleetServiceProtoStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FleetServiceProtoStub>() {
        @java.lang.Override
        public FleetServiceProtoStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FleetServiceProtoStub(channel, callOptions);
        }
      };
    return FleetServiceProtoStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FleetServiceProtoBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FleetServiceProtoBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FleetServiceProtoBlockingStub>() {
        @java.lang.Override
        public FleetServiceProtoBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FleetServiceProtoBlockingStub(channel, callOptions);
        }
      };
    return FleetServiceProtoBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FleetServiceProtoFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FleetServiceProtoFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FleetServiceProtoFutureStub>() {
        @java.lang.Override
        public FleetServiceProtoFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FleetServiceProtoFutureStub(channel, callOptions);
        }
      };
    return FleetServiceProtoFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void sendRequest(dk.via.fleetforward.gRPC.Fleetforward.RequestProto request,
        io.grpc.stub.StreamObserver<dk.via.fleetforward.gRPC.Fleetforward.ResponseProto> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendRequestMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service FleetServiceProto.
   */
  public static abstract class FleetServiceProtoImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return FleetServiceProtoGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service FleetServiceProto.
   */
  public static final class FleetServiceProtoStub
      extends io.grpc.stub.AbstractAsyncStub<FleetServiceProtoStub> {
    private FleetServiceProtoStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FleetServiceProtoStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FleetServiceProtoStub(channel, callOptions);
    }

    /**
     */
    public void sendRequest(dk.via.fleetforward.gRPC.Fleetforward.RequestProto request,
        io.grpc.stub.StreamObserver<dk.via.fleetforward.gRPC.Fleetforward.ResponseProto> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendRequestMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service FleetServiceProto.
   */
  public static final class FleetServiceProtoBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<FleetServiceProtoBlockingStub> {
    private FleetServiceProtoBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FleetServiceProtoBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FleetServiceProtoBlockingStub(channel, callOptions);
    }

    /**
     */
    public dk.via.fleetforward.gRPC.Fleetforward.ResponseProto sendRequest(dk.via.fleetforward.gRPC.Fleetforward.RequestProto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendRequestMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service FleetServiceProto.
   */
  public static final class FleetServiceProtoFutureStub
      extends io.grpc.stub.AbstractFutureStub<FleetServiceProtoFutureStub> {
    private FleetServiceProtoFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FleetServiceProtoFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FleetServiceProtoFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<dk.via.fleetforward.gRPC.Fleetforward.ResponseProto> sendRequest(
        dk.via.fleetforward.gRPC.Fleetforward.RequestProto request) {
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
          serviceImpl.sendRequest((dk.via.fleetforward.gRPC.Fleetforward.RequestProto) request,
              (io.grpc.stub.StreamObserver<dk.via.fleetforward.gRPC.Fleetforward.ResponseProto>) responseObserver);
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
              dk.via.fleetforward.gRPC.Fleetforward.RequestProto,
              dk.via.fleetforward.gRPC.Fleetforward.ResponseProto>(
                service, METHODID_SEND_REQUEST)))
        .build();
  }

  private static abstract class FleetServiceProtoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FleetServiceProtoBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return dk.via.fleetforward.gRPC.Fleetforward.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FleetServiceProto");
    }
  }

  private static final class FleetServiceProtoFileDescriptorSupplier
      extends FleetServiceProtoBaseDescriptorSupplier {
    FleetServiceProtoFileDescriptorSupplier() {}
  }

  private static final class FleetServiceProtoMethodDescriptorSupplier
      extends FleetServiceProtoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    FleetServiceProtoMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (FleetServiceProtoGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FleetServiceProtoFileDescriptorSupplier())
              .addMethod(getSendRequestMethod())
              .build();
        }
      }
    }
    return result;
  }
}
