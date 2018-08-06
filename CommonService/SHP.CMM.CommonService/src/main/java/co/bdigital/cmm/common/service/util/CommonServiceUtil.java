package co.bdigital.cmm.common.service.util;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import co.bdigital.cmm.common.service.constant.ConstantCommonService;
import co.bdigital.cmm.jpa.exception.JPAException;

/**
 * La clase Constant Maneja las constantes del proyecto Security Manager.
 * 
 * @author cristian.martinez
 */
public class CommonServiceUtil {

    private CommonServiceUtil() {

    }

    /**
     * Método que construye un String a partir de la concatenación de sus
     * parámetros
     * 
     * @param args
     * @return
     */
    public static String generateString(String... args) {

        StringBuilder bf = new StringBuilder();

        if (null != args) {
            for (String arg : args) {
                bf.append(arg);
            }
        }
        return bf.toString();
    }

    public static String objectToString(Object object) throws JPAException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = ConstantCommonService.COMMON_STRING_EMPTY;
        try {
            jsonInString = mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new JPAException(
                    generateString(ConstantCommonService.ERROR_PARSING), e);
        }
        return jsonInString;
    }

    @SuppressWarnings("unchecked")
    public static <T> T parsePayload(String any, Class<?> clazz)
            throws JPAException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = objectToArray(any,
                    ConstantCommonService.COMMON_ARRAY_GENERAL_INFO);

            return (T) mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new JPAException(generateString(
                    ConstantCommonService.ERROR_PARSING, clazz.getName()), e);
        }

    }

    public static String objectToArray(String json, String[] array) {
        String before = null;
        String after = null;
        String half = null;
        StringBuilder strbuild;
        StringBuilder stringBuilder;
        int position;
        int positionAuxiliar;
        for (int i = ConstantCommonService.COMMON_INT_ZERO; i < array.length; i++) {
            position = json.indexOf(array[i]);
            if (position >= ConstantCommonService.COMMON_INT_ZERO) {
                positionAuxiliar = position + array[i].length()
                        + ConstantCommonService.COMMON_INT_TWO;

                if (json.charAt(
                        positionAuxiliar) != ConstantCommonService.COMMON_CHAR_LEFT_SQUARE) {
                    before = json.substring(
                            ConstantCommonService.COMMON_INT_ZERO,
                            positionAuxiliar);
                    position = json.indexOf(
                            ConstantCommonService.COMMON_STRING_RIGHT_KEY,
                            position);
                    half = ConstantCommonService.COMMON_STRING_LEFT_KEY
                            + json.substring(
                                    positionAuxiliar
                                            + ConstantCommonService.COMMON_INT_ONE,
                                    position);
                    strbuild = new StringBuilder();
                    strbuild.append(
                            ConstantCommonService.COMMON_STRING_RIGHT_KEY);
                    strbuild.append(
                            ConstantCommonService.COMMON_STRING_RIGHT_SQUARE);
                    strbuild.append(json.substring(
                            position + ConstantCommonService.COMMON_INT_ONE));
                    after = strbuild.toString();

                    stringBuilder = new StringBuilder();
                    stringBuilder.append(before);
                    stringBuilder.append(
                            ConstantCommonService.COMMON_STRING_LEFT_SQUARE);
                    stringBuilder.append(half);
                    stringBuilder.append(after);
                    json = stringBuilder.toString();
                }

            }
        }
        return json;
    }

}
