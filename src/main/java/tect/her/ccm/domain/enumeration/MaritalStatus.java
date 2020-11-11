package tect.her.ccm.domain.enumeration;

/**
 * The MaritalStatus enumeration.
 */
public enum MaritalStatus {
    CELIBATIRE("C"),
    MARIE("M"),
    VEUF("V"),
    DIVORCE("D");

    private final String value;

    MaritalStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
