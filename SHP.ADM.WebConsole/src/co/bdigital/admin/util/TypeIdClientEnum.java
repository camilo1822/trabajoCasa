package co.bdigital.admin.util;

/*
 * @author juan.molinab
 * Enumerador para manejar los tipos de id de un cliente
 */
public enum TypeIdClientEnum {

    CC(ConstantADM.TYPE_ID_CC_DESC, ConstantADM.TYPE_ID_CC_CODE), TI(
            ConstantADM.TYPE_ID_TI_DESC, ConstantADM.TYPE_ID_TI_CODE), PP(
            ConstantADM.TYPE_ID_PP_DESC, ConstantADM.TYPE_ID_PP_CODE);

    private final String desc;
    private final String key;

    /**
     * Constructor enum UserStatus
     * 
     * @param desc
     * @param number
     */
    TypeIdClientEnum(String desc, String key) {
        this.desc = desc;
        this.key = key;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

}
