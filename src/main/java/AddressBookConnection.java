import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AddressBookConnection {
    List<AddressBook> addressBookList = new ArrayList<>();
    private static AddressBookConnection addressBookConnection;
    private PreparedStatement recordDataStatement;

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
                //LocalDate date = resultSet.getDate("startDate").toLocalDate();
                addressBookList.add(new AddressBook(firstName, lastName, address, city, state, phoneNo, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AddressBookException(AddressBookException.AddressBookExceptionType.READ_DATA_EXCEPTION,
                    "!!Unable to read data from database!!");
        }
        return addressBookList;
    }
    public List<AddressBook> getRecordDataByName(String firstName) throws AddressBookException {
        List<AddressBook> record = null;
        if (this.recordDataStatement == null) this.preparedStatementForRecord();
        try {
            recordDataStatement.setString(1, firstName);
            ResultSet resultSet = recordDataStatement.executeQuery();
            record = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AddressBookException(AddressBookException.AddressBookExceptionType.UPDATION_DATA_EXCEPTION,
                    "!!Unable to update data from database!!");
        }
        return record;
    }

    private void preparedStatementForRecord() {
        try {
            Connection connection = this.getConnection();
            String query = "SELECT * FROM addressbook WHERE First_Name = ?";
            recordDataStatement = connection.prepareStatement(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public int updateDataUsingPreparedStatement(String firstName, String address) {
        String query = "UPDATE addressbook SET Address = ? WHERE First_Name = ?";
        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatementUpdate = connection.prepareStatement(query);
            preparedStatementUpdate.setString(1, address);
            preparedStatementUpdate.setString(2, firstName);
            return preparedStatementUpdate.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public List<AddressBook> getRecordsAddedInGivenDateRange(String date1, String date2) throws AddressBookException {
        String query = String.format("SELECT * FROM addressbook WHERE startDate BETWEEN '%s' AND '%s';", date1, date2);
        return this.getDataFromDataBase(query);

    }
    public List<AddressBook> getRecordsByCityOrState(String city, String state) throws AddressBookException {
        List<AddressBook> addressBooks = this.readData();
        String query = String.format("SELECT * FROM addressbook WHERE City='%s' OR State='%s';", city, state);
        return this.getDataFromDataBase(query);
    }

    public AddressBook addNewContact(String firstName, String lastName, String address, String city, String state,
                                     String phoneNo, String email) throws AddressBookException {
        int id = -1;
        AddressBook addressBookData = null;
        String query = String.format(
                "insert into addressBook(First_Name, Last_Name, Address, City, State, Mobile_No, Email) values ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                firstName, lastName, address, city, state,  phoneNo, email);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            int rowChanged = statement.executeUpdate(query, statement.RETURN_GENERATED_KEYS);
            if (rowChanged == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next())
                    id = resultSet.getInt(1);
            }
            addressBookData = new AddressBook(firstName, lastName, address, city, state, phoneNo, email);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AddressBookException(AddressBookException.AddressBookExceptionType.INSERTION_FAIL,"Unable to add employee!!");
        }
        return addressBookData;
    }

}



