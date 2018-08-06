/**
 * 
 */
package co.bdigital.admin.model;

import java.io.Serializable;

/**
 * @author hansel.ospino
 *
 */
public class ProcessResult implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -277280597570231060L;
    private String id;
    private String description;

    /**
     * @param id
     * @param description
     */
    public ProcessResult(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
