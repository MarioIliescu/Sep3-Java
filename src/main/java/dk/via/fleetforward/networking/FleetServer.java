package dk.via.fleetforward.networking;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FleetServer  {

    private static final int PORT = 6032;
    private final FleetMainHandler mainHandler;
    private Server grpcServer;

    public FleetServer(FleetMainHandler mainHandler) {
        this.mainHandler = mainHandler;
    }
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
