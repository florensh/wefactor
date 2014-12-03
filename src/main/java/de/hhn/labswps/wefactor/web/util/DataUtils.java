package de.hhn.labswps.wefactor.web.util;

import de.hhn.labswps.wefactor.domain.BaseEntity;
import de.hhn.labswps.wefactor.domain.ObjectIdentification;

public class DataUtils {

    public static ObjectIdentification createObjectIdentification(
            BaseEntity entity) {

        ObjectIdentification obj = new ObjectIdentification();
        obj.setOidIdentification(entity.getId());
        obj.setOidName(entity.toString());
        obj.setOidType(entity.getClass().getSimpleName());

        return obj;
    }

    public static ObjectIdentification createObjectIdentification(
            BaseEntity entity, String simpleName) {
        ObjectIdentification obj = new ObjectIdentification();
        obj.setOidIdentification(entity.getId());
        obj.setOidName(entity.toString());
        obj.setOidType(simpleName);

        return obj;
    }

}
