package net.therap.onlinestore.entity;

/**
 * @author nadimmahmud
 * @since 4/3/23
 */
public enum UserType {

    ADMIN("Admin"),
    SHOP_KEEPER("Shop Keeper"),
    DELIVERY_MAN("Delivery Man"),
    CUSTOMER("Customer");

    private final String label;

    UserType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
