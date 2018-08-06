/**
 * 
 */
package co.bdigital.admin.util.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.bdigital.admin.util.ConstantADM;
import co.bdigital.cmm.ejb.util.Constant;
import co.bdigital.shl.tracer.CustomLogger;

/**
 * @author hansel.ospino
 *
 */
@FacesConverter("co.bdigital.admin.util.converter.StringDateConverter")
public class StringDateConverter implements Converter {

    private static CustomLogger logger;

    public StringDateConverter() {
        super();
        logger = new CustomLogger(this.getClass());
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        String valueAsString = value.toString();

        String[] valueWithoutMills = valueAsString
                .split(Constant.DOUBLE_BACK_SLASH + Constant.EAR_DOT);

        if (valueWithoutMills.length > Constant.COMMON_INT_UNO) {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    Constant.COMMON_FORMAT_DATE_TO_FRONT);
            Date soliciteDate = null;
            try {
                soliciteDate = sdf.parse(valueWithoutMills[0]);
                sdf.applyPattern(Constant.COMMON_FORMAT_DATE_TO_FRONT);
                return sdf.format(soliciteDate);
            } catch (ParseException e) {
                logger.error(ConstantADM.WORD_ERROR, e);
            }
        }
        return valueAsString;
    }

}
