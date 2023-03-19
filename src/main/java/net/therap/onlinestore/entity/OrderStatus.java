package net.therap.onlinestore.entity;

/**
 * @author nadimmahmud
 * @since 3/4/23
 */
public enum OrderStatus {

    ORDERED("Ordered"),
    READY("Ready to delivery"),
    PICKED("Picked by delivery man"),
    DELIVERED("Delivered");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
