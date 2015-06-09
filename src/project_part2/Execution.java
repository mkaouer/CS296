package project_part2;

public class Execution 
{

    public static void main(String[] args) 
    {
        ConnectionFactory connection = new ConnectionFactory(true);
        
        try 
        {
			// An example of how to load the database into a set of ArrayLists, modifying some values, and saving the arrays back to the database
        	connection.load();
			connection.print_size();
			connection.student.get(0).setName("John Williams");
			connection.staff.get(0).setName("Krish Star");
			connection.registration.get(0).grade = new String("B");
			connection.faculty.get(0).setName("Max Ruin");
			connection.course.get(0).credit = 3;
			connection.save();
		} 
        catch (Exception e) 
		{
			e.printStackTrace();
		}
    	
    }
}