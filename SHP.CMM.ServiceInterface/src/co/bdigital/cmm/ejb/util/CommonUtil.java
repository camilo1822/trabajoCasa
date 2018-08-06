/**
 * 
 */
package co.bdigital.cmm.ejb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import co.bdigital.cmm.ejb.generic.ServiceControllerHelper;
import co.bdigital.cmm.messaging.general.ProtectedMsgRSType;
import co.bdigital.cmm.messaging.general.ProtectedMsgServiceResponseType;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;
import co.bdigital.cmm.messaging.slacknotification.SlackNotificationRQType;
import co.bdigital.cmm.messaging.slacknotification.SlackNotificationServiceRequestType;
import co.bdigital.shl.security.manager.exception.SecurityManagerSHLException;

/**
 * @author cristian.martinez@pragma.com.co
 * 
 *         Clase utilitaria.
 *
 */
public class CommonUtil {

    /**
     * Constructor Privado
     */
    private CommonUtil() {

    }

    /**
     * Se valida si el tipo de transaccion es <code>null</code>, entonces se
     * retorna 31 que es tipo de transaccion <code>PedirPlata</code>.
     * 
     * @param transactionType
     * @return <code>String</code>
     */
    public static String validateTransactionType(String transactionType) {

        if ((null != transactionType) && (!transactionType.trim().isEmpty())) {

            return transactionType;

        } else {

            return String.valueOf(Constant.TRANSACTION_TYPE);
        }

    }

    /**
     * Metodo que genera el ID de una Transaccion.
     * 
     * @param type
     * @param terminal
     * @param clientId
     * @param date
     * @return <code>String</code>
     */
    public static String generateTransactionId(String type, String terminal,
            String clientId, String date) {

        StringBuilder transactionId = new StringBuilder(type);

        transactionId.append(Constant.GUION_MEDIO);
        transactionId.append(terminal);
        transactionId.append(Constant.GUION_MEDIO);
        transactionId.append(clientId);
        transactionId.append(Constant.GUION_MEDIO);
        transactionId.append(date);

        return transactionId.toString();
    }

    /**
     * Servicio para cifrar cualquier respuesta
     * 
     * @param request
     * @param any
     * @param type
     * @param sch
     * @param em
     * @return <code>ProtectedMsgServiceResponseType</code>
     * @throws SecurityManagerSHLException
     */
    public static ProtectedMsgServiceResponseType getProtectedMsgServiceResponseType(
            RequestMessageObjectType request, Object any, String type,
            ServiceControllerHelper sch, EntityManager em)
            throws SecurityManagerSHLException {

        ProtectedMsgServiceResponseType protectedMsgServiceResponseType;
        ProtectedMsgRSType protectedMsgRSType;
        String encryptedResponseObject;

        protectedMsgServiceResponseType = new ProtectedMsgServiceResponseType();
        protectedMsgRSType = new ProtectedMsgRSType();

        // Diffie-Hellman
        encryptedResponseObject = sch.getStringEncryptAESDiffieHellman(request,
                any, em);

        protectedMsgRSType.setInfo(encryptedResponseObject);
        protectedMsgRSType.setType(type);

        protectedMsgServiceResponseType.setProtectedMsgRS(protectedMsgRSType);

        return protectedMsgServiceResponseType;
    }

    /**
     * 
     * @param arg
     * @return String con los parámetros concatenados
     */
    public static String buildString(String... arg) {
        if (null != arg) {
            StringBuilder sb = new StringBuilder();
            for (String str : arg) {
                sb.append(str);
            }
            return sb.toString();
        }
        return null;
    }

    /*
     * Metodo para crear objeto Request
     * <code>SlackNotificationServiceRequestType</code>
     * 
     * @param message
     * 
     * @param channel
     * 
     * @return <code>SlackNotificationServiceRequestType</code>
     */

    public static SlackNotificationServiceRequestType getSlackNotificationServiceRequestType(
            String message, String channel) {

        SlackNotificationServiceRequestType slackNotificationServiceRequestType;
        SlackNotificationRQType slackNotificationRQType;

        slackNotificationServiceRequestType = new SlackNotificationServiceRequestType();
        slackNotificationRQType = new SlackNotificationRQType();

        slackNotificationRQType.setChannel(channel);
        slackNotificationRQType.setText(message);

        slackNotificationServiceRequestType
                .setSlackNotificationRQ(slackNotificationRQType);

        return slackNotificationServiceRequestType;
    }

    /**
     * Servicio para enviar notificacion al grupo aministrativo de Slack
     * 
     * @param request
     * @param message
     * @param channel
     * @param em
     * @return <code>ResponseMessageObjectType</code>
     */
    public static ResponseMessageObjectType sendSlackNotification(
            RequestMessageObjectType request, String message, String channel,
            EntityManager em) {

        SlackNotificationServiceRequestType slackNotificationServiceRequestType = getSlackNotificationServiceRequestType(
                message, channel);

        return ServiceControllerHelper.getInstance()
                .createBrokerRequestNequiApp(request,
                        slackNotificationServiceRequestType, null,
                        Constant.NAME_NOTIFICATION,
                        Constant.NAMESPACE_UTIL_SERVICES_NOTIFICATION,
                        Constant.OPERATION_SEND_MIGRATION,
                        Constant.SLACK_NOTIFICATION_RQ,
                        Constant.SLACK_NOTIFICATION_RS, em);

    }

