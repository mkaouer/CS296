package project_part2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;


public class ConnectionFactory 
{
	// Connection Parameters
	static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:mysql://localhost/project_db";
    public static final String USER = "root";
    public static final String PASSWORD = "";
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    
    // ArrayLists of table values 
    
    ArrayList<Student> student ;
    ArrayList<Staff> staff ;
    ArrayList<Faculty> faculty ;
    ArrayList<Course> course ;
    ArrayList<Registration> registration ;
    
    //private constructor
    private ConnectionFactory() 
    {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    // public constructor
    public ConnectionFactory(Boolean active)
    {
    	student = new ArrayList<Student>();
    	staff = new ArrayList<Staff>();
    	faculty = new ArrayList<Faculty>();
    	course = new ArrayList<Course>();
    	registration = new ArrayList<Registration>();
    }
     
    private Connection createConnection() 
    {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
        }
        return connection;
    }  
     
    public static Connection getConnection() 
    {
        return instance.createConnection();
    }
    
    
	public void load() throws Exception
    {
        String course_query = "SELECT * FROM course";
        String faculty_query = "SELECT * FROM faculty";
        String registration_query = "SELECT * FROM registration";
        String staff_query = "SELECT * FROM staff";
        String student_query = "SELECT * FROM student";
        
        student = new ArrayList<Student>();
    	staff = new ArrayList<Staff>();
    	faculty = new ArrayList<Faculty>();
    	course = new ArrayList<Course>();
    	registration = new ArrayList<Registration>();
    	
    	this.instance = new ConnectionFactory();
        
        try 
        {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            
            // get data from course
            resultSet =statement.executeQuery(course_query);
            while(resultSet .next())
            {
            	Course temp = new Course();
            	
            	if (resultSet.getObject("ID") != null) temp.ID = new Integer(resultSet.getInt("ID"));
            	else temp.ID = -1;
            	if (resultSet.getString("title") != null) temp.title = new String(resultSet.getString("title"));
            	else temp.title = new String();
            	if (resultSet.getString("days") != null) temp.days = new String(resultSet.getString("days"));
            	else temp.days = new String();
            	if (resultSet.getObject("max_capacity") != null) temp.max_capacity = new Integer(resultSet.getInt("max_capacity"));
            	else temp.max_capacity = -1;
            	if (resultSet.getObject("credit") != null) temp.credit = new Integer(resultSet.getInt("credit"));
            	else temp.credit = -1;
            	course.add(temp);
            }
            
         // get data from faculty
            resultSet =statement.executeQuery(faculty_query);
            while(resultSet .next())
            {
            	Faculty temp = new Faculty();
            	
            	if (resultSet.getObject("ID") != null) temp.ID = new Integer(resultSet.getInt("ID"));
            	else temp.ID = -1;
            	if (resultSet.getString("name") != null) temp.setName(new String(resultSet.getString("name")));
            	else temp.setName(new String());
            	if (resultSet.getString("address") != null) temp.setAddress(new String(resultSet.getString("address")));
            	else temp.setAddress(new String());
            	if (resultSet.getString("phone_number") != null) temp.setPhone(new String(resultSet.getString("phone_number")));
            	else temp.setPhone(new String());
            	if (resultSet.getString("email_address") != null) temp.setEmail(new String(resultSet.getString("email_address")));
            	else temp.setEmail(new String());
            	if (resultSet.getString("office") != null) temp.office = new String(resultSet.getString("office"));
            	else temp.office = new String();
            	if (resultSet.getObject("salary") != null) temp.salary = new Integer(resultSet.getInt("salary"));
            	else temp.salary = -1;
            	if (resultSet.getObject("rank") != null) temp.rank = new Integer(resultSet.getInt("rank"));
            	else temp.rank = -1;
            	if (resultSet.getString("hire") != null) temp.hire = new String(resultSet.getString("hire"));
            	else temp.hire = new String();
            	if (resultSet.getString("office_hours") != null) temp.office_hours = new String(resultSet.getString("office_hours"));
            	else temp.office_hours = new String();
            	faculty.add(temp);
            }
            
         // get data from registration
            resultSet =statement.executeQuery(registration_query);
            while(resultSet .next())
            {
            	Registration temp = new Registration();
            	
            	if (resultSet.getObject("ID") != null) temp.ID = new Integer(resultSet.getInt("ID"));
            	else temp.ID = -1;
            	if (resultSet.getString("term") != null) temp.term = new String(resultSet.getString("term"));
            	else temp.term = new String();
            	if (resultSet.getObject("registration_id") != null) temp.registration_id = new Integer(resultSet.getInt("registration_id"));
            	else temp.registration_id = -1;
            	if (resultSet.getObject("course_id") != null) temp.course_id = new Integer(resultSet.getInt("course_id"));
            	else temp.course_id = -1;
            	if (resultSet.getString("enrolled_as") != null) temp.enrolled_as = new String(resultSet.getString("enrolled_as"));
            	else temp.enrolled_as = new String();
            	if (resultSet.getString("grade") != null) temp.grade = new String(resultSet.getString("grade"));
            	else temp.grade = new String();
            	registration.add(temp);
            }
            
         // get data from staff
            resultSet =statement.executeQuery(staff_query);
            while(resultSet .next())
            {
            	Staff temp = new Staff();
            	
            	if (resultSet.getObject("ID") != null) temp.ID = new Integer(resultSet.getInt("ID"));
            	else temp.ID = -1;
            	if (resultSet.getString("name") != null) temp.setName(new String(resultSet.getString("name")));
            	else temp.setName(new String());
            	if (resultSet.getString("address") != null) temp.setAddress(new String(resultSet.getString("address")));
            	else temp.setAddress(new String());
            	if (resultSet.getString("phone_number") != null) temp.setPhone(new String(resultSet.getString("phone_number")));
            	else temp.setPhone(new String());
            	if (resultSet.getString("email_address") != null) temp.setEmail(new String(resultSet.getString("email_address")));
            	else temp.setEmail(new String());
            	if (resultSet.getString("title") != null) temp.title = new String(resultSet.getString("title"));
            	else temp.title = new String();
            	staff.add(temp);
            }
            
         // get data from student
            resultSet =statement.executeQuery(student_query);
            while(resultSet .next())
            {
            	Student temp = new Student();
            	
            	if (resultSet.getObject("ID") != null) temp.ID = new Integer(resultSet.getInt("ID"));
            	else temp.ID = -1;
            	if (resultSet.getString("name") != null) temp.setName(new String(resultSet.getString("name")));
            	else temp.setName(new String());
            	if (resultSet.getString("address") != null) temp.setAddress(new String(resultSet.getString("address")));
            	else temp.setAddress(new String());
            	if (resultSet.getString("phone_number") != null) temp.setPhone(new String(resultSet.getString("phone_number")));
            	else temp.setPhone(new String());
            	if (resultSet.getString("email_address") != null) temp.setEmail(new String(resultSet.getString("email_address")));
            	else temp.setEmail(new String());
            	if (resultSet.getString("class_status") != null) temp.class_status = new String(resultSet.getString("class_status"));
            	else temp.class_status = new String();
            	student.add(temp);
            }
            
            SQLWarning warning = statement.getWarnings();
            if (warning != null)
                throw new Exception(warning.getMessage());
        } 
        catch (SQLException e) 
        {
            Exception exception = new Exception(e.getMessage(), e);
            throw exception;
        } 
        finally 
        {
            close(statement);
            close(connection);
            close(resultSet);
            System.out.println(">>Queries Successfully Executed, ArrayLists have been updated");
        }
    }
	
