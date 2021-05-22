import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AddressBookServiceTest {
    AddressBookService addressBookService = new AddressBookService();
    List<AddressBook> addressBookList;

    @Test
    public void givenAddressBook_WhenRetrived_ShouldReturnAddressBookSize() throws AddressBookException {
        addressBookList = addressBookService.readAddressBookData();
        Assertions.assertEquals(6, addressBookList.size());
    }
}
