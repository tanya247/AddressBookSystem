public class AddressBookException extends Exception{
    public enum AddressBookExceptionType {
        READ_DATA_EXCEPTION;
    }

    public AddressBookExceptionType type;

    public AddressBookException(AddressBookExceptionType type, String message) {
        this.type = type;
    }


}
