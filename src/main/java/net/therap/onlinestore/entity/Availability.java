package net.therap.onlinestore.entity;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
public enum Availability {

    AVAILABLE("Available"),
    UNAVAILABLE("Unavailable");

    private final String label;

    Availability(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
