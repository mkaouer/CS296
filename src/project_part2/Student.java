package project_part2;

public class Student extends Person 
{

    public String class_status;

    public Student()
    {
    	super();
    	class_status = new String();
    }
    
    public Student(String name, String address, String phone, String email, String classStatus) 
    {
    	super(name, address, phone, email);
    	this.class_status = classStatus;
    }
    public String toString()
    {
        return "Student: "+this.getName()+" has Status: " + this.class_status;
    }

}
