package tect.her.ccm.domain.enumeration;

/**
 * The Sex enumeration.
 */
public enum Sex {
    MASCULIN("M"),
    FEMININ("F");

    private final String value;

    Sex(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
