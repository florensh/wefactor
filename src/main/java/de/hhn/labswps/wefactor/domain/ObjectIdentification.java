package de.hhn.labswps.wefactor.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ObjectIdentification {

    private String oidType;

    private String oidName;

    private String oidIdentification;

    public String getOidType() {
        return oidType;
    }

    public void setOidType(String oidType) {
        this.oidType = oidType;
    }

    public String getOidName() {
        return oidName;
    }

    public void setOidName(String oidName) {
        this.oidName = oidName;
    }

    public String getOidIdentification() {
        return oidIdentification;
    }

    public void setOidIdentification(String oidIdentification) {
        this.oidIdentification = oidIdentification;
    }

}
