import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddressBookService {

    List<AddressBook> addressBookList;
    private static AddressBookConnection addressBookConnection;
    private Arrays record;

    public AddressBookService() {
        addressBookConnection = AddressBookConnection.getInstance();
    }

    public List<AddressBook> readAddressBookData() throws AddressBookException {

        return this.addressBookList = addressBookConnection.readData();
    }
    public void updateAddress(String firstName, String address) throws AddressBookException{
        int result = new AddressBookConnection().updateDataUsingPreparedStatement(firstName,address);
        if (result == 0)
            return;
        AddressBook addressBook = this.getAddressBookData(firstName);
        if (addressBook != null)
            addressBook.setAddress(address);
    }

    private AddressBook getAddressBookData(String firstName) throws AddressBookException {
        addressBookList = this.readAddressBookData();
        return this.addressBookList.stream()
                .filter(addressBook -> addressBook.getFirstName().equals(firstName))
                .findFirst()
                .orElse(null);
    }

    public boolean checkAddressBookInSyncWithDB(String firstName) throws AddressBookException {
        List<AddressBook> addressBooks = addressBookConnection.getRecordDataByName(firstName);

        return addressBooks.get(0).equals(getAddressBookData(firstName));
    }
    public List<AddressBook> getRecordAddedInDateRange(String date1, String date2) throws AddressBookException {
        List<AddressBook> addressBooks = addressBookConnection.getRecordsAddedInGivenDateRange(date1, date2);
        return addressBooks;
    }
    public List<AddressBook> getRecordsAddedByCityOrStateName(String city, String state) throws AddressBookException {
        List<AddressBook> addressBooks = addressBookConnection.getRecordsByCityOrState(city,state);
        return addressBooks;
    }
    public void addNewContact(String firstName, String lastName,  String address, String city, String state,
                              String phoneNo, String email) throws AddressBookException {
        addressBookList = this.readAddressBookData();
        addressBookList.add(addressBookConnection.addNewContact(firstName, lastName,  address, city, state,  phoneNo,
                email));
    }


}