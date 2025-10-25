SEP3 - Java

- [ - Java](#infrastructure-for-sep3---java)
  - [Github Repository with all the code](#github-repository-with-all-the-code)
    - [Click for Windows GitBash install.](#click-for-windows-gitbash-install)
    - [Click for Linux GitBash install.](#click-for-linux-gitbash-install)
    - [Click for Mac Gitbash install.](#click-for-mac-gitbash-install)
  - [Infrastructure of FleetForward](#infrastructure-of-fleetforward)
    - [`Request` and `Response`](#request-and-response)
      - [GRPC important notes](#grpc-important-notes)
    - [Server](#server)
      - [gRPC Server explained](#grpc-server-explained)
    - [FleetMainHandler](#fleetmainhandler)
    - [Service Provider](#service-provider)
    - [GlobalContext](#globalcontext)
    - [Handlers](#handlers)
      - [Interface](#interface)
      - [Implementation example](#implementation-example)
    - [Services](#services)
      - [Service Interfaces](#service-interfaces)
      - [Implementation Service Example](#implementation-service-example)
        - [Creating](#creating)
        - [Update](#update)
        - [Delete](#delete)
        - [GetAll](#getall)
        - [Get Single](#get-single)
    - [Model and entities](#model-and-entities)
      - [Example Entity](#example-entity)
    - [Repository and persistance](#repository-and-persistance)
    - [Dependencies, application settings and pom](#dependencies-application-settings-and-pom)
      - [Application Settings](#application-settings)
      - [Pom and dependencies](#pom-and-dependencies)

Open GitBash and clone in the wanted folder for deeper analysis. Work in progress.

```sh
git clone https://github.com/MarioIliescu/Sep3-Java
```

### [Click for Windows GitBash install.](https://git-scm.com/downloads/win)  

### [Click for Linux GitBash install.](https://git-scm.com/downloads/linux)

### [Click for Mac Gitbash install.](https://git-scm.com/downloads/mac)

## Infrastructure of FleetForward

Requests ──► Server ──► MainHandler ──► DesignatedHandler ──► DesignatedService ──► Entity ──► Repository ──► Database

<img width="479" height="788" alt="image" src="https://github.com/user-attachments/assets/9cf88250-2e91-475f-9d53-59725f227468" />

### `Request` and `Response`

---

` Request ` is send by the client on the C# Server side.
They contain gRPC objects of type `Request` and are met with objects of type `Response`.  
Instead of `string` to clarify which `Handler` will be used or which `Action` is required or the `Status` returned by the `Server`, enums have been used, if another `ActionType`, `HandlerType` or `StatusType` they need to be added to the list. Enums are better for consistency, making sure typos do not sneak into the class and much easier to debug.

```protobuf
syntax = "proto3";

package dk.via.fleetforward.gRPC;

import "google/protobuf/any.proto";

enum HandlerTypeProto {
  HANDLER_UNKNOWN = 0;
  HANDLER_COMPANY = 1;
}

enum ActionTypeProto {
  ACTION_UNKNOWN = 0;
  ACTION_CREATE = 1;
  ACTION_GET = 2;
  ACTION_UPDATE = 3;
  ACTION_DELETE = 4;
  ACTION_LIST = 5;
}
enum StatusTypeProto {
  STATUS_UNKNOWN = 0;
  STATUS_OK = 1;
  STATUS_ERROR = 2;
  STATUS_INVALID_PAYLOAD = 3;
}
message RequestProto {
  HandlerTypeProto handler = 1;
  ActionTypeProto action = 2;
  google.protobuf.Any payload = 3;
}

message ResponseProto {
  StatusTypeProto status = 1;
  google.protobuf.Any payload = 2;
}
```

#### GRPC important notes

---

- `Message` is the gRPC version of the type Object in Java
- `Any.pack(Message message)` is a way to pack any `Object` into a gRPC `Message`
- google.protobuf.Any means that the `Object` inside the payload is any type of `Object` so there can be anything. It's used to transfer all the gRPC generated objects between the servers.

### Server

---

The server accepts Requests and sends them to the ` FleetMainHandler ` (the name does not matter it can be any name).

#### gRPC Server explained

---

Importing grpc tools `io.grpc.Server;` and `io.grpc.ServerBuilder` to make the `Server` infrastracture with listening threads and everything needs to know to accept requests.  
Open a `PORT` on 6032 but it can be any available port, in this case the connection is `TCP`.  
In the constructor give `FleetMainhandler` as a `Service` to take care of `Requests`.

```java
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.stereotype.Component;

public class FleetServer  {

    private static final int PORT = 6032;
    private final FleetMainHandler mainHandler;
    private Server grpcServer;

    public FleetServer(FleetMainHandler mainHandler) {
        this.mainHandler = mainHandler;
    }
```

Creating the method `start()`  
Put the server into a new `Thread`  
Use the `ServerBuilder` to build the `Server` on the designated `PORT` and injecting the `FleetMainHandler` as a `Service`.  
`Runtime.getRuntime().addShutdownHook` adding a hook to initiate the shutdown of the `Server` when Runtime gets terminated and displays a red message with `System.err.println()`.  
Non `Daemon` to keep the JVM alive until termination.  

```java
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
```

### FleetMainHandler  

---

Add the service to be able to extend it.  
Need to add it to the `proto` file.

```protobuf
service FleetService {
  rpc sendRequest (RequestProto) returns (ResponseProto);
}
```

Use `@GRpcService` to be able to inject it into the `Server`.  
Use `ServiceProvider` to inject the **implementation** of our **interfaces** like in **SEP2** following **Dependency Inversion**.  
`extends FleetServiceGrpc.FleetServiceImplBase` important as a `GRpcService`. (can inject it into the `ServerBuilder`)

```java
@GRpcService
public class FleetMainHandler extends FleetServiceGrpc.FleetServiceImplBase {
    private final ServiceProvider serviceProvider;

    public FleetMainHandler(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
```

Sending a `Request` and observing `Responses` using GRpc.

```java
    @Override
    public void sendRequest(RequestProto request, StreamObserver<ResponseProto> responseObserver) {
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

            ResponseProto response = ResponseProto.newBuilder()
                    .setStatus(StatusTypeProto.STATUS_OK)
                    .setPayload(payload)
                    .build();
            sendResponseWithHandleException(responseObserver, response);

        } catch (Exception e) {
            sendGrpcError(responseObserver, StatusTypeProto.STATUS_ERROR, e.getMessage());
        }
    }
```

`ErrorHandling` method to handle errors.

```java
 private void sendResponseWithHandleException(StreamObserver<ResponseProto> responseObserver, ResponseProto response)
    {
        try {
            responseObserver.onNext(response);
        } catch (ClassCastException e) {
            sendGrpcError(responseObserver, StatusTypeProto.STATUS_INVALID_PAYLOAD, "Invalid request");
            return;
        } catch (Exception e) {
            sendGrpcError(responseObserver, StatusTypeProto.STATUS_ERROR, e.getMessage());
            return;
        }
        try {
            responseObserver.onCompleted();
        } catch (Exception e) {
            System.err.println("Error completing gRPC response: " + e.getMessage());
        }
    }


```

`GRpcError` method that sends Errors if something goes wrong.

```java
private void sendGrpcError(StreamObserver<ResponseProto> observer, StatusTypeProto status, String errorMessage) {
        Any payload =Any.pack(StringValue.of(errorMessage));// convert error message to protobuf message
        ResponseProto response = ResponseProto.newBuilder().
                setStatus(status).
                setPayload(payload)
                .build();
        observer.onNext(response);
        observer.onCompleted();
    }
```

### Service Provider

```java
@Service
public class ServiceProvider {
    public CompanyServiceDatabase getCompanyService(){
        return GlobalContext.getBean(CompanyServiceDatabase.class);
    }
    public FleetNetworkHandler getCompanyHandler(){
        return new CompanyHandler(getCompanyService());
    }
}
```

### GlobalContext

```java
package dk.via.fleetforward.startup;

import org.springframework.context.ApplicationContext;

public class GlobalContext {

  private static ApplicationContext context;

  private GlobalContext() {
    // prevent instantiation
  }

  public static void setContext(ApplicationContext applicationContext) {
    context = applicationContext;
  }

  public static ApplicationContext getContext() {
    if (context == null) {
      throw new IllegalStateException("Spring ApplicationContext not initialized yet.");
    }
    return context;
  }

  public static <T> T getBean(Class<T> beanClass) {
    return getContext().getBean(beanClass);
  }
}
```

### Handlers

 ---

#### Interface

Observe the `@Service` to be possible to inject it with Spring `ApplicationContext`, dependency injection.

```java
@Service
public interface FleetNetworkHandler {
    Message handle(ActionType actionType, Object payload);
}
```

#### Implementation example

No need for the `@Service` since it inherits it from the interface
`CompanyService` interface private `Object`.

```java
public class CompanyHandler implements FleetNetworkHandler {

    private CompanyService companyService;

    public CompanyHandler(CompanyService companyService) {
        this.companyService = companyService;
    }
```

Sending the `Request` after being processed to the `Service` implementation to handle

```java
@Override
    public Message handle(ActionTypeProto actionType, Object payload) {
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
            case ACTION_LIST -> {
                proto = companyService.getAll();
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
```

`handleGet()` and `handleDelete()` due to having 2 days of accessing an Object in the database since `mcNumber` is `Unique`

```java
 private CompanyProto handleGet(CompanyProto request) {
        if (request.getId() != 0) {
            return companyService.getSingle(request.getId());
        } 
        else if (!request.getMcNumber().isEmpty()) {
            return companyService.getSingle(request.getMcNumber());
        }
        else{
            throw new IllegalArgumentException("Must provide either an ID or MC number for ACTION_GET.");
        }
}

private void handleDelete(CompanyProto request) {
        if (request.getId() != 0) {
            companyService.delete(request.getId());
        } 
        else if (!request.getMcNumber().isEmpty()) {
            companyService.delete(request.getMcNumber());
        }
        else{
            throw new IllegalArgumentException("Must provide either an ID or MC number for ACTION_DELETE.");
        }
}
```

### Services

---

#### Service Interfaces

---

Use the protobuf Objects.
`@Service` is important to be able to inject it.  
Make an `interface` for every type of `Object` inside the `Database` with the proper methods needed, use CRUD.

```java

@Service
public interface CompanyService {
    //CRUD operations

    CompanyProto create(CompanyProto payload);

    CompanyProto update(CompanyProto payload);

    CompanyProto getSingle(String mcNumber);

    CompanyProto getSingle(int id);

    void delete(String mcNumber);

    void delete(int id);

    CompanyProtoList getAll();
}
```

#### Implementation Service Example

---

Mark the `Service` as the database implementation, the previous interface can be used for file storage in memory etc... but a database is used in this case: `PostGreSQL`.  
All methods that make changes to the database must be `@Transactional`.

```java
@Service("database")
public class CompanyServiceDatabase implements CompanyService{

    private final CompanyRepository companyRepository;

    /**
     * Constructor
     * @param companyRepository The company repository for database operations using Spring Data JPA
     */
    public CompanyServiceDatabase(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
```

##### Creating

```java
@Transactional
    public CompanyProto create(CompanyProto payload) {
        //Make an Entity from our model
        Company company = new Company(); 
        //Set all the paramethers required, the setters need to have the logic
        company.setMcNumber(payload.getMcNumber());
        company.setCompanyName(payload.getCompanyName());
        //After the entity is created succesfully, save it and get the new created entity with the Id
        Company created = companyRepository.save(company);
        //return the proto 
        return CompanyProto.newBuilder()
                .setId(created.getId())
                .setMcNumber(created.getMcNumber())
                .setCompanyName(created.getCompanyName()).build();
}
```

##### Update

```java
@Override
    @Transactional
    public CompanyProto update(CompanyProto payload) {
        //First verify the object exists
        Company existing = companyRepository.findById(payload.getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        //Set the new values, verified in the entity setters
        existing.setMcNumber(payload.getMcNumber());
        existing.setCompanyName(payload.getCompanyName());
        //save the updated object
        Company updated = companyRepository.save(existing);
        //return the new object
        return CompanyProto.newBuilder()
                .setId(updated.getId())
                .setMcNumber(updated.getMcNumber())
                .setCompanyName(updated.getCompanyName())
                .build();
}
```

##### Delete

```java
public void delete(int id) {
    //Use the magic spell and works. JPA
       companyRepository.deleteById(id);
    }
```

##### GetAll

```java
@Override
    public Fleetforward.CompanyProtoList getAll() {
      List<Company> companies = companyRepository.findAll();

      // Builder for the list
      Fleetforward.CompanyProtoList.Builder companiesProtoBuilder = Fleetforward.CompanyProtoList.newBuilder();

      // Convert each Company entity to CompanyProto
      for (Company company : companies) {
        Fleetforward.CompanyProto companyProto = Fleetforward.CompanyProto.newBuilder()
            .setId(company.getId())
            .setMcNumber(company.getMcNumber())
            .setCompanyName(company.getCompanyName())
            .build();
        companiesProtoBuilder.addCompanies(companyProto);
      }
      // Build and return the list
      return companiesProtoBuilder.build();
```

##### Get Single

```java
@Override
    public CompanyProto getSingle(int id) {
        //Wrap in optional in case it doesn't exist
        Optional<Company> fetched = companyRepository.findById(id); //null safety
        Company company = fetched.orElseThrow(() -> new RuntimeException("Company not found"));
        //return the proto
        return CompanyProto.newBuilder()
                .setId(company.getId())
                .setMcNumber(company.getMcNumber())
                .setCompanyName(company.getCompanyName())
                .build();
    }
```

### Model and entities

---

#### Example Entity

---

**IMPORTANT**  
 Mark the `Object` with `@Entity` so `JPA` knows to add to the `database`.

---

```java
//Mark as Entity
@Entity
//Which table is part of
@Table(name = "company", schema = "fleetforward")
public class Company {
    //serial key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //wrapper in Integer
    Integer id;
    //give the column in the database, if it s unique and nullable
    @Column(name = "mc_number", unique = true, nullable = false)
    String mcNumber;
    @Column(name = "company_name", nullable = false)
    String companyName;
    //...getters and setters

     public void setMcNumber(String mcNumber) {
        //Use the model to put in existance requirements, for example mc is 10 characters long.
        if (StringUtility.isNullOrEmpty(mcNumber)) {
            throw new IllegalArgumentException("MC number cannot be null or empty");
        }
        else if (mcNumber.length() != 10) {
            throw new IllegalArgumentException("MC number must be 10 characters long");
        }
        this.mcNumber = mcNumber;
    }
```

### Repository and persistance

---

Mark as `@Repository`

```java
@Repository
//Type of object and primary key type
//Company = Object
//Integer = pk
public interface CompanyRepository extends JpaRepository<Company, Integer>{
    //Optional if we want more than just basic crud
    Optional<Company> findByMcNumber(String mcNumber);
    void deleteByMcNumber(String mcNumber);
}
```

### Dependencies, application settings and pom

---

#### Application Settings

---

<img width="351" height="54" alt="image" src="https://github.com/user-attachments/assets/9c41ec6f-2fa7-433c-a7d9-ef8c79307999" />

```java
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres //where the database is located
spring.datasource.username=postgres //user
spring.datasource.password= //the password of the user
spring.jpa.properties.hibernate.default_schema=//the schema
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true//shows sql in console
spring.jpa.properties.hibernate.format_sql=true
```

#### Pom and dependencies

---

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.2</version>
        <relativePath/>
    </parent>

    <groupId>via.sdj3</groupId>
    <artifactId>grpc-springboot-x</artifactId>
    <version>1.0</version>
    <packaging>jar</packaging>

    <name>grpc-springboot-x</name>
    <description>SEP3-Java</description>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>21</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>javax.annotation</groupId>
            <artifactId>javax.annotation-api</artifactId>
            <version>1.3.2</version>
        </dependency>

        <!-- PostGreSQL -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.6.0</version>
        </dependency>
        <!-- gRPC Spring Boot Starter -->
        <dependency>
            <groupId>io.github.lognet</groupId>
            <artifactId>grpc-spring-boot-starter</artifactId>
            <version>4.8.0</version>
        </dependency>

        <dependency>
            <groupId>jakarta.annotation</groupId>
            <artifactId>jakarta.annotation-api</artifactId>
            <version>2.1.1</version>
        </dependency>

        <!-- Protobuf runtime -->
        <dependency>
            <groupId>com.google.protobuf</groupId>
            <artifactId>protobuf-java</artifactId>
            <version>3.21.6</version>
        </dependency>

        <!-- Spring Boot test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <version>3.3.2</version>
        </dependency>

        <!-- Mockito and JUnit test -->

        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>




    <build>
        <plugins>

            <!-- Spring Boot -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>

            <!--Protobuf Compiler Plugin -->
            <plugin>
                <groupId>com.github.os72</groupId>
                <artifactId>protoc-jar-maven-plugin</artifactId>
                <version>3.11.4</version>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>run</goal>
                        </goals>
                        <configuration>
                            <!--FIX: Include built-in google protos -->
                            <includeStdTypes>true</includeStdTypes>

                            <!-- Your protobuf compiler version -->
                            <protocVersion>3.21.6</protocVersion>

                            <!-- Where your .proto files are -->
                            <inputDirectories>
                                <include>src/main/proto</include>
                            </inputDirectories>

                            <!-- Keep your existing output structure -->
                            <outputTargets>
                                <outputTarget>
                                    <type>java</type>
                                    <outputDirectory>src/main/java</outputDirectory>
                                </outputTarget>
                                <outputTarget>
                                    <type>grpc-java</type>
                                    <pluginArtifact>io.grpc:protoc-gen-grpc-java:1.66.0</pluginArtifact>
                                    <outputDirectory>src/main/java</outputDirectory>
                                </outputTarget>
                            </outputTargets>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```
