import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AddressBookConnection {
    List<AddressBook> addressBookList;
    private static AddressBookConnection addressBookConnection;
    public Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/ addressbook_service?useSS1=false";
        String userName = "root";
        String password = "Ruchi-789";
        Connection connection;
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        return connection;
    }
    public static AddressBookConnection getInstance() {
        if (addressBookConnection == null)
            addressBookConnection = new AddressBookConnection();
        return addressBookConnection;
    }
    public List<AddressBook> readData() throws AddressBookException {
        addressBookList = new ArrayList<AddressBook>();
        String sql = "SELECT * FROM addressbook; ";
        return this.getDataFromDataBase(sql);
    }

    private List<AddressBook> getDataFromDataBase(String sql) throws AddressBookException {
        addressBookList = new ArrayList<AddressBook>();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            addressBookList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AddressBookException(AddressBookException.AddressBookExceptionType.READ_DATA_EXCEPTION,
                    "!!Unable to read data from database!!");
        }
        return addressBookList;
    }

    private List<AddressBook> getAddressBookData(ResultSet resultSet) throws AddressBookException {
        addressBookList = new ArrayList<AddressBook>();
        try {
            while (resultSet.next()) {
                String firstName = resultSet.getString("First_Name");
                String lastName = resultSet.getString("Last_Name");
                String address = resultSet.getString("Address");
                String city = resultSet.getString("City");
                String state = resultSet.getString("State");
                String phoneNo = resultSet.getString("Mobile_No");
                String email = resultSet.getString("Email");
                addressBookList.add(new AddressBook(firstName, lastName, address, city, state, phoneNo, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AddressBookException(AddressBookException.AddressBookExceptionType.READ_DATA_EXCEPTION,
                    "!!Unable to read data from database!!");
        }
        return addressBookList;
    }
}
