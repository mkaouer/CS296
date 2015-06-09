package project_part2;

import java.util.Date;

public abstract class Employee extends Person
{

    String office;
    int salary;
    String hire;

    public Employee() 
    {
    }

    public Employee(String name, String address, String phone, String email)
    {
        super(name, address, phone, email);
    }

    public Employee(String office, int salary, String hire)
    {
        this.office = office;
        this.salary = salary;
        this.hire = hire;
    }

    public void setOffice(String office)
    {
        this.office = office;
    }

    public String getOffice()
    {
        return this.office;
    }

    public void setSalary(int salary)
    {
        this.salary = salary;
    }

    public double getSalary()
    {
        return this.salary;
    }

    public void setHire(String hire)
    {
        this.hire = hire;
    }

    public String getHire()
    {
        return this.hire;
    }

    public abstract String toString();
    
}
