import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        addressBookService.updateEmployeeSalary("Sandip","Kanpur");
        System.out.println(addressBooks);
        boolean result = addressBookService.checkEmployeePayrollInSyncWithDB("Sandip");
        Assertions.assertTrue(result);
    }
}