	public void save() throws Exception
	{
		boolean success = false;
		PreparedStatement update = null;
		Statement _deleteTableDtataStmt = null;
		try 
        {
			this.instance = new ConnectionFactory();
			connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            _deleteTableDtataStmt = connection.createStatement();
            
            // update student
            // empty table before re-populating it
            String _deleteTableData ="TRUNCATE TABLE student";
            _deleteTableDtataStmt.executeUpdate(_deleteTableData);
            success = false;
            String updateQuery = "INSERT INTO student "+
            "(name, address, phone_number, email_address, class_status, ID)"+
            "VALUES (?, ?, ?, ?, ?, ?)";
            
            update = connection.prepareStatement(updateQuery);
            
            connection.setAutoCommit(false);
            System.out.println("\n>Updating the student... ");
            for(int i=0;i<student.size();i++)
    		{
                update.setString (1, student.get(i).getName());
                update.setString (2, student.get(i).getAddress());
                update.setString (3, student.get(i).getPhone());
                update.setString (4, student.get(i).getEmail());
                update.setString (5, student.get(i).class_status);
                update.setInt    (6, student.get(i).ID);
                update.executeUpdate();
                connection.commit();
    		}
            // update course
            // empty table before re-populating it
            _deleteTableData ="TRUNCATE TABLE course";
            _deleteTableDtataStmt.executeUpdate(_deleteTableData);
            success = false;
            updateQuery = "INSERT INTO course "+
            "(ID, title, days, max_capacity, credit)"+
            "VALUES (?, ?, ?, ?, ?)";
            
            update = connection.prepareStatement(updateQuery);
            
            connection.setAutoCommit(false);
            System.out.println("\n>Updating the course... ");
            for(int i=0;i<course.size();i++)
    		{
                update.setInt    (1, course.get(i).ID);
                update.setString (2, course.get(i).title);
                update.setString (3, course.get(i).days);
                update.setInt    (4, course.get(i).max_capacity);
                update.setInt    (5, course.get(i).credit);
                update.executeUpdate();
                connection.commit();
    		}
            
            // update faculty
            // empty table before re-populating it
            _deleteTableData ="TRUNCATE TABLE faculty";
            _deleteTableDtataStmt.executeUpdate(_deleteTableData);
            success = false;
            updateQuery = "INSERT INTO faculty "+
            "(ID, name, address, phone_number, email_address, office, salary, hire, office_hours, rank)"+
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            update = connection.prepareStatement(updateQuery);
            
            connection.setAutoCommit(false);
            System.out.println("\n>Updating the faculty... ");
            for(int i=0;i<faculty.size();i++)
    		{
                update.setInt    (1, faculty.get(i).ID);
                update.setString (2, faculty.get(i).getName());
                update.setString (3, faculty.get(i).getAddress());
                update.setString (4, faculty.get(i).getPhone());
                update.setString (5, faculty.get(i).getEmail());
                update.setString (6, faculty.get(i).office);
                update.setInt    (7, faculty.get(i).salary);
                update.setString (8, faculty.get(i).hire);
                update.setString (9, faculty.get(i).office_hours);
                update.setInt    (10, faculty.get(i).rank);
                update.executeUpdate();
                connection.commit();
    		}
            
         // update registration
            // empty table before re-populating it
            _deleteTableData ="TRUNCATE TABLE registration";
            _deleteTableDtataStmt.executeUpdate(_deleteTableData);
            success = false;
            updateQuery = "INSERT INTO registration "+
            "(ID, term, registration_id, course_id, enrolled_as, grade)"+
            "VALUES (?, ?, ?, ?, ?, ?)";
            
            update = connection.prepareStatement(updateQuery);
            
            connection.setAutoCommit(false);
            System.out.println("\n>Updating the registration... ");
            for(int i=0;i<registration.size();i++)
    		{
                update.setInt    (1, registration.get(i).ID);
                update.setString (2, registration.get(i).term);
                update.setInt  (3, registration.get(i).registration_id);
                update.setInt  (4, registration.get(i).course_id);
                update.setString (5, registration.get(i).enrolled_as);
                update.setString (6, registration.get(i).grade);
                update.executeUpdate();
                connection.commit();
    		}
            
         // update staff
            // empty table before re-populating it
            _deleteTableData ="TRUNCATE TABLE staff";
            _deleteTableDtataStmt.executeUpdate(_deleteTableData);
            success = false;
            updateQuery = "INSERT INTO staff "+
            "(ID, name, address, phone_number, email_address, title)"+
            "VALUES (?, ?, ?, ?, ?, ?)";
            
            update = connection.prepareStatement(updateQuery);
            
            connection.setAutoCommit(false);
            System.out.println("\n>Updating the staff... ");
            for(int i=0;i<staff.size();i++)
    		{
                update.setInt    (1, staff.get(i).ID);
                update.setString (2, staff.get(i).getName());
                update.setString (3, staff.get(i).getAddress());
                update.setString (4, staff.get(i).getPhone());
                update.setString (5, staff.get(i).getEmail());
                update.setString (6, staff.get(i).title);
                update.executeUpdate();
                connection.commit();
    		}
            
            connection.setAutoCommit(true);
            success = true;
            if (success == true) 
            	{
            		System.out.println("\n>Successfully updated the database \n");
            	}
            
            SQLWarning warning = statement.getWarnings();
            if (warning != null)
                throw new Exception(warning.getMessage());
        } 
        catch (SQLException e) 
        {
            Exception exception = new Exception(e.getMessage(), e);
            throw exception;
        } 
        finally 
        {
        	close(statement);
            close(connection);
            close(resultSet);
        }
	}
	
	public void print_size()
	{
		System.out.println("size of student: "+student.size());
		System.out.println("size of faculty: "+faculty.size());
		System.out.println("size of staff: "+staff.size());
		System.out.println("size of registration: "+registration.size());
		System.out.println("size of course: "+course.size());
	}
    
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                /*Ignore*/
            }
        }
    }
 
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                /*Ignore*/
            }
        }
    }
 
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                /*Ignore*/
            }
        }
    }
    
    
}