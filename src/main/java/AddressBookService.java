import java.util.List;

public class AddressBookService {

    List<AddressBook> addressBookList;
    private static AddressBookConnection addressBookConnection;

    public AddressBookService() {
        addressBookConnection = AddressBookConnection.getInstance();
    }

    public List<AddressBook> readAddressBookData() throws AddressBookException {

        return this.addressBookList = addressBookConnection.readData();
    }

}