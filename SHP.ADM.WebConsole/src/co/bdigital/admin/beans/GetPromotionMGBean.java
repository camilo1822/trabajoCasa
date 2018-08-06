package co.bdigital.admin.beans;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;

import co.bdigital.admin.ejb.controller.view.GetPromotionServiceBeanLocal;
import co.bdigital.admin.ejb.controller.view.GetRulesDescriptionServiceBeanLocal;
import co.bdigital.admin.ejb.controller.view.UpdatePromotionServiceBeanLocal;
import co.bdigital.admin.ejb.controller.view.UserInfoServiceBeanLocal;
import co.bdigital.admin.messaging.services.getrulesdescription.DescriptionType;
import co.bdigital.admin.model.ActionRol;
import co.bdigital.admin.model.PermissionByRol;
import co.bdigital.admin.model.ProcessResult;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.UtilADM;
import co.bdigital.cmm.jpa.model.AwRol;
import co.bdigital.cmm.jpa.model.PromocionComercio;
import co.bdigital.cmm.jpa.model.PromocionOperacion;
import co.bdigital.cmm.jpa.model.PromocionUsuario;
import co.bdigital.shl.tracer.CustomLogger;
import co.nequi.message.integration.services.getpromotiondetail.RulesType;

/**
 * 
 * @author juan.arboleda
 *
 */
@ManagedBean(name = "getPromotionMGBean")
@ViewScoped
public class GetPromotionMGBean implements Serializable {

    /**
     * 
     */
    /* Lista de los comercios */
    private List<PromocionComercio> listComerce;

    /* Lista de los comercios */
    private List<PromocionUsuario> listUsers;

    private static final long serialVersionUID = 1L;
    private String nameService;
    /* Arreglo donde se guarda la informacion de las acciones con su id */
    private transient List<ActionRol> actionsRol = null;
    /* Lista de todos los roles */
    private List<AwRol> listAllRol;
    /* Logger */
    private CustomLogger logger;
    /* Permisos de la accion */
    private PermissionByRol permissionRol;
    /* Codigo del pais */
    private String countryId;
    /** Lista de las descripciones **/
    private List<SelectItem> selectDescription;
    private List<DescriptionType> descriptionType;

    private String id;
    private String description;

    private String fromDate;
    private String toDate;
    private String fromHour;
    private String toHour;

    private Date dateFrom;
    private Date dateTo;
    private Date hourFrom;
    private Date hourTo;

    private String stringFromDate;
    private String stringToDate;
    private Date saveDateFrom;
    private Date saveDateTo;

    private Double minValue = ConstantADM.COMMON_DOUBLE_ZERO;
    private Double maxValue = ConstantADM.COMMON_DOUBLE_ZERO;
    private BigDecimal value = ConstantADM.COMMON_BIG_DECIMAL_ZERO;
    private String minValueTxt;
    private String maxValueTxt;
    private String valueTxt;
    private String valueType;
    private String accountingAccount;
    private BigDecimal availableValue = ConstantADM.COMMON_BIG_DECIMAL_ZERO;
    private BigDecimal availableValueOld;
    private Double ocurrence = ConstantADM.COMMON_DOUBLE_ZERO;
    private String ocurrenceTxt;
    private String status;
    private String notificationType;
    private String subject;
    private String message;
    private String code;
    private BigDecimal maximumBudget;
    private String maximumBudgetTxt;
    private Boolean resetCampaign;
    private BigDecimal amountToDeliver;
    private BigDecimal maximumBudgetOld;
    private BigDecimal availableValueAfterModify;
    private String statusTxt;
    private String frequencyMinimum;
    private String frequencyRestart;
    private String idPromotion;
    private String usersSize;
    private String restitutionName;
    private int createView;

    /* Lista de todos las reglas */
    private RulesType rulesType;

    private transient List<RulesType> listRule = null;

    @EJB
    private transient GetPromotionServiceBeanLocal getPromotionServiceBeanLocal;
    @EJB
    private transient UpdatePromotionServiceBeanLocal updatePromotionServiceBeanLocal;
    @EJB
    private transient GetRulesDescriptionServiceBeanLocal getRulesDescriptionServiceBeanLocal;
    @EJB
    private transient UserInfoServiceBeanLocal userInfoServiceBean;
    private transient List<ProcessResult> processResults = new ArrayList<>();
    private transient Integer processResultsSize;

