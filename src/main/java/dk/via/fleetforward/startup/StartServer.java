package dk.via.fleetforward.startup;

import dk.via.fleetforward.networking.FleetServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;
@EnableJpaRepositories("dk.via.fleetforward.repositories.database")
@EntityScan("dk.via.fleetforward.model")
@SpringBootApplication(scanBasePackages = "dk.via.fleetforward")

public class StartServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        GlobalContext.setContext(SpringApplication.run(StartServer.class, args));

        // Manually get FleetServer bean and start gRPC
        FleetServer fleetServer = GlobalContext.getContext().getBean(FleetServer.class);
        fleetServer.start();
        System.out.println("Fleetforward gRPC Server started");
        System.out.println("You did it choom");
    }

}
