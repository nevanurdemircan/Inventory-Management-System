package enums;

public enum UserType {
    RETAILER,
    SUPPLIER;

    public static boolean isValidType(String type) {
        for (UserType userType : UserType.values()) {
            if (userType.name().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

}