    @SuppressWarnings("unchecked")
    public GetPromotionMGBean() {
        logger = new CustomLogger(GetPromotionMGBean.class);
        this.countryId = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.COUNTRY_ID);
        this.actionsRol = (List<ActionRol>) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.ACTIONS_BY_ROL_SESION);
        permissionRol = (PermissionByRol) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap()
                .get(ConstantADM.PERMISSIONS_BY_ROL_SESION);
        this.listComerce = new ArrayList<>();
        this.listUsers = new ArrayList<>();
        this.createView = ConstantADM.COMMON_ONE;
    }

    @PostConstruct
    public void init() {
        this.listAllRol = getListRol(this.countryId);
        getSelect();
    }

    /**
     * Metodo llamado desde el boton de guardar perfil, el cual guarda un nuevo
     * rol
     */
    public void showPromotion() {

        FacesMessage messages = null;
        this.resetCampaign = Boolean.FALSE;
        try {
            this.descriptionType = getRulesDescriptionServiceBeanLocal
                    .getRulesDescription(countryId);
            String servicio = descriptionType.get(Integer.parseInt(nameService))
                    .getService();
            code = descriptionType.get(Integer.parseInt(nameService))
                    .getPromotionType();
            idPromotion = descriptionType.get(Integer.parseInt(nameService))
                    .getIdPromOperacion();
            String descrptionRule = descriptionType
                    .get(Integer.parseInt(nameService)).getDescriptionRule();
            rulesType = getPromotionServiceBeanLocal.getPromotion(servicio,
                    countryId, code, ConstantADM.STRING_EMPTY, idPromotion,
                    descrptionRule);

            id = rulesType.getId();
            if (null == rulesType.getDescription()) {
                description = ConstantADM.STRING_EMPTY;
            } else {
                description = rulesType.getDescription();
            }
            stringToDate = rulesType.getToDate();
            stringFromDate = rulesType.getFromDate();
            if (null == rulesType.getMinValue() || ConstantADM.STRING_EMPTY
                    .equals(String.valueOf(rulesType.getMinValue()))) {
                minValue = ConstantADM.COMMON_DOUBLE_ZERO;
            } else {
                minValue = Double.parseDouble(rulesType.getMinValue());
            }
            if (null == rulesType.getMaxValue() || ConstantADM.STRING_EMPTY
                    .equals(String.valueOf(rulesType.getMaxValue()))) {
                maxValue = ConstantADM.COMMON_DOUBLE_ZERO;
            } else {
                maxValue = Double.parseDouble(rulesType.getMaxValue());
            }
            if (null == rulesType.getValue() || ConstantADM.STRING_EMPTY
                    .equals(String.valueOf(rulesType.getValue()))) {
                value = ConstantADM.COMMON_BIG_DECIMAL_ZERO;
            } else {
                value = new BigDecimal(rulesType.getValue());
            }

            valueType = rulesType.getValueType();
            if (null == rulesType.getAccountingAccount()) {
                accountingAccount = ConstantADM.STRING_EMPTY;
            } else {
                accountingAccount = rulesType.getAccountingAccount();
            }
            if (null == rulesType.getAvailableValue()
                    || ConstantADM.STRING_EMPTY.equals(
                            String.valueOf(rulesType.getAvailableValue()))) {
                availableValue = ConstantADM.COMMON_BIG_DECIMAL_ZERO;
            } else {
                availableValue = new BigDecimal(rulesType.getAvailableValue());
            }
            availableValueOld = availableValue;

            if (null == rulesType.getMaximumBudget() || ConstantADM.STRING_EMPTY
                    .equals(String.valueOf(rulesType.getMaximumBudget()))) {
                maximumBudget = ConstantADM.COMMON_BIG_DECIMAL_ZERO;
            } else {
                maximumBudget = new BigDecimal(rulesType.getMaximumBudget());
            }
            maximumBudgetOld = maximumBudget;

            if (null == rulesType.getOcurrence() || ConstantADM.STRING_EMPTY
                    .equals(String.valueOf(rulesType.getOcurrence()))) {
                ocurrence = ConstantADM.COMMON_DOUBLE_ZERO;
            } else {
                ocurrence = Double.parseDouble(rulesType.getOcurrence());
            }

            status = rulesType.getStatus();
            if (Boolean.TRUE.toString().equalsIgnoreCase(status)) {
                status = ConstantADM.STRING_UNO;
            } else {
                status = ConstantADM.COMMON_STRING_ZERO;
            }

            notificationType = rulesType.getNotificationtype();
            if (null == rulesType.getSubject()) {
                subject = ConstantADM.STRING_EMPTY;
            } else {
                subject = rulesType.getSubject();
            }
            if (null == rulesType.getMessage()) {
                message = ConstantADM.STRING_EMPTY;
            } else {
                message = rulesType.getMessage();
            }
            if (null == rulesType.getFrequencyMinimum()
                    || ConstantADM.COMMON_STRING_NULL
                            .equals(rulesType.getFrequencyMinimum())) {
                frequencyMinimum = ConstantADM.STRING_EMPTY;
            } else {
                frequencyMinimum = rulesType.getFrequencyMinimum();
            }
            if (null == rulesType.getFrequencyRestart()
                    || ConstantADM.COMMON_STRING_NULL
                            .equals(rulesType.getFrequencyRestart())) {
                frequencyRestart = ConstantADM.STRING_EMPTY;
            } else {
                frequencyRestart = rulesType.getFrequencyRestart();
            }

            // Se setea date y time desde fecha completa
            SimpleDateFormat formatter = new SimpleDateFormat(
                    ConstantADM.STRING_DATE_FORMAT);

            saveDateFrom = formatter.parse(stringFromDate);
            saveDateTo = formatter.parse(stringToDate);

            formatter = new SimpleDateFormat(ConstantADM.STRING_FORMAT_DATE);

            fromDate = formatter.format(saveDateFrom);
            toDate = formatter.format(saveDateTo);

            formatter = new SimpleDateFormat(ConstantADM.STRING_FORMAT_TIME);

            fromHour = formatter.format(saveDateFrom);
            toHour = formatter.format(saveDateTo);

            // Se inicializa los valores
            dateFrom = saveDateFrom;
            dateTo = saveDateTo;
            hourFrom = saveDateFrom;
            hourTo = saveDateTo;

            usersSize = getPromotionServiceBeanLocal
                    .getPromocionUsuarioSize(idPromotion);

        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_GET_PROMOTION, e);
            messages = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.ERROR_GET_PROMOTION,
                    ConstantADM.ERROR_GET_PROMOTION_DESC);
            FacesContext.getCurrentInstance().addMessage(null, messages);
        }
    }

    public void savePromotion() {
        FacesMessage messages = null;
        try {

            this.logger.debug(ConstantADM.MESSAGE_TYPE_PROMOTION + this.code);

            // Se valida que tipo de promocion sea bono, en este caso el minimo
            // y maximo deben quedar en cero.
            if (ConstantADM.STRING_TYPE_PROMOTION_BONO.equals(this.code)) {

                minValueTxt = null;
                maxValueTxt = null;

            } else {

                minValueTxt = String.valueOf(minValue);
                maxValueTxt = String.valueOf(maxValue);

            }

            valueTxt = String.valueOf(value);
            availableValue = availableValueAfterModify;
            String availableValueTxt = String.valueOf(availableValue);
            ocurrenceTxt = String.valueOf(ocurrence);
            maximumBudgetTxt = String.valueOf(maximumBudget);

            if (null != frequencyRestart
                    && !ConstantADM.STRING_EMPTY.equals(frequencyRestart)) {
                if (UtilADM.daysDiference(dateFrom, dateTo, frequencyRestart)) {
                    messages = UtilADM.updateRules(
                            updatePromotionServiceBeanLocal, id, description,
                            stringFromDate, stringToDate, minValueTxt,
                            maxValueTxt, valueTxt, valueType, accountingAccount,
                            availableValueTxt, ocurrenceTxt, status,
                            notificationType, subject, message, countryId,
                            maximumBudgetTxt, frequencyMinimum,
                            frequencyRestart);
                } else {
                    messages = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            ConstantADM.ERROR_UPDATE_PROMOTION,
                            ConstantADM.ERROR_RANGE_FREQUENCY_RESTART_DESC);
                }

            } else {
                messages = UtilADM.updateRules(updatePromotionServiceBeanLocal,
                        id, description, stringFromDate, stringToDate,
                        minValueTxt, maxValueTxt, valueTxt, valueType,
                        accountingAccount, availableValueTxt, ocurrenceTxt,
                        status, notificationType, subject, message, countryId,
                        maximumBudgetTxt, frequencyMinimum, frequencyRestart);
            }

        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_UPDATE_PROMOTION, e);
            messages = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    ConstantADM.ERROR_UPDATE_PROMOTION,
                    ConstantADM.ERROR_UPDATE_PROMOTION_DESC);
        }
        // se inicializa valores con los nuevos guardados
        availableValueOld = availableValue;
        maximumBudgetOld = maximumBudget;
        resetCampaign = Boolean.FALSE;
        FacesContext.getCurrentInstance().addMessage(null, messages);
    }

    /**
     * Metodo para controlar las pestanas de promociones
     * 
     * @param event
     * @return
     */
    public String onFlowProcess(FlowEvent event) {
        if (ConstantADM.STRING_CONFIRMATION.equals(event.getNewStep())) {
            // inicializar valores
            availableValue = ConstantADM.COMMON_BIG_DECIMAL_ZERO;
            amountToDeliver = ConstantADM.COMMON_BIG_DECIMAL_ZERO;
            availableValueAfterModify = ConstantADM.COMMON_BIG_DECIMAL_ZERO;
            maximumBudgetOld = ConstantADM.COMMON_BIG_DECIMAL_ZERO;
            showPromotion();
        }
        this.createView = ConstantADM.COMMON_ZERO;
        return event.getNewStep();
    }

    /**
     * Metodo para controlar las pestanas en la actualizacion de promocion
     * 
     * @param event
     * @return
     */
    public String onFlowProcessUpdate(FlowEvent event) {
        if (ConstantADM.STRING_CONFIRMATION_UPDATE.equals(event.getNewStep())) {

            SimpleDateFormat formatter = new SimpleDateFormat(
                    ConstantADM.STRING_DATE_FORMAT);

            saveDateFrom = dateTime(dateFrom, hourFrom);
            saveDateTo = dateTime(dateTo, hourTo);

            stringFromDate = formatter.format(saveDateFrom);
            stringToDate = formatter.format(saveDateTo);

            formatter = new SimpleDateFormat(ConstantADM.STRING_FORMAT_DATE);

            fromDate = formatter.format(dateFrom);
            toDate = formatter.format(dateTo);

            formatter = new SimpleDateFormat(ConstantADM.STRING_FORMAT_TIME);

            fromHour = formatter.format(hourFrom);
            toHour = formatter.format(hourTo);

            if (null != status && ConstantADM.COMMON_NUM_ESTADO_PROMO_ACTIVO
                    .equals(status)) {
                statusTxt = ConstantADM.COMMON_STRING_ESTADO_PROMO_ACTIVO;
            } else {
                statusTxt = ConstantADM.COMMON_STRING_ESTADO_PROMO_INACTIVO;
            }
            // si se reinicia campana
            // el valor disponible es el presupuesto maximo
            if (resetCampaign) {
                availableValueAfterModify = maximumBudget;
                amountToDeliver = availableValueAfterModify.divide(value,
                        ConstantADM.COMMON_TWO, RoundingMode.HALF_UP);
            } else {
                // sino el disponible es cambia segun el disponible maximo
                availableValue = maximumBudget
                        .subtract(maximumBudgetOld.subtract(availableValue));
                availableValueAfterModify = availableValue;
                amountToDeliver = availableValue.divide(value,
                        ConstantADM.COMMON_TWO, RoundingMode.HALF_UP);
            }

        } else if (ConstantADM.STRING_GET_PROMOTION
                .equals(event.getNewStep())) {
            // se reinicia el presupuesto maximo valor disponible
            availableValue = availableValueOld;
        }

        return event.getNewStep();
    }

    /**
     * Metodo para retornar la lista de roles
     * 
     * @param countryId
     * @return lista de roles
     */
    private List<AwRol> getListRol(String countryId) {
        List<AwRol> roles = new ArrayList<>();
        try {
            roles = UtilADM.getListRolSplitProfileName(
                    userInfoServiceBean.getListRol(countryId));
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_GET_LIST_ROL, e);
        }
        return roles;
    }

    /**
     * <p>
     * Utilidad que reinicia el estado de los campos de la ventana de
     * edici&oacute;n de datos de l apromocion.
     * </p>
     */
    public void resetEditUserDialog() {
        RequestContext.getCurrentInstance()
                .reset(ConstantADM.UPDATE_PRMOTION_DIALOG);
    }

    /**
     * Metodo para devolver a la vista la descripcion de las promociones
     * 
     * @return the selectDescription
     */
    public List<SelectItem> getSelect() {
        this.selectDescription = new ArrayList<>();
        this.descriptionType = getRulesDescriptionServiceBeanLocal
                .getRulesDescription(countryId);
        int position = ConstantADM.COMMON_INT_ZERO;
        for (DescriptionType descriptions : descriptionType) {

            SelectItem selectItem = new SelectItem(position,
                    descriptions.getDescriptionRule());
            this.selectDescription.add(selectItem);
            position++;
        }
        this.createView = ConstantADM.COMMON_ONE;
        return this.selectDescription;
    }

    /**
     * Metodo que construye el date con fecha y hora especifico
     * 
     * @param date
     * @param time
     */
    public Date dateTime(Date date, Date time) {

        Calendar aDate = Calendar.getInstance();
        aDate.setTime(date);

        Calendar aTime = Calendar.getInstance();
        aTime.setTime(time);

        Calendar aDateTime = Calendar.getInstance();
        aDateTime.set(Calendar.DAY_OF_MONTH, aDate.get(Calendar.DAY_OF_MONTH));
        aDateTime.set(Calendar.MONTH, aDate.get(Calendar.MONTH));
        aDateTime.set(Calendar.YEAR, aDate.get(Calendar.YEAR));
        aDateTime.set(Calendar.HOUR_OF_DAY, aTime.get(Calendar.HOUR_OF_DAY));
        aDateTime.set(Calendar.MINUTE, aTime.get(Calendar.MINUTE));
        aDateTime.set(Calendar.SECOND, aTime.get(Calendar.SECOND));

        return aDateTime.getTime();
    }

    public List<PromocionComercio> getListComerce() {
        try {
            this.listComerce = getPromotionServiceBeanLocal
                    .getPromocionComercio(idPromotion);
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_GET_LIST_COMERCE, e);
        }
        return listComerce;
    }

    public List<PromocionUsuario> getListUsers() {
        try {
            this.listUsers = getPromotionServiceBeanLocal
                    .getPromocionUsuarioLimit(idPromotion, this.countryId);
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_GET_LIST_USERS, e);
        }
        return listUsers;
    }

    /**
     * Metodo para guardar temporalmente el archivo SCV
     * 
     * @param file
     */
    public void saveFileCSV(UploadedFile file) {

        BufferedReader br = null;
        String line = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        FileOutputStream out = null;
        FileReader reader = null;
        String document;
        String documentType;
        String phone;
        List<PromocionUsuario> promocionUsuarioList = new ArrayList<>();
        int rowsCont = 0;
        if (null == this.processResults) {
            this.processResults = new ArrayList<>();
        } else {
            this.processResults.clear();
        }

        try {
            bis = new BufferedInputStream(file.getInputstream());
            out = new FileOutputStream(ConstantADM.SYSTEM_TEMPORARY_ROUTE);
            bos = new BufferedOutputStream(out);
            int contador = 0;
            while ((contador = bis.read()) != ConstantADM.COMMON_NEGATIVE_ONE) {
                bos.write(contador);
            }
            bos.flush();
            reader = new FileReader(ConstantADM.SYSTEM_TEMPORARY_ROUTE);
            br = new BufferedReader(reader);
            String messageOut;
            int contColumns = ConstantADM.COMMON_ZERO;
            PromocionOperacion promocionOperacion = getPromotionServiceBeanLocal
                    .getPromocionOperacion(idPromotion);
            StringBuilder errorDescription = new StringBuilder();
            while ((line = br.readLine()) != null) {
                rowsCont++;
                String[] columnData = line.split(ConstantADM.COMMA);
                document = ConstantADM.STRING_EMPTY;
                documentType = ConstantADM.STRING_EMPTY;
                phone = ConstantADM.STRING_EMPTY;
                if (ConstantADM.COMMON_INT_THREE < columnData.length) {
                    messageOut = ConstantADM.ERROR_INVALID_COLUMNS_UPLOAD_FILE;
                    messageOut = messageOut.replace(
                            ConstantADM.COMMON_STRING_REPLACE_KEY_FIRST,
                            Integer.toString(columnData.length));
                    messageOut = messageOut.replace(
                            ConstantADM.COMMON_STRING_REPLACE_KEY_SECOND,
                            ConstantADM.COMMON_STRING_USER_PROMOTION_REQUIRED_COLUMNS);
                    throw new IllegalArgumentException(messageOut);
                }
                if (ConstantADM.COMMON_ZERO == contColumns) {
                    contColumns++;
                    continue;
                }

                List<String> errors = validateCustomer(columnData);
                if (null == errors || errors.isEmpty()) {
                    if (columnData.length == ConstantADM.COMMON_INT_THREE) {
                        phone = columnData[ConstantADM.COMMON_TWO];
                    }
                    if (!ConstantADM.STRING_EMPTY
                            .equals(columnData[ConstantADM.COMMON_ZERO])) {
                        documentType = columnData[ConstantADM.COMMON_ZERO];
                    }
                    if (!ConstantADM.STRING_EMPTY
                            .equals(columnData[ConstantADM.COMMON_ONE])) {
                        document = columnData[ConstantADM.COMMON_ONE];
                    }
                    PromocionUsuario promocionUsuario = UtilADM
                            .getPromocionUsuario(document, documentType, phone,
                                    promocionOperacion);
                    promocionUsuarioList.add(promocionUsuario);
                } else {

                    if (ConstantADM.COMMON_INT_ZERO < errorDescription
                            .length()) {
                        errorDescription.delete(ConstantADM.COMMON_INT_ZERO,
                                errorDescription.length());
                    }

                    errorDescription.append(
                            ConstantADM.ERROR_MESSAGE_VALIDATE_PROMOTION_CUSTOMER_INFO);
                    for (String error : errors) {
                        errorDescription.append(ConstantADM.STRING_SPACE);
                        errorDescription.append(error);
                    }

                    processResults
                            .add(new ProcessResult(String.valueOf(rowsCont),
                                    errorDescription.toString()));
                }
            }
            if (null != promocionUsuarioList
                    && !promocionUsuarioList.isEmpty()) {
                boolean response = getPromotionServiceBeanLocal
                        .persistPromotions(promocionUsuarioList, idPromotion);
                if (!response) {
                    messageOut = ConstantADM.ERROR_LOAD_PROMTIONS_DOCUMENT;
                    throw new IllegalArgumentException(messageOut);
                }
            }
        } catch (IOException e) {
            logger.error(ConstantADM.ERROR_READ_UPLOAD_FILE, e);
            throw new IllegalArgumentException(
                    ConstantADM.ERROR_READ_UPLOAD_FILE);
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_LOAD_PROMTIONS_DOCUMENT, e);
            throw new IllegalArgumentException(
                    ConstantADM.ERROR_LOAD_PROMTIONS_DOCUMENT);
        } finally {
            closeReosurces(reader, br, bos, out, bis);
        }
    }

    /**
     * Metodo para guardar temporalmente el archivo CSV de comercios
     * 
     * @param file
     */
    public void saveFileCSVComerce(UploadedFile file) {

        BufferedReader br = null;
        String line = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        FileOutputStream out = null;
        FileReader reader = null;
        String comerce;
        String terminal;
        String comerceStatus;
        HashMap<Integer, PromocionComercio> promocionComercioMap = new HashMap<>();

        String messageOut;
        int rowsCont = 0;
        if (null == this.processResults) {
            this.processResults = new ArrayList<>();
        } else {
            this.processResults.clear();
        }
        try {
            bis = new BufferedInputStream(file.getInputstream());
            out = new FileOutputStream(ConstantADM.SYSTEM_TEMPORARY_ROUTE);
            bos = new BufferedOutputStream(out);
            int contador = 0;
            StringBuilder errorDescription = new StringBuilder();

            while ((contador = bis.read()) != ConstantADM.COMMON_NEGATIVE_ONE) {
                bos.write(contador);
            }
            bos.flush();
            reader = new FileReader(ConstantADM.SYSTEM_TEMPORARY_ROUTE);
            br = new BufferedReader(reader);

            PromocionOperacion promocionOperacion = getPromotionServiceBeanLocal
                    .getPromocionOperacion(idPromotion);

            int contColumns = ConstantADM.COMMON_ZERO;
            while ((line = br.readLine()) != null) {
                rowsCont++;
                String[] columnData = line.split(ConstantADM.COMMA);
                comerce = ConstantADM.STRING_EMPTY;
                terminal = ConstantADM.STRING_EMPTY;
                comerceStatus = ConstantADM.STRING_EMPTY;

                if (ConstantADM.COMMON_INT_THREE < columnData.length) {
                    messageOut = ConstantADM.ERROR_INVALID_COLUMNS_UPLOAD_FILE;
                    messageOut = messageOut.replace(
                            ConstantADM.COMMON_STRING_REPLACE_KEY_FIRST,
                            Integer.toString(columnData.length));
                    messageOut = messageOut.replace(
                            ConstantADM.COMMON_STRING_REPLACE_KEY_SECOND,
                            ConstantADM.COMMON_STRING_USER_PROMOTION_REQUIRED_COLUMNS);
                    throw new IllegalArgumentException(messageOut);
                }

                if (ConstantADM.COMMON_ZERO == contColumns) {
                    contColumns++;
                    continue;
                }

                List<String> errors = validateCommerce(columnData);
                if (null == errors || errors.isEmpty()) {

                    if (!ConstantADM.STRING_EMPTY
                            .equals(columnData[ConstantADM.COMMON_ZERO])) {
                        comerce = columnData[ConstantADM.COMMON_ZERO];
                    }

                    terminal = ConstantADM.DEFAULT_TERMINAL;

                    if (columnData.length >= ConstantADM.COMMON_TWO
                            && !ConstantADM.STRING_EMPTY.equals(
                                    columnData[ConstantADM.COMMON_ONE])) {
                        terminal = columnData[ConstantADM.COMMON_ONE];
                    }
                    comerceStatus = ConstantADM.DEFAULT_STATUS;

                    if (columnData.length >= ConstantADM.COMMON_INT_THREE
                            && !ConstantADM.STRING_EMPTY.equals(
                                    columnData[ConstantADM.COMMON_TWO])) {
                        comerceStatus = columnData[ConstantADM.COMMON_TWO];
                    }

                    PromocionComercio promocionComercio = UtilADM
                            .getPromocionComercio(comerce, terminal,
                                    comerceStatus, idPromotion,
                                    promocionOperacion);

                    promocionComercioMap.put(
                            promocionComercio.getId().hashCode(),
                            promocionComercio);
                } else {

                    if (ConstantADM.COMMON_INT_ZERO < errorDescription
                            .length()) {
                        errorDescription.delete(ConstantADM.COMMON_INT_ZERO,
                                errorDescription.length());
                    }

                    errorDescription.append(
                            ConstantADM.ERROR_MESSAGE_VALIDATE_PROMOTION_CUSTOMER_INFO);
                    for (String error : errors) {
                        errorDescription.append(ConstantADM.STRING_SPACE);
                        errorDescription.append(error);
                    }

                    processResults
                            .add(new ProcessResult(String.valueOf(rowsCont),
                                    errorDescription.toString()));
                }

            }

            if (null != promocionComercioMap
                    && !promocionComercioMap.isEmpty()) {

                List<PromocionComercio> promocionComercioList = new ArrayList<PromocionComercio>(
                        promocionComercioMap.values());

                boolean response = getPromotionServiceBeanLocal
                        .persistPromotionComercio(promocionComercioList,
                                idPromotion);
                if (!response) {
                    messageOut = ConstantADM.ERROR_LOAD_PROMTIONS_DOCUMENT;
                    throw new IllegalArgumentException(messageOut);
                }
            }
        } catch (IOException e) {
            logger.error(ConstantADM.ERROR_READ_UPLOAD_FILE, e);
            throw new IllegalArgumentException(
                    ConstantADM.ERROR_READ_UPLOAD_FILE);
        } catch (Exception e) {
            logger.error(ConstantADM.ERROR_LOAD_PROMTIONS_DOCUMENT, e);
            throw new IllegalArgumentException(
                    ConstantADM.ERROR_LOAD_PROMTIONS_DOCUMENT);
        } finally {
            closeReosurces(reader, br, bos, out, bis);
        }
    }

    /**
     * @param columnData
     */
    private List<String> validateCustomer(String[] columnData) {
        String messageOut;
        List<String> errors = new ArrayList<>();
        if (ConstantADM.COMMON_INT_THREE < columnData.length) {
            messageOut = ConstantADM.ERROR_INVALID_COLUMNS_UPLOAD_FILE;
            messageOut = messageOut.replace(
                    ConstantADM.COMMON_STRING_REPLACE_KEY_FIRST,
                    Integer.toString(columnData.length));
            messageOut = messageOut.replace(
                    ConstantADM.COMMON_STRING_REPLACE_KEY_SECOND,
                    ConstantADM.COMMON_STRING_USER_PROMOTION_REQUIRED_COLUMNS);
            errors.add(messageOut);
            // Se retorna porque ya las demas validaciones no aplican
            return errors;
        }
        if (columnData.length < ConstantADM.COMMON_INT_THREE
                && (columnData.length < ConstantADM.COMMON_TWO
                        || ConstantADM.STRING_EMPTY
                                .equals(columnData[ConstantADM.COMMON_ONE])
                        || ConstantADM.STRING_EMPTY
                                .equals(columnData[ConstantADM.COMMON_ZERO]))) {

            messageOut = ConstantADM.ERROR_INVALID_REGISTRY;
            errors.add(messageOut);
            // Se retorna porque ya las demas validaciones no aplican
            return errors;
        }

        if (columnData.length == ConstantADM.COMMON_INT_THREE
                && !Pattern.matches(ConstantADM.REGULAR_EXPRESION_NUMBERS,
                        columnData[ConstantADM.COMMON_TWO])) {

            messageOut = ConstantADM.ERROR_INVALID_PHONE;
            errors.add(messageOut);
        }

        if (!Pattern.matches(ConstantADM.REGULAR_EXPRESION_ALPHABETICAL,
                columnData[ConstantADM.COMMON_ZERO])) {

            messageOut = ConstantADM.ERROR_INVALID_DOCUMENT_TYPE;
            errors.add(messageOut);
        }
        if (ConstantADM.COMMON_STRING_REGION_COL.equals(this.countryId)) {
            if (!Pattern.matches(
                    ConstantADM.REGULAR_EXPRESION_COLOMBIA_DOCUMENT,
                    columnData[ConstantADM.COMMON_ONE])) {

                messageOut = ConstantADM.ERROR_INVALID_CO_DOCUMENT;
                errors.add(messageOut);
            }
        } else {
            if (!Pattern.matches(ConstantADM.REGULAR_EXPRESION_PANAMA_DOCUMENT,
                    columnData[ConstantADM.COMMON_ONE])) {

                messageOut = ConstantADM.ERROR_INVALID_PA_DOCUMENT;
                errors.add(messageOut);
            }
        }
        return errors;
    }

    /**
     * Metodo para validar el comercio que se sube en el archivo plano.
     * 
     * @param columnData
     */
    private List<String> validateCommerce(String[] columnData) {

        String messageOut;
        List<String> errors = new ArrayList<>();

        if (ConstantADM.COMMON_INT_THREE < columnData.length) {

            messageOut = ConstantADM.ERROR_INVALID_COLUMNS_UPLOAD_FILE;
            messageOut = messageOut.replace(
                    ConstantADM.COMMON_STRING_REPLACE_KEY_FIRST,
                    Integer.toString(columnData.length));
            messageOut = messageOut.replace(
                    ConstantADM.COMMON_STRING_REPLACE_KEY_SECOND,
                    ConstantADM.COMMON_STRING_CCIO_PROMOTION_REQUIRED_COLUMNS);
            errors.add(messageOut);
            // Se retorna porque ya las demas validaciones no aplican
            return errors;
        }

        if (null == columnData[ConstantADM.COMMON_INT_ZERO]
                || columnData[ConstantADM.COMMON_INT_ZERO].isEmpty()) {

            messageOut = ConstantADM.COMMON_STRING_COMERCE_REQUIRED_FIELD;
            errors.add(messageOut);

        }

        if (columnData.length > ConstantADM.COMMON_TWO
                && null != columnData[ConstantADM.COMMON_TWO]
                && !columnData[ConstantADM.COMMON_TWO].isEmpty()
                && !columnData[ConstantADM.COMMON_TWO]
                        .equals(ConstantADM.COMMON_NUM_ESTADO_PROMO_ACTIVO)
                && !columnData[ConstantADM.COMMON_TWO]
                        .equals(ConstantADM.COMMON_STRING_PROMO_INACTIVA)) {
            messageOut = ConstantADM.COMMON_STRING_ERROR_SATUS_FIELD;
            errors.add(messageOut);

        }
        return errors;
    }

    /**
     * Metodo para cerrar recursos necesarios para subir archivos
     * 
     * @param reader
     * @param br
     * @param bos
     * @param out
     * @param bis
     */
    public void closeReosurces(FileReader reader, BufferedReader br,
            BufferedOutputStream bos, FileOutputStream out,
            BufferedInputStream bis) {
        try {
            if (null != reader) {
                reader.close();
            }
        } catch (IOException e) {
            logger.error(ConstantADM.ERROR_CLOSING_RESOURCES_TO_UPLOAD_FILE, e);
        }
        try {
            if (null != br) {
                br.close();
            }
        } catch (IOException e) {
            logger.error(ConstantADM.ERROR_CLOSING_RESOURCES_TO_UPLOAD_FILE, e);
        }
        try {
            if (null != bos) {
                bos.close();
            }
        } catch (IOException e) {
            logger.error(ConstantADM.ERROR_CLOSING_RESOURCES_TO_UPLOAD_FILE, e);
        }
        try {
            if (null != out) {
                out.close();
            }
        } catch (IOException e) {
            logger.error(ConstantADM.ERROR_CLOSING_RESOURCES_TO_UPLOAD_FILE, e);
        }
        try {
            if (null != bis) {
                bis.close();
            }
        } catch (IOException e) {
            logger.error(ConstantADM.ERROR_CLOSING_RESOURCES_TO_UPLOAD_FILE, e);
        }
    }

    /**
     * Metodo para cargar el documento CSV
     * 
     * @param event
     */
    public void loadData(FileUploadEvent event) {
        FacesMessage messageOut;
        UploadedFile file = event.getFile();

        try {
            this.saveFileCSV(file);
            if (ConstantADM.COMMON_INT_ZERO >= processResultsSize) {
                messageOut = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.WORD_SUCCESS,
                        ConstantADM.FILE_UPLOAD_SUCCESS);
                usersSize = getPromotionServiceBeanLocal
                        .getPromocionUsuarioSize(idPromotion);
            } else {
                messageOut = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.WORD_ERROR,
                        ConstantADM.FILE_UPLOAD_ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            messageOut = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.WORD_ERROR, e.getMessage());
            logger.error(ConstantADM.FILE_UPLOAD_ERROR_MESSAGE, e);
        }
        FacesContext.getCurrentInstance().addMessage(null, messageOut);
    }

    /**
     * Metodo para cargar el documento CSV de comercios
     * 
     * @param event
     */
    public void loadDataComerce(FileUploadEvent event) {
        FacesMessage messageOut;
        UploadedFile file = event.getFile();

        try {
            this.saveFileCSVComerce(file);
            if (ConstantADM.COMMON_INT_ZERO >= processResultsSize) {
                messageOut = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.WORD_SUCCESS,
                        ConstantADM.FILE_UPLOAD_SUCCESS);
            } else {
                messageOut = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantADM.WORD_ERROR,
                        ConstantADM.FILE_UPLOAD_ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            messageOut = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.WORD_ERROR, e.getMessage());
            logger.error(ConstantADM.FILE_UPLOAD_ERROR_MESSAGE, e);
        }
        FacesContext.getCurrentInstance().addMessage(null, messageOut);
    }

    public void deletePromotions() {
        FacesMessage messageOut;

        try {
            getPromotionServiceBeanLocal.deletePromotions(idPromotion);
            usersSize = getPromotionServiceBeanLocal
                    .getPromocionUsuarioSize(idPromotion);
            messageOut = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    ConstantADM.WORD_SUCCESS,
                    ConstantADM.DELETE_PROMOTIONS_SUCCESS);
        } catch (IllegalArgumentException e) {
            messageOut = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.WORD_ERROR, e.getMessage());
            logger.error(ConstantADM.DELETE_PROMOTIONS_ERROR, e);
        }
        FacesContext.getCurrentInstance().addMessage(null, messageOut);
    }

    public void deleteComerce() {
        FacesMessage messageOut;

        try {
            getPromotionServiceBeanLocal.deletePromotionComercio(idPromotion);
            messageOut = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    ConstantADM.WORD_SUCCESS,
                    ConstantADM.DELETE_COMERCE_SUCCESS);
        } catch (IllegalArgumentException e) {
            messageOut = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.WORD_ERROR, e.getMessage());
            logger.error(ConstantADM.DELETE_PROMOTIONS_ERROR, e);
        }
        FacesContext.getCurrentInstance().addMessage(null, messageOut);
    }

    /**
     * Metodo que crea una regla de promocion para resarcimiento
     */
    public void createPromotionRule() {
        FacesMessage messageOut;
        try {
            PromocionOperacion promocionOperacion = getPromotionServiceBeanLocal
                    .getPromocionOperacionByService(
                            ConstantADM.COMMON_STRING_RESARCIMIENTO,
                            this.countryId);
            if(null != this.restitutionName){
                boolean response = getPromotionServiceBeanLocal
                        .persistPromocionRegla(this.restitutionName, promocionOperacion);
                if (response) {
                    messageOut = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            ConstantADM.WORD_SUCCESS,
                            ConstantADM.PERSIST_RULE_SUCCESS);
                } else {
                    messageOut = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ConstantADM.WORD_ERROR, ConstantADM.PERSIST_RULE_ERROR);
                }
            } else{
                messageOut = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        ConstantADM.WORD_ERROR, ConstantADM.PERSIST_RULE_ERROR);
            }      
        } catch (Exception e) {
            messageOut = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ConstantADM.WORD_ERROR, e.getMessage());
            logger.error(ConstantADM.PERSIST_RULE_ERROR, e);
        }
        FacesContext.getCurrentInstance().addMessage(null, messageOut);
    }

    /**
     * @return the nameService
     */
    public String getNameService() {
        return nameService;
    }

    /**
     * @param nameService
     *            the nameService to set
     */
    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public List<RulesType> getRules() {
        return listRule;
    }

    public void setRules(List<RulesType> listRule) {
        this.listRule = listRule;
    }

    /**
     * @return the actionsRol
     */
    public List<ActionRol> getActionsRol() {
        return actionsRol;
    }

    /**
     * @param actionsRol
     *            the actionsRol to set
     */
    public void setActionsRol(List<ActionRol> actionsRol) {
        this.actionsRol = actionsRol;
    }

    /**
     * @return the listAllRol
     */
    public List<AwRol> getListAllRol() {
        return listAllRol;
    }

    /**
     * @param listAllRol
     *            the listAllRol to set
     */
    public void setListAllRol(List<AwRol> listAllRol) {
        this.listAllRol = listAllRol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
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

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getAccountingAccount() {
        return accountingAccount;
    }

    public void setAccountingAccount(String accountingAccount) {
        this.accountingAccount = accountingAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAvailableValue() {
        return availableValue;
    }

    public void setAvailableValue(BigDecimal availableValue) {
        this.availableValue = availableValue;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public Double getOcurrence() {
        return ocurrence;
    }

    public void setOcurrence(Double ocurrence) {
        this.ocurrence = ocurrence;
    }

    public String getMinValueTxt() {
        return minValueTxt;
    }

    public void setMinValueTxt(String minValueTxt) {
        this.minValueTxt = minValueTxt;
    }

    public String getMaxValueTxt() {
        return maxValueTxt;
    }

    public void setMaxValueTxt(String maxValueTxt) {
        this.maxValueTxt = maxValueTxt;
    }

    public String getValueTxt() {
        return valueTxt;
    }

    public void setValueTxt(String valueTxt) {
        this.valueTxt = valueTxt;
    }

    public String getOcurrenceTxt() {
        return ocurrenceTxt;
    }

    public void setOcurrenceTxt(String ocurrenceTxt) {
        this.ocurrenceTxt = ocurrenceTxt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getSelectDescription() {
        return selectDescription;
    }

    public void setSelectDescription(List<SelectItem> selectDescription) {
        this.selectDescription = selectDescription;
    }

    public RulesType getRulesType() {
        return rulesType;
    }

    public void setRulesType(RulesType rulesType) {
        this.rulesType = rulesType;
    }

    /**
     * @return the maximumBudget
     */
    public BigDecimal getMaximumBudget() {
        return maximumBudget;
    }

    /**
     * @param maximumBudget
     *            the maximumBudget to set
     */
    public void setMaximumBudget(BigDecimal maximumBudget) {
        this.maximumBudget = maximumBudget;
    }

    /**
     * @return the fromHour
     */
    public String getFromHour() {
        return fromHour;
    }

    /**
     * @param fromHour
     *            the fromHour to set
     */
    public void setFromHour(String fromHour) {
        this.fromHour = fromHour;
    }

    /**
     * @return the toHour
     */
    public String getToHour() {
        return toHour;
    }

    /**
     * @param toHour
     *            the toHour to set
     */
    public void setToHour(String toHour) {
        this.toHour = toHour;
    }

    /**
     * @return the hourFrom
     */
    public Date getHourFrom() {
        return hourFrom;
    }

    /**
     * @param hourFrom
     *            the hourFrom to set
     */
    public void setHourFrom(Date hourFrom) {
        this.hourFrom = hourFrom;
    }

    /**
     * @return the hourTo
     */
    public Date getHourTo() {
        return hourTo;
    }

    /**
     * @param hourTo
     *            the hourTo to set
     */
    public void setHourTo(Date hourTo) {
        this.hourTo = hourTo;
    }

    /**
     * @return the resetCampaign
     */
    public Boolean getResetCampaign() {
        return resetCampaign;
    }

    /**
     * @param resetCampaign
     *            the resetCampaign to set
     */
    public void setResetCampaign(Boolean resetCampaign) {
        this.resetCampaign = resetCampaign;
    }

    /**
     * @return the amountToDeliver
     */
    public BigDecimal getAmountToDeliver() {
        return amountToDeliver;
    }

    /**
     * @param amountToDeliver
     *            the amountToDeliver to set
     */
    public void setAmountToDeliver(BigDecimal amountToDeliver) {
        this.amountToDeliver = amountToDeliver;
    }

    /**
     * @return the stringFromDate
     */
    public String getStringFromDate() {
        return stringFromDate;
    }

    /**
     * @param stringFromDate
     *            the stringFromDate to set
     */
    public void setStringFromDate(String stringFromDate) {
        this.stringFromDate = stringFromDate;
    }

    /**
     * @return the stringToDate
     */
    public String getStringToDate() {
        return stringToDate;
    }

    /**
     * @param stringToDate
     *            the stringToDate to set
     */
    public void setStringToDate(String stringToDate) {
        this.stringToDate = stringToDate;
    }

    /**
     * @return the availableValueAfterModify
     */
    public BigDecimal getAvailableValueAfterModify() {
        return availableValueAfterModify;
    }

    /**
     * @param availableValueAfterModify
     *            the availableValueAfterModify to set
     */
    public void setAvailableValueAfterModify(
            BigDecimal availableValueAfterModify) {
        this.availableValueAfterModify = availableValueAfterModify;
    }

    /**
     * @return the maximumBudgetTxt
     */
    public String getMaximumBudgetTxt() {
        return maximumBudgetTxt;
    }

    /**
     * @param maximumBudgetTxt
     *            the maximumBudgetTxt to set
     */
    public void setMaximumBudgetTxt(String maximumBudgetTxt) {
        this.maximumBudgetTxt = maximumBudgetTxt;
    }

    /**
     * @return the statusTxt
     */
    public String getStatusTxt() {
        return statusTxt;
    }

    /**
     * @param statusTxt
     *            the statusTxt to set
     */
    public void setStatusTxt(String statusTxt) {
        this.statusTxt = statusTxt;
    }

    /**
     * @return the availableValueOld
     */
    public BigDecimal getAvailableValueOld() {
        return availableValueOld;
    }

    /**
     * @param availableValueOld
     *            the availableValueOld to set
     */
    public void setAvailableValueOld(BigDecimal availableValueOld) {
        this.availableValueOld = availableValueOld;
    }

    public String getFrequencyMinimum() {
        return frequencyMinimum;
    }

    public void setFrequencyMinimum(String frequencyMinimum) {
        this.frequencyMinimum = frequencyMinimum;
    }

    public String getFrequencyRestart() {
        return frequencyRestart;
    }

    public void setFrequencyRestart(String frequencyRestart) {
        this.frequencyRestart = frequencyRestart;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsersSize() {
        return usersSize;
    }

    public void setUsersSize(String usersSize) {
        this.usersSize = usersSize;
    }

    public List<ProcessResult> getProcessResults() {
        return processResults;
    }

    public void setProcessResults(List<ProcessResult> processResults) {
        this.processResults = processResults;
    }

    public void setProcessResultsSize(Integer processResultsSize) {
        this.processResultsSize = processResultsSize;
    }

    public Integer getProcessResultsSize() {
        processResultsSize = null != processResults ? processResults.size()
                : ConstantADM.COMMON_NEGATIVE_ONE;
        return processResultsSize;
    }

    public String getRestitutionName() {
        return restitutionName;
    }

    public void setRestitutionName(String restitutionName) {
        this.restitutionName = restitutionName;
    }

    public int getCreateView() {
        return createView;
    }

    public void setCreateView(int createView) {
        this.createView = createView;
    }
}