    /**
     * Metodo que retorna un mensaje a partir del objeto <code>Parametro</code>.
     * 
     * @param templateString
     * @param regexString
     * @param value
     * @return <code>String</code>
     */
    public static String buildMessageFromTemplate(String templateString,
            String regexString, String value) {

        String message = templateString;

        if (null != templateString && !templateString.isEmpty()) {

            if (null != regexString && null != value)
                message = message.replaceAll(regexString, value);
        }
        return message;

    }

    /**
     * <p>
     * M&eacute;todo que construye la URL para consumir los servicios de
     * middleware.
     * </p>
     * 
     * @param host
     * @param port
     * @param context
     * @return URL para consumir los servicios de middleware.
     */
    public static String getUrlRestServicesEndPoint(String host, String port,
            String context) {
        StringBuilder url = new StringBuilder();
        url.append(host);
        if (null != port && !Constant.COMMON_STRING_EMPTY_STRING.equals(port)) {
            url.append(Constant.COLON);
            url.append(port);
        }
        url.append(context);
        return url.toString();
    }

    /**
     * Metodo para reemplazar caracteres especiales y acentos. En caso de que
     * sea un espacio, se reemplaza por vacio
     * 
     * @param stringData
     * @param charReplace
     * @return String without special characters
     */
    @Deprecated
    public static String replaceAccentAndSpecialCharacter(String stringData,
            char charReplace) {

        char[] arrayData;
        Pattern pattern;
        Matcher matcher;
        int pos;

        // Si el caracter de reemplazo es vacio se cambia por caracter numeral
        if (Constant.COMMON_CHAR_BLANK_SPACE == charReplace) {
            charReplace = Constant.COMMON_CHAR_NUMERAL_REPLACE;
        }

        if (null != stringData && !stringData.isEmpty()) {

            // Se crea el patron con regex para detectar caracteres que NO
            // pertenecen al alfabeto.
            pattern = Pattern
                    .compile(Constant.COMMON_STRING_REGEX_WHITOUT_ALPHABET);
            matcher = pattern.matcher(stringData);
            arrayData = stringData.toCharArray();

            // Obtenemos la posicion de cada caracter que no pertenece al
            // alfabeto.
            // Tambien reemplazamos el caracter que NO cumple y lo reemplazamos
            // por
            // el char que llega como parametro.
            while (matcher.find()) {
                pos = matcher.start();
                arrayData[pos] = charReplace;
            }
            // Si se reemplazo espacios, se cambia por caracter vacio
            String arrayDataString = String.valueOf(arrayData);
            arrayDataString = arrayDataString.replace(
                    Constant.COMMON_STRING_NUMERAL_REPLACE,
                    Constant.COMMON_STRING_EMPTY_STRING);
            return arrayDataString;
        }

        return stringData;
    }

    /**
     * Metodo para reemplazar caracteres especiales y acentos. En caso de que
     * sea un espacio, se reemplaza por vacio
     * 
     * @param stringData
     * @return String without special characters
     */
    public static String replaceAccentAndSpecialCharacter(String stringData) {

        char[] arrayData;
        Pattern pattern;
        Matcher matcher;
        int pos;

        if (null != stringData && !stringData.isEmpty()) {

            // Se crea el patron con regex para detectar caracteres que NO
            // pertenecen al alfabeto.
            pattern = Pattern
                    .compile(Constant.COMMON_STRING_REGEX_WHITOUT_ALPHABET);
            matcher = pattern.matcher(stringData);
            arrayData = stringData.toCharArray();

            // Obtenemos la posicion de cada caracter que no pertenece al
            // alfabeto.
            // Tambien reemplazamos el caracter que NO cumple y lo reemplazamos
            // por
            // el char que llega como parametro.
            while (matcher.find()) {
                pos = matcher.start();
                if (arrayData[pos] != Constant.COMMON_CHAR_BLANK_SPACE)
                    arrayData[pos] = Constant.COMMON_CHAR_NUMERAL_REPLACE;
            }
            // Si se reemplazo espacios, se cambia por caracter vacio
            String arrayDataString = String.valueOf(arrayData);

            arrayDataString = arrayDataString.replace(
                    Constant.COMMON_STRING_NUMERAL_REPLACE,
                    Constant.COMMON_STRING_EMPTY_STRING);

            return arrayDataString;
        }

        return stringData;
    }

    /**
     * Método para obtener un Date a partir de una fecha <code>String</code> y
     * un formato.
     * 
     * @param stringDate
     * @param format
     * @return <code>String</code>
     * @throws ParseException
     */
    public static Date getDateFormat(String stringDate, String format)
            throws ParseException {

        SimpleDateFormat simpleDateFormat;
        Date date;

        simpleDateFormat = new SimpleDateFormat(format);
        date = simpleDateFormat.parse(stringDate);

        return date;
    }

    /**
     * Método para dar formato a una fecha.
     * 
     * @param date
     * @param format
     * @return <code>String</code>
     */
    public static String setDateFormat(Date date, String format) {

        SimpleDateFormat simpleDateFormat;
        String dateFormated;

        simpleDateFormat = new SimpleDateFormat(format);
        dateFormated = simpleDateFormat.format(date);

        return dateFormated;
    }

}
