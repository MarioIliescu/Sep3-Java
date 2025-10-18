package dk.via.fleetforward.startup;

import dk.via.fleetforward.gRPC.Fleetforward.CompanyProto;
import dk.via.fleetforward.services.company.CompanyService;
import dk.via.fleetforward.services.company.CompanyServiceDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ManualTestingMain {

    public static void main(String[] args) {
        // Start Spring Boot once and get the context
        ApplicationContext context = SpringApplication.run(ManualTestingMain.class, args);

        System.out.println("Manual testing started");

        // Get the CompanyService bean from Spring
        CompanyService companyService = context.getBean(CompanyServiceDatabase.class);

        // Create a test company
        CompanyProto company = CompanyProto.newBuilder()
                .setCompanyName("TestingSRL")
                .setMcNumber("JAVAISTES4")
                .build();

      companyService.create(company);

//        // Update a test company
//        CompanyProto company2 = CompanyProto.newBuilder()
//                .setId(3)
//                .setCompanyName("TestingSRLUpdated")
//                .setMcNumber("JAVAISTES2")
//                .build();
//
//        companyService.update(company2);
//        // Fetch it back
//        CompanyProto fetched = companyService.getSingle("JAVAISTEST");
//        System.out.println("Fetched company: " + fetched.getCompanyName());

        // Delete it if you want
       // companyService.delete("JAVAISTES2");
    }
}
