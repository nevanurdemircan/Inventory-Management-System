package enums;

public enum UserType {
    RETAILER("Retailer"),
    SUPPLIER("Supplier");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserType fromString(String value) {
        for (UserType type : values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid UserType: " + value);
    }
}
