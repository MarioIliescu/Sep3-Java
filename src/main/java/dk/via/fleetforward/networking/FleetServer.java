package dk.via.fleetforward.networking;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * FleetServer class for starting the gRPC server
 * @implNote This class is a Spring component and is instantiated by Spring
 * @see FleetMainHandler
 * @see ServerBuilder
 * @see Server
 */
@Component
public class FleetServer  {

    private static final int PORT = 6032;
    private final FleetMainHandler mainHandler;
    private Server grpcServer;

    public FleetServer(FleetMainHandler mainHandler) {
        this.mainHandler = mainHandler;
    }

    /**
     * Starts the Fleet gRPC server.
     * This method initializes and starts a gRPC server on the specified port, attaching
     * the main gRPC service handler for handling incoming requests. It spawns the server
     * execution in a separate thread to ensure non-blocking behavior and sets up a shutdown
     * hook to gracefully stop the server when the application is terminated.
     *
     * The server runs as a non-daemon thread, ensuring the JVM stays alive as long as the
     * server is running. Any errors during server initialization or termination are logged.
     *
     * @implNote The gRPC service lifecycle is managed by this method, including server initialization,
     *           starting, and awaiting termination.
     * @throws RuntimeException If the server fails to start or is interrupted during operation.
     */
    public void start() {
        Thread grpcThread = new Thread(() -> {
            try {
                grpcServer = ServerBuilder.forPort(PORT)
                        .addService(mainHandler)
                        .build()
                        .start();

                System.out.println("Fleet gRPC Server started on port " + PORT);

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    System.err.println("Shutting down gRPC server...");
                    if (grpcServer != null) grpcServer.shutdown();
                }));

                grpcServer.awaitTermination(); // keep server alive
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }, "gRPC-Server-Thread");

        grpcThread.setDaemon(false); // non-daemon keeps JVM alive
        grpcThread.start();
    }
}
