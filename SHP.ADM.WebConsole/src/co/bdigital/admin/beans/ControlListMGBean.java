package co.bdigital.admin.beans;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.bdigital.admin.ejb.controller.view.ControlListValidationServiceBeanLocal;
import co.bdigital.admin.messaging.validatecustomer.CustomerType;
import co.bdigital.admin.model.PermissionByRol;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.UtilADM;
import co.bdigital.admin.util.TypeIdClientEnum;
import co.bdigital.shl.tracer.CustomLogger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;

/**
 * Bean para comunicar con la vista de listas de control
 * 
 * @author juan.molinab
 *
 */
@ManagedBean(name = "controlListMGBean")
@RequestScoped
public class ControlListMGBean {

    /** Numero del documento para buscar el usuario **/
    private String numberDocument;
    /** Tipo de documento con el que se realizara la busqueda **/
    private String documentType;
    /** Nombres del cliente **/
    private String names;
    /** Apellidos del cliente **/
    private String lastNames;
    /** Lista de los posibles estados de un usuario **/
    private List<SelectItem> selectOneItemTypeId;
    /** Lista para almacenar la lista de control **/
    private ArrayList<CustomerType> listCustomerType;
    /** EJB para invocar el servicio de validacion de documento **/
    @EJB
    private ControlListValidationServiceBeanLocal controlListValidationServiceBean;
    /** Codigo del pais **/
    private String countryId;
    /** Ubicacion de la carpeta temporal donde se suben los archivos **/
    private String realPath;
    /** Utilidad para almacenar la informacion de los logs **/
    private CustomLogger logger;
    /** Permisos de la accion **/
    private PermissionByRol permissionRol;

