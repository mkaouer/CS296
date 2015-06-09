package project_part2;

public class Faculty extends Employee 
{
    String office_hours;
    int rank;
    
    public Faculty()
    {
    	super();
    	office_hours = new String();
    }

    public Faculty(String name, String address, String phone, String email, String officeHours2, int rank) 
    {
    super(name, address, phone, email);
    //this.name = name;
    office_hours = new String(officeHours2);
    this.rank = rank ;
    }

    public String toString()
    {
        return "Faculty: "+this.getName()+" has rank :" + this.rank;
    }
}