package co.bdigital.admin.ejb.controller;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import com.nequi.cmm.report.service.ServiceReportGenerator;
import com.nequi.cmm.report.util.ConstantReport;

import co.bdigital.admin.ejb.controller.view.GetBankCertificateServiceBeanLocal;
import co.bdigital.admin.messaging.reports.CustomerDetailsReport;
import co.bdigital.admin.messaging.reports.CustomerDetailsReportType;
import co.bdigital.admin.messaging.services.getbankcertificate.GetBankCertificateServiceRequestType;
import co.bdigital.admin.messaging.services.getcustomerdetails.GetCustomerStatusRQType;
import co.bdigital.admin.messaging.services.getcustomerdetails.GetCustomerStatusRSType;
import co.bdigital.admin.messaging.services.getcustomerdetails.GetCustomerStatusRequestType;
import co.bdigital.admin.messaging.services.getcustomerdetails.GetCustomerStatusResponseType;
import co.bdigital.admin.util.CallServiceBean;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.admin.util.ServiceControllerBean;
import co.bdigital.admin.util.WebConsoleUtil;
import co.bdigital.cmm.ejb.exception.MiddlewareException;
import co.bdigital.cmm.ejb.generic.ServiceControllerHelper;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.model.DivisionGeografica;
import co.bdigital.cmm.jpa.model.Parametro;
import co.bdigital.cmm.jpa.service.impl.ClientByDocumentJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.DivisionGeograficaJPAServiceIMPL;
import co.bdigital.cmm.jpa.service.impl.ParameterJPAServiceIMPL;
import co.bdigital.cmm.jpa.services.DivisionGeograficaJPAService;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.bdigital.cmm.messaging.general.StatusType;
import co.bdigital.shl.tracer.CustomLogger;
import co.bdigital.shl.tracer.ErrorEnum;
import net.sf.jasperreports.export.PdfExporterConfiguration;

/**
 * 
 * @author juan.arboleda
 *
 */
@Stateless
@Local(GetBankCertificateServiceBeanLocal.class)
public class GetBankCertificateServiceBean extends CallServiceBean
        implements GetBankCertificateServiceBeanLocal {

    @PersistenceContext(unitName = "JPAManager")
    private EntityManager em;
    private CustomLogger logger;
    @EJB
    private ServiceControllerBean serviceLocatorBean;
    private EntityManagerFactory entityManagerFactory;

    private EntityManager emFRM;

    @Resource(name = "getBankCertificateIsTraceable")
    private Boolean getBankCertificateIsTraceable;

    @Resource(name = "getBankCertificateIsDebugable")
    private Boolean getBankCertificateIsDebugable;

    private ServiceControllerHelper sch;

    private DivisionGeograficaJPAService divisionGeograficaJPAService;

    /**
     * Metodo para inicializar recursos del EJB.
     */
    @PostConstruct
    void init() {
        logger = new CustomLogger(GetBankCertificateServiceBean.class);
        this.entityManagerFactory = Persistence
                .createEntityManagerFactory(Constant.FRM_MANAGER);
        this.sch = ServiceControllerHelper.getInstance();

        this.divisionGeograficaJPAService = new DivisionGeograficaJPAServiceIMPL();
    }

    @PreDestroy
    void shutdown() {
        if ((null != this.entityManagerFactory)
                && (this.entityManagerFactory.isOpen())) {

            this.entityManagerFactory.close();
        }

        if ((null != this.emFRM) && (this.emFRM.isOpen())) {

            this.emFRM.close();
        }
    }

    /*
     * Metodo para generar el certificado bancario.
     * 
     * (non-Javadoc)
     * 
     * @see
     * co.bdigital.admin.ejb.controller.view.GetBankCertificateServiceBeanLocal#
     * getCertificate(co.bdigital.cmm.messaging.general.
     * ResponseMessageObjectType, java.lang.String, java.lang.String,
     * java.io.OutputStream)
     */
    @Override
    public void getCertificate(ResponseMessageObjectType responseBroker,
            String region, String phone, OutputStream outputStream) {
        Parametro paramJasper;
        String reportDate = null;
        try {
            StatusType statusTypeBroker = responseBroker.getResponseMessage()
                    .getResponseHeader().getStatus();

            /* Validar que la respuesta de BROKER */
            if (Constant.COMMON_STRING_ZERO
                    .equals(statusTypeBroker.getStatusCode())) {

                /*
                 * Se obtiene la ruta donde se encuentra el reporte JASPER
                 * BANK_CERTIFICATION
                 */
                /* Se consultan los parámetros de reporte */
                List<Parametro> parametros = ParameterJPAServiceIMPL
                        .getInstance().getRegionParameter(
                                ConstantADM.COMMON_STRING_PARAMETER_REPORTS,
                                region, em);

                paramJasper = sch.getParameterByName(parametros,
                        ConstantADM.COMMON_STRING_PARAMETER_REPORTS_JASPER_BANK_CERTIFICATION);

                GetCustomerStatusResponseType getCustomerStatusResponseType = (GetCustomerStatusResponseType) sch
                        .parsePayload(
                                responseBroker.getResponseMessage()
                                        .getResponseBody().getAny(),
                                new GetCustomerStatusResponseType());

                GetCustomerStatusRSType getCustomerStatusRSType = getCustomerStatusResponseType
                        .getGetCustomerStatusRS();

                CustomerDetailsReport customerDetailsReport = new CustomerDetailsReport();
                CustomerDetailsReportType customerDetailsReportType = new CustomerDetailsReportType();

                customerDetailsReportType.setFullName(
                        getCustomerStatusRSType.getFullName().toUpperCase());

                customerDetailsReportType
                        .setDocument(getCustomerStatusRSType.getDocumentId());

                customerDetailsReportType.setAccountNumber(phone);

                customerDetailsReportType.setAccountOpnDate(
                        getCustomerStatusRSType.getAcctOpnDate());

                customerDetailsReportType.setConventionalAccount(
                        getCustomerStatusRSType.getForacId());

                /* Se homologa tipo de documento */
                switch (getCustomerStatusRSType.getDocumentType()) {
                case ConstantADM.COMMON_STRING_CC:
                    customerDetailsReportType
                            .setDocumentType(ConstantADM.COMMON_STRING_DESC_CC);
                    break;
                case ConstantADM.COMMON_STRING_CE:
                    customerDetailsReportType
                            .setDocumentType(ConstantADM.COMMON_STRING_DESC_CE);
                    break;
                case ConstantADM.COMMON_STRING_PS:
                    customerDetailsReportType
                            .setDocumentType(ConstantADM.COMMON_STRING_DESC_PS);
                    break;
                case ConstantADM.COMMON_STRING_TI:
                    customerDetailsReportType
                            .setDocumentType(ConstantADM.COMMON_STRING_DESC_TI);
                    break;
                default:
                    break;
                }

                /* Se homologa el estado */
                switch (getCustomerStatusRSType.getAcctStatus()) {
                case ConstantADM.COMMON_STRING_ESTADO_A:
                    customerDetailsReportType.setAccountStatus(
                            ConstantADM.COMMON_STRING_ESTADO_ACTIVO);
                    break;
                case ConstantADM.COMMON_STRING_ESTADO_I:
                    customerDetailsReportType.setAccountStatus(
                            ConstantADM.COMMON_STRING_ESTADO_INACTIVO);
                    break;
                default:
                    customerDetailsReportType.setAccountStatus(
                            ConstantADM.COMMON_STRING_ESTADO_DORMIDO);
                    break;
                }

                customerDetailsReport
                        .setCustomerDetails(customerDetailsReportType);

                String json = this.sch.parseJSONToString(customerDetailsReport);

                reportDate = WebConsoleUtil.formatDateToString(new Date(),
                        ConstantADM.FORMAT_DATE_PATTERN_BANK_CERTIFICATION);
                reportDate = reportDate.substring(0, 1).toUpperCase()
                        + reportDate.substring(1);

                Map<String, Object> reportParameter = new HashMap<String, Object>();
                reportParameter.put(ConstantADM.PATTERN_STRING_REPORT_DATE,
                        reportDate);

                reportParameter.put(
                        PdfExporterConfiguration.PROPERTY_OWNER_PASSWORD,
                        phone);
                reportParameter.put(
                        PdfExporterConfiguration.PROPERTY_USER_PASSWORD, phone);
                reportParameter.put(PdfExporterConfiguration.PROPERTY_ENCRYPTED,
                        true);

                ServiceReportGenerator.buildEncryptedPdfFromJSON(
                        paramJasper.getValor(), reportParameter, json,
                        customerDetailsReportType.getDocument(),
                        customerDetailsReportType.getDocument(), outputStream);

            } else {
                logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                        responseBroker.getResponseMessage().getResponseHeader()
                                .getStatus().getStatusDesc(),
                        new MiddlewareException(responseBroker
                                .getResponseMessage().getResponseHeader()
                                .getStatus().getStatusDesc()));
            }

        } catch (Exception e) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    ConstantReport.ERROR_MESSAGE_REPORT_BUILD, e);
        }
    }

    /**
     * Metodo para llamar al servicio de broker para consultar el estado del
     * cliente en Finacle.
     * 
     * @param request
     * @param cliente
     * @param divisionGeografica
     * @param bankId
     * @param phone
     * @return {@link ResponseMessageObjectType}
     */
    public ResponseMessageObjectType callGetCustomerStatus(
            RequestMessageObjectType request, Cliente cliente,
            DivisionGeografica divisionGeografica, Parametro bankId,
            String phone) {

        GetCustomerStatusRequestType getCustomerStatusRequestType = new GetCustomerStatusRequestType();
        GetCustomerStatusRQType getCustomerStatusRQType = new GetCustomerStatusRQType();
        getCustomerStatusRQType.setBankId(bankId.getValor());
        getCustomerStatusRQType
                .setDocument(buildClientId(cliente.getNumeroId()));
        getCustomerStatusRQType.setPhoneNumber(
                WebConsoleUtil.buildString(ConstantADM.COMMON_STRING_PLUS,
                        divisionGeografica.getCodigoPostal(), phone));
        getCustomerStatusRequestType
                .setGetCustomerStatusRQ(getCustomerStatusRQType);

        return this.sch.createBrokerRequestNequiApp(request,
                getCustomerStatusRequestType, null,
                ConstantADM.COMMON_STRING_NAME_CUSTOMER,
                ConstantADM.COMMON_STRING_NAMESPACE_CUSTOMER,
                ConstantADM.COMMON_STRING_OPERATION_CUSTOMER,
                ConstantADM.REQUEST_CUSTOMER_DETAILS,
                ConstantADM.RESPONSE_CUSTOMER_DETAILS, this.em);

    }

    /**
     * Metodo para construir numero de clientId (Cuetnas cerradas)
     * 
     * @param clientId
     * @return
     */
    public String buildClientId(String clientId) {
        if (ConstantADM.STRING_EMPTY.equals(clientId)) {
            return ConstantADM.STRING_EMPTY;
        } else {
            if (clientId.indexOf(
                    ConstantADM.COMMON_SUB_GUION) < ConstantADM.COMMON_INT_ZERO) {
                return clientId;
            } else {
                return clientId.substring(ConstantADM.COMMON_INT_ZERO,
                        clientId.indexOf(ConstantADM.COMMON_SUB_GUION));
            }
        }
    }

    /*
     * Metodo para obtener el certificado bancario.
     * 
     * (non-Javadoc)
     * 
     * @see
     * co.bdigital.admin.ejb.controller.view.GetBankCertificateServiceBeanLocal#
     * getCertificateData(co.bdigital.cmm.messaging.general.
     * RequestMessageObjectType)
     */
    @Override
    public ResponseMessageObjectType getCertificateData(
            RequestMessageObjectType request) {
        Cliente client = null;
        String region = null;
        String phone = null;
        try {
            if (!this.sch.validateRequest(
                    request.getRequestMessage().getRequestBody().getAny(),
                    new GetBankCertificateServiceRequestType(),
                    ConstantADM.SERVICE_OPERATION_CREATE_USER)) {

                return this.sch.responseErrorMessage(
                        request.getRequestMessage().getRequestHeader(),
                        Constant.ERROR_CODE_PARSE_PAYLOAD,
                        Constant.ERROR_MESSAGE_PARSING_ERROR_IN,
                        Constant.COMMON_SYSTEM_MDW, this.em);

            }

            GetBankCertificateServiceRequestType payloadRequest = (GetBankCertificateServiceRequestType) this.sch
                    .parsePayload(
                            request.getRequestMessage().getRequestBody()
                                    .getAny(),
                            new GetBankCertificateServiceRequestType());

            region = request.getRequestMessage().getRequestHeader()
                    .getDestination().getServiceRegion();
            phone = payloadRequest.getGetBankCertificateRQ().getPhoneNumber();

            client = ClientByDocumentJPAServiceIMPL.getInstance()
                    .findClientByDocumentId(phone, region, em);
            if (null == client) {
                client = ClientByDocumentJPAServiceIMPL.getInstance()
                        .findClientCloseAcountByDocumentId(phone, region, em);
                if (null == client) {
                    logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                            ConstantReport.ERROR_MESSAGE_REPORT_BUILD,
                            new MiddlewareException(
                                    ConstantReport.ERROR_MESSAGE_REPORT_BUILD));
                    return this.sch.responseErrorMessage(
                            request.getRequestMessage().getRequestHeader(),
                            Constant.ERROR_CODE_INVALID_PARAMS,
                            Constant.ERROR_MESSAGE_INVALID_PARAMS,
                            Constant.COMMON_SYSTEM_MDW, this.em);
                }
            }
            // Se consulta el BANK_ID
            List<Parametro> bankIdList = ParameterJPAServiceIMPL.getInstance()
                    .getRegionParameter(
                            ConstantADM.COMMON_STRING_TYPE_PARAMETER_BANK_ID,
                            region, em);

            Parametro bankId = sch.getParameterByName(bankIdList,
                    ConstantADM.COMMON_STRING_PARAMETER_BANK_ID);
            /*
             * Se consulta la división geográfica para obtener el indicativo del
             * país
             */
            DivisionGeografica divisionGeografica = divisionGeograficaJPAService
                    .findDivisionGeografica(region, em);

            /* Consultar el estado de la cuenta en BROKER */
            return callGetCustomerStatus(request, client, divisionGeografica,
                    bankId, phone);

        } catch (Exception e) {
            logger.error(ErrorEnum.MIDDLEWARE_ERROR,
                    ConstantReport.ERROR_MESSAGE_REPORT_BUILD, e);
            return this.sch.responseErrorMessage(
                    request.getRequestMessage().getRequestHeader(),
                    Constant.COMMON_STRING_FIVE_HUNDRED,
                    Constant.ERROR_MESSAGE_MIDDLEWARE_GENERIC_ERROR,
                    Constant.COMMON_SYSTEM_MDW, this.em);
        }
    }

}
