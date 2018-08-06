package co.bdigital.admin.component;

import java.io.Serializable;

public class LinkNode implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String label;
    private String link;

    public LinkNode(String label, String link){
        this.label = label;
        this.link = link;
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
    
    
}