    /**
     * Constructor CheckListMGBean
     */
    public ControlListMGBean() {
        logger = new CustomLogger(this.getClass());
        @SuppressWarnings("unchecked")
        ArrayList<CustomerType> customerListSesion = (ArrayList<CustomerType>) FacesContext
                .getCurrentInstance().getExternalContext().getSessionMap()
                .get(ConstantADM.CUSTOMER_LIST_CONTROL);
        if (null == customerListSesion || customerListSesion.size() == 0) {
            listCustomerType = new ArrayList<CustomerType>();
        } else {
            listCustomerType = customerListSesion;
        }

        this.countryId = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.COUNTRY_ID);
        permissionRol = (PermissionByRol) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.PERMISSIONS_BY_ROL_SESION);
    }

    /**
     * @return the numberDocument
     */
    public String getNumberDocument() {
        return numberDocument;
    }

    /**
     * @param numberDocument
     *            the numberDocument to set
     */
    public void setNumberDocument(String numberDocument) {
        this.numberDocument = numberDocument;
    }

    /**
     * @return the documentType
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * @param documentType
     *            the documentType to set
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * @return the listCustomerType
     */
    public List<CustomerType> getListCustomerType() {
        return listCustomerType;
    }

    /**
     * @param listCustomerType
     *            the listCustomerType to set
     */
    public void setListCustomerType(ArrayList<CustomerType> listCustomerType) {
        this.listCustomerType = listCustomerType;
    }

    /**
     * @return the countryId
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * @param countryId
     *            the countryId to set
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    /**
     * Método para validar un solo documento, esta peticion se realiza desde el
     * formulario
     */
    public void validateOnlyOneCustomer() {
        listCustomerType = new ArrayList<CustomerType>();
        CustomerType customer = new CustomerType();
        customer.setTypeId(this.documentType);
        customer.setIdNumber(this.numberDocument);
        customer.setNames(this.names);
        customer.setLastNames(this.lastNames);
        listCustomerType.add(customer);
        // Se llama al medoto para validar el documento
        validateCustomerList(listCustomerType);

    }

    /**
     * Método para validar una lista de documentos, esta peticion se realiza al
     * cargar un archivo CSV
     */
    public void validateListCustomer() {
        try {
            FacesMessage message;
            realPath = (String) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap()
                    .get(ConstantADM.PATH_FILE_UPLOAD);
            if (null != realPath && "" != realPath) {
                getData(realPath);
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        ConstantADM.WORD_ERROR, ConstantADM.CHOOSE_FILE_UPLOAD);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }

        } catch (FileNotFoundException e) {
            logger.error(ConstantADM.FILE_NOT_FOUNT_UPLOAD, e);
        } catch (IOException e) {
            logger.error(ConstantADM.ERROR_READ_FILE_UPLOAD, e);
        }
    }

    /**
     * @return the names
     */
    public String getNames() {
        return names;
    }

    /**
     * @param names
     *            the names to set
     */
    public void setNames(String names) {
        this.names = names;
    }

    /**
     * @return the lastNames
     */
    public String getLastNames() {
        return lastNames;
    }

    /**
     * @param lastNames
     *            the lastNames to set
     */
    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    /**
     * Metodo para devolver a la vista los tipos de documento con su codigo
     * 
     * @return the selectOneItemTypeId
     */
    public List<SelectItem> getSelectOneItemTypeId() {
        this.selectOneItemTypeId = new ArrayList<SelectItem>();
        TypeIdClientEnum CC = TypeIdClientEnum.CC;
        TypeIdClientEnum TI = TypeIdClientEnum.TI;
        TypeIdClientEnum PP = TypeIdClientEnum.PP;
        SelectItem selectItemCC = new SelectItem(CC.getKey(), CC.getDesc());
        SelectItem selectItemTI = new SelectItem(TI.getKey(), TI.getDesc());
        SelectItem selectItemPP = new SelectItem(PP.getKey(), PP.getDesc());
        this.selectOneItemTypeId.add(selectItemCC);
        this.selectOneItemTypeId.add(selectItemTI);
        this.selectOneItemTypeId.add(selectItemPP);
        return this.selectOneItemTypeId;
    }

    /**
     * Metodo para validar una lista de documentos contra listas de control
     * 
     * @param customer
     *            Lista de documentos
     * @return La lista con las validaciones correspondientes
     */
    public List<CustomerType> validateCustomerList(
            ArrayList<CustomerType> customer) {
        @SuppressWarnings("unchecked")
        ArrayList<CustomerType> customerCopy = (ArrayList<CustomerType>) customer
                .clone();
        try {
            customer = controlListValidationServiceBean.validateCustomerList(
                    customer, this.countryId);
        } catch (Exception e) {
            customer = customerCopy;
            logger.error(ConstantADM.ERROR_VALIDATE_LIST_CONTROL, e);
        }

        if (null != customer && customer.size() > 0) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap()
                    .put(ConstantADM.CUSTOMER_LIST_CONTROL, customer);
        }
        return customer;
    }

    /**
     * Metodo para cargar el documento CSV
     * 
     * @param event
     * @throws IOException
     */
    public void loadData(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        ExternalContext ec = FacesContext.getCurrentInstance()
                .getExternalContext();
        String path = ec.getRealPath("/");
        realPath = path + File.separator + ConstantADM.RESOURCES_PATH
                + File.separator + ConstantADM.FILE_PATH + File.separator
                + file.getFileName();
        if (null != realPath || "" != realPath) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap()
                    .put(ConstantADM.PATH_FILE_UPLOAD, realPath);
        }
        FacesMessage message;
        if (saveFileCSV(realPath, file)) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    ConstantADM.WORD_SUCCESS, ConstantADM.FILE_UPLOAD_SUCCESS);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.WORD_ERROR, ConstantADM.FILE_UPLOAD_ERROR);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Metodo para guardar temporalmente el archivo SCV
     * 
     * @param realPath
     * @param file
     * @return
     */
    public boolean saveFileCSV(String realPath, UploadedFile file) {
        try {
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            bis = new BufferedInputStream(file.getInputstream());
            FileOutputStream out = new FileOutputStream(realPath);
            bos = new BufferedOutputStream(out);
            byte[] buffer = new byte[(int) file.getSize()];
            int contador = 0;
            while ((contador = bis.read()) != -1) {
                bos.write(contador);
            }
            bos.flush();
            bos.close();
            bis.close();
            return true;
        } catch (IOException ioe) {
            logger.error(ConstantADM.ERROR_READ_FILE_UPLOAD, ioe);
            return false;
        }
    }

    /**
     * Metodo para realizar la lectura de la informacion del archivo SCV
     * 
     * @param realPath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void getData(String realPath) throws FileNotFoundException,
            IOException {
        BufferedReader bufferReader = new BufferedReader(new FileReader(
                realPath));
        String linea;
        StringTokenizer token;
        CustomerType customer;
        listCustomerType = new ArrayList<CustomerType>();
        int cont;
        FacesMessage message = null;
        boolean isValidDocument = true;
        while (bufferReader.ready()) {
            if (!isValidDocument) {
                break;
            }
            linea = bufferReader.readLine();
            if (null == linea || "".equals(linea)) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.WORD_ERROR,
                        ConstantADM.ERROR_EMPTY_LINE_UPLOAD);
                break;
            }
            token = new StringTokenizer(linea, ",");
            if (token.countTokens() < 4) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.WORD_ERROR,
                        ConstantADM.ERROR_MISSING_COLUMNS);
                break;
            }
            customer = new CustomerType();
            cont = 0;
            while (token.hasMoreTokens() && isValidDocument) {
                String data = token.nextToken();
                switch (cont) {
                case 0:
                    if (UtilADM.validateDocumentType(data)) {
                        customer.setTypeId(data);
                    } else {
                        isValidDocument = false;
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                ConstantADM.WORD_ERROR,
                                ConstantADM.ERROR_TYPE_DOCUMENT_INVALID_UPLOAD);
                    }
                    break;
                case 1:
                    if (UtilADM.isNumeric(data)) {
                        customer.setIdNumber(data);
                    } else {
                        isValidDocument = false;
                        message = new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                ConstantADM.WORD_ERROR,
                                ConstantADM.ERROR_NUMBER_DOCUMENT_INVALID_UPLOAD);
                    }
                    break;
                case 2:
                    customer.setNames(data);
                    break;
                default:
                    customer.setLastNames(data);
                    break;
                }
                cont++;
            }
            listCustomerType.add(customer);
        }
        bufferReader.close();
        // Se elimina el archivo de la carpeta temporal
        deleteDocument(realPath);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put("pathFileUpload", "");
        if (message != null) {
            listCustomerType = new ArrayList<CustomerType>();
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            // Se llama al medoto para validar el documento
            validateCustomerList(listCustomerType);
        }
    }

    public void deleteDocument(String path) {
        try {
            File ruta = new File(path.toString());
            ruta.delete();
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_READ_FILE_UPLOAD, e);
        }

    }

    /**
     * Metodo para generar archivo PDF con la informacion de las listas de
     * control
     * 
     * @param document
     * @throws IOException
     * @throws DocumentException
     */
    @SuppressWarnings("unchecked")
    public void preProcessPDF(Object document) throws IOException,
            DocumentException {
        listCustomerType = (ArrayList<CustomerType>) FacesContext
                .getCurrentInstance().getExternalContext().getSessionMap()
                .get(ConstantADM.CUSTOMER_LIST_CONTROL);

        final Document pdf = (Document) document;
        HeaderFooter header = new HeaderFooter(new Phrase(
                ConstantADM.HEADER_FILE_PDF), false);
        pdf.setHeader(header);

        pdf.setPageSize(PageSize.A4);
        pdf.open();

    }

    /**
     * @return the permissionRol
     */
    public PermissionByRol getPermissionRol() {
        return permissionRol;
    }

    /**
     * @param permissionRol
     *            the permissionRol to set
     */
    public void setPermissionRol(PermissionByRol permissionRol) {
        this.permissionRol = permissionRol;
    }
}
