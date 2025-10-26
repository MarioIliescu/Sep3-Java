# Entities and Repositories

- [Entities and Repositories](#entities-and-repositories)
  - [Model and entities](#model-and-entities)
    - [Example Entity](#example-entity)
  - [Repository and persistance](#repository-and-persistance)
  - [Dependencies, application settings and pom](#dependencies-application-settings-and-pom)
    - [Application Settings](#application-settings)

For a better overview of the project go back to [README](https://github.com/MarioIliescu/SEP3-Java/blob/master/README.md)

## Model and entities

### Example Entity

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

## Repository and persistance

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

## Dependencies, application settings and pom

### Application Settings

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
