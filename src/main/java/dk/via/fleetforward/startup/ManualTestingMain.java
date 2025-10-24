/*

package dk.via.fleetforward.startup;

import dk.via.fleetforward.gRPC.Fleetforward.CompanyProto;
import dk.via.fleetforward.repositories.database.CompanyRepository;
import dk.via.fleetforward.services.company.CompanyService;
import dk.via.fleetforward.services.company.CompanyServiceDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories("dk.via.fleetforward.repositories.database")
@EntityScan("dk.via.fleetforward.model")
public class ManualTestingMain
{

  public static void main(String[] args)
  {
    // Start Spring Boot once and get the context
    ApplicationContext context = SpringApplication.run(ManualTestingMain.class,
        args);

    System.out.println("Manual testing started");

    // Get the CompanyService bean from Spring
    CompanyService companyService = new CompanyServiceDatabase(context.getBean(CompanyRepository.class));
    
    Iterable<CompanyProto> source=companyService.getAll();
    List<CompanyProto> companies=new ArrayList<>();
    source.forEach(companies::add);
    int id = companies.get(companies.size()-1).getId();
    for (int i = id + 1; i <id +10; i++)
    {
      String mcNumber;
      if(i<10){
        mcNumber = "JAVAISTES" + i;
      }
      else if(i<100){
        mcNumber = "JAVAISTE" + i;
      }
      else if (i<1000)
      {
        mcNumber = "JAVAIST" + i;
      }
      else
      {
        mcNumber = "JAVAISDEFT";
      }
      CompanyProto company = CompanyProto.newBuilder().setCompanyName("TestingSRL").setMcNumber(mcNumber).build();
      companyService.create(company);
    }



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
*/
