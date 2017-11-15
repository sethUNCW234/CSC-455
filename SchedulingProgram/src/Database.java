import java.sql.*;
import java.util.ArrayList;

// based on www.vogella.com/tutorials/MySQLJava/article.html
public final class Database
{
	private final String domain = "65.184.59.231";
	private final String port = "3306";
	private final String database_name = "csc450"; //"faf9072";
	private final String sql_username = "ctd"; //"faf9072";
	private final String sql_passwd = "eG*OSrpn4NZy"; //"xyreddf15";
	
	private Connection connection;
	private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ArrayList<Employee> employees;
	
	public ArrayList<Employee> readEmployees() throws Exception
	{
		employees = new ArrayList<Employee>();
		try
		{
			connect();
			
			statement = connection.createStatement();
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM employee");
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next())
			{
				String firstName = resultSet.getString("fName");
				String lastName = resultSet.getString("lName");
				String id = resultSet.getString("employeeID");
				String phone = resultSet.getString("phone");
				String email = resultSet.getString("email");
				boolean isManager = resultSet.getBoolean("isManager");
				
				Employee employee;
				if (isManager)
					employee = new Manager(firstName, lastName, id, phone, email);
				else
					employee = new Employee(firstName, lastName, id, phone, email);
				employees.add(employee);
			}
		}
		catch (Exception e) { throw e; }
		finally
		{
			close();
		}
		return employees;
	}
	public ArrayList<Venue> readVenues() throws Exception
	{
		ArrayList<Venue> venues = new ArrayList<Venue>();
		try
		{
			connect();
			
			statement = connection.createStatement();
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM venue");
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next())
			{
				String name = resultSet.getString("name");
				String address = resultSet.getString("address");
				int tables = resultSet.getInt("tableNum");
				
				Venue venue = new Venue(name, address, tables);
				venues.add(venue);
			}
		}
		catch (Exception e) { throw e; }
		finally
		{
			close();
		}
		return venues;
	}
	
	private void connect() throws Exception
	{
		try
		{
			// load MySql driver
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + domain + ":" + port + "/" + database_name,
					sql_username, sql_passwd);
		}
		catch (Exception e) { throw e; }
	}
	
	private void close()
	{
		try
		{
			if (resultSet != null) resultSet.close();
			if (statement!= null) statement.close();
			if (connection != null) connection.close();
		}
		catch (Exception e) { }
	}
}
