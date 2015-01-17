package de.hhn.labswps.wefactor.web.util;

import de.hhn.labswps.wefactor.domain.BaseEntity;
import de.hhn.labswps.wefactor.domain.ObjectIdentification;

/**
 * The Class DataUtils.
 */
public class DataUtils {

    /**
     * Creates the object identification.
     *
     * @param entity
     *            the entity
     * @return the object identification
     */
    public static ObjectIdentification createObjectIdentification(
            final BaseEntity entity) {

        final ObjectIdentification obj = new ObjectIdentification();
        obj.setOidIdentification(entity.getId());
        obj.setOidName(entity.toString());
        obj.setOidType(entity.getClass().getSimpleName());

        return obj;
    }

    /**
     * Creates the object identification.
     *
     * @param entity
     *            the entity
     * @param simpleName
     *            the simple name
     * @return the object identification
     */
    public static ObjectIdentification createObjectIdentification(
            final BaseEntity entity, final String simpleName) {
        final ObjectIdentification obj = new ObjectIdentification();
        obj.setOidIdentification(entity.getId());
        obj.setOidName(entity.toString());
        obj.setOidType(simpleName);

        return obj;
    }

}
