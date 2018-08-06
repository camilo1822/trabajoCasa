package co.bdigital.admin.model;

import java.io.Serializable;

/**
 * 
 * @author juan.molinab
 *
 */
public class ActionRol implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** Nombre de la accion que se mostrara en el arbol de navegacion **/
    private String name;
    /** Url de las acciones permitidas **/
    private String url;
    /** Id de la accion */
    private long idAction;

    /**
     * Constructor de la clase ActionRol
     * 
     * @param name
     * @param url
     */
    public ActionRol(String name, String url, long idAction) {
        this.name = name;
        this.url = url;
        this.idAction = idAction;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the idAction
     */
    public long getIdAction() {
        return idAction;
    }

    /**
     * @param idAction
     *            the idAction to set
     */
    public void setIdAction(long idAction) {
        this.idAction = idAction;
    }
}
