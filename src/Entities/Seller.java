package Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Seller implements Serializable {
    public int id;
    public String name;
    public String email;
    public Date birthDate;
    public Double baseSalary;

    public Department department;


    public Seller() {
    }

    public Seller(int id, String name, String email, Date birthDate, Double baseSalary,  Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.baseSalary = baseSalary;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Seller: " + id + " - " +  name + " - " + email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(id, ((Seller) obj).id);
    }


}
