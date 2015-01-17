package de.hhn.labswps.wefactor.domain;

import javax.persistence.Embeddable;

/**
 * The Class ObjectIdentification.
 */
@Embeddable
public class ObjectIdentification {

    /** The oid type. */
    private String oidType;

    /** The oid name. */
    private String oidName;

    /** The oid identification. */
    private Long oidIdentification;

    /**
     * Gets the oid type.
     *
     * @return the oid type
     */
    public String getOidType() {
        return this.oidType;
    }

    /**
     * Sets the oid type.
     *
     * @param oidType
     *            the new oid type
     */
    public void setOidType(final String oidType) {
        this.oidType = oidType;
    }

    /**
     * Gets the oid name.
     *
     * @return the oid name
     */
    public String getOidName() {
        return this.oidName;
    }

    /**
     * Sets the oid name.
     *
     * @param oidName
     *            the new oid name
     */
    public void setOidName(final String oidName) {
        this.oidName = oidName;
    }

    /**
     * Gets the oid identification.
     *
     * @return the oid identification
     */
    public Long getOidIdentification() {
        return this.oidIdentification;
    }

    /**
     * Sets the oid identification.
     *
     * @param oidIdentification
     *            the new oid identification
     */
    public void setOidIdentification(final Long oidIdentification) {
        this.oidIdentification = oidIdentification;
    }

}
