/**
 * 
 */
package co.bdigital.admin.util;

import co.bdigital.cmm.ejb.util.Constant;

/**
 * @author juan.molinab
 *
 */
public enum UserStatusEnum {

    ACTIVE("Activo", Constant.INT_ZERO), INACTIVE("Inactivo",
            Constant.COMMON_INT_UNO);

    private final String desc;
    private final int value;

    /**
     * Constructor enum UserStatus
     * 
     * @param desc
     * @param value
     */
    UserStatusEnum(String desc, int value) {
        this.desc = desc;
        this.value = value;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

}
