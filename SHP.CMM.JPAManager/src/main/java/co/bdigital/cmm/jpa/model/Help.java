package co.bdigital.cmm.jpa.model;
/**
 * La clase Help ofrece el esquema del objeto REST para ser retornado como parte de las respuestas al Front
 * @author daniel.pareja
 *
 */
public class Help {
	
	private String content;
	private String helpId;
	private String isLeaf;
	private String helpIdFather;
	
	/**
	 * Contructor de la clase
	 */
	public Help() {
	}

	/**
	 * Contructor de la clase
	 * @param content                Contenido del item
	 * @param helpId                 id del item
	 * @param isLeaf                 indica si hoja o no
	 * @param helpIdFather           Id del item padre
	 */
	public Help(String content, String description, String helpId, String isLeaf, String helpIdFather) {
		this.content = content;
		this.helpId = helpId;
		this.isLeaf = isLeaf;
		this.helpIdFather = helpIdFather;
	}

	/**
	 * Metodo para obtener el content
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Metodo para modificar el content
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Metodo para obtener el helpId
	 * @return helpId
	 */
	public String getHelpId() {
		return helpId;
	}

	/**
	 * Metodo para modificar el helpId
	 * @param helpId
	 */
	public void setHelpId(String helpId) {
		this.helpId = helpId;
	}

	/**
	 * Metodo para obtener el isLeaf
	 * @return isLeaf
	 */
	public String getIsLeaf() {
		return isLeaf;
	}

	/**
	 * Metodo para modificar el isLeaf
	 * @param isLeaf
	 */
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * Metodo para obtener el helpIdFather
	 * @return helpIdFather
	 */
	public String getHelpIdFather() {
		return helpIdFather;
	}

	/**
	 * Metodo para modificar el helpIdFather
	 * @param helpIdFather
	 */
	public void setHelpIdFather(String helpIdFather) {
		this.helpIdFather = helpIdFather;
	}
}