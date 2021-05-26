public class AddressBookException extends Exception{
    public enum AddressBookExceptionType {
        READ_DATA_EXCEPTION, UPDATION_DATA_EXCEPTION, INSERTION_FAIL
    }

    public AddressBookExceptionType type;

    public AddressBookException(AddressBookExceptionType type, String message) {
        this.type = type;
    }


}