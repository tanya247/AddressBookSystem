import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class AddressBookServiceTest {
    AddressBookService addressBookService = new AddressBookService();
    List<AddressBook> addressBookList;

    @Test
    public void givenAddressBook_WhenRetrived_ShouldReturnAddressBookSize() throws AddressBookException {
        addressBookList = addressBookService.readAddressBookData();
        System.out.println(addressBookList);
        Assertions.assertEquals(6, addressBookList.size());
    }
    @Test
    public void givenNewAddress_WhenUpdated_ShouldSyncWithDatabase() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBook>addressBooks = addressBookService.readAddressBookData();
        addressBookService.updateAddress("Sandip","Kanpur");
        System.out.println(addressBooks);
        boolean result = addressBookService.checkAddressBookInSyncWithDB("Sandip");
        Assertions.assertTrue(result);
    }
    @Test
    public void givenDateRangeForRecord_WhenRetrieved_ShouldReturnProperData() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBook> recordDataInGivenDateRange = addressBookService.getRecordAddedInDateRange("2020-01-01","2015-05-20");
        Assertions.assertEquals(3,recordDataInGivenDateRange.size());
    }
    @Test
    public void givenNameofCityOrState_WhenRetrieved_ShouldReturnProperData() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBook> addressBooks = addressBookService.getRecordsAddedByCityOrStateName("Ghaziabad","UP");
        System.out.println(addressBooks);
        Assertions.assertEquals(5,addressBooks.size());
    }
    @Test
    public void givenNewContact_WhenAdded_ShouldSyncWithDB() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.addNewContact("Tanya","Kansal","chandi mandir","Hapur","UP","7458964411","abc@gmail.com");
        Assertions.assertTrue(addressBookService.checkAddressBookInSyncWithDB("Tanya"));

    }
    @Test
    public void givenMultipleContact_WhenAdded_ShouldSyncWithDB() throws AddressBookException {
        AddressBook[] addressBooks= {
                new AddressBook("Palak", "Singhal", "abc", "Hapur",
                        "UP", "789456244", "Pss@gmail.com"),
                new AddressBook("Paras", "Singhal", "abcd", "Hapur",
                        "UP", "785625444", "Parass@gmail.com")

        };
        List<AddressBook> addressBookList = Arrays.asList(addressBooks);
        addressBookService.addMultipleContactsToRecord(addressBookList);
        Assertions.assertTrue(addressBookService.checkAddressBookInSyncWithDB("Palak"));
        Assertions.assertTrue(addressBookService.checkAddressBookInSyncWithDB("Paras"));

    }
}


