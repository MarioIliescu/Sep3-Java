package dk.via.fleetforward.model;

import dk.via.fleetforward.utility.StringUtility;
import jakarta.persistence.*;

@Entity
@Table(name = "company", schema = "fleetforward")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "mc_number", unique = true, nullable = false)
    String mcNumber;
    @Column(name = "company_name", nullable = false)
    String companyName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMcNumber() {
        return mcNumber;
    }

    public void setMcNumber(String mcNumber) {
        if (StringUtility.isNullOrEmpty(mcNumber)) {
            throw new IllegalArgumentException("MC number cannot be null or empty");
        }
        else if (mcNumber.length() != 10) {
            throw new IllegalArgumentException("MC number must be 10 characters long");
        }
        this.mcNumber = mcNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        if (StringUtility.isNullOrEmpty(companyName)) {
            throw new IllegalArgumentException("Company name cannot be null or empty");
        }
        else if (companyName.length() > 50) {
            throw new IllegalArgumentException("Company name must be 50 characters or less");
        }
        this.companyName = companyName;
    }
}
