/**
 * 
 */
package co.bdigital.admin.ldap.util;

/**
 * @author hansel.ospino
 *
 */
public enum EnumLDAPErrors {
    USER_EXISTS("[LDAP: error code 68", "450"), USER_EXISTS_IN_GROUP(
            "[LDAP: error code 20",
            "451"), NO_SUCH_OBJECT("[LDAP: error code 32 - No Such Object]",
                    "452"), MODIFY_PASSWORD_ERROR(
                            "[LDAP: error code 53 - Must supply correct old password to change to new one]",
                            "462"), INVALID_CREDENTIALS(
                                    "[LDAP: error code 49 - Invalid Credentials]",
                                    "454"), USER_ERROR("",
                                            "455"), USER_DEL_ERROR("",
                                                    "456"), USER_GROUP_ERROR("",
                                                            "457"), USER_DEL_GROUP_ERROR(
                                                                    "",
                                                                    "458"), FUNCTION_NOT_FOUND(
                                                                            "La función ",
                                                                            " 501"), REQUEIRED_DATA(
                                                                                    "",
                                                                                    "459"), USER_NULL(
                                                                                            "",
                                                                                            "460"), MODIFY_ATTRIBUTE(
                                                                                                    "",
                                                                                                    "461"), INTERNAL_ERROR(
                                                                                                            "",
                                                                                                            "500");

    private String ldapErrorDescription;
    private String errorCode;

    private EnumLDAPErrors(String ldapErrorDescription, String errorCode) {
        this.setLdapErrorDescription(ldapErrorDescription);
        this.setErrorCode(errorCode);
    }

    /**
     * @return the ldapErrorDescription
     */
    public String getLdapErrorDescription() {
        return ldapErrorDescription;
    }

    /**
     * @param ldapErrorDescription
     *            the ldapErrorDescription to set
     */
    public void setLdapErrorDescription(String ldapErrorDescription) {
        this.ldapErrorDescription = ldapErrorDescription;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode
     *            the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}