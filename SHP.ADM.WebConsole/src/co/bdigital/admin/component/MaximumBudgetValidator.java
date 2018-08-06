package co.bdigital.admin.component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import co.bdigital.admin.util.ConstantADM;

/**
 * Custom JSF Validator for  Maximun Budget input
 */
@FacesValidator("custom.maximumBudgetValidator")
public class MaximumBudgetValidator implements Validator {

    private static final String FIELD_AVAILABLE_VALUE = "tabUpdatePromotion:formUpdatePromotion:formGetPromotion:availableValue";
    private static final String FIELD_RESET = "tabUpdatePromotion:formUpdatePromotion:formGetPromotion:resetCamp_input";
    private static final String ON = "on";
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        Double maximunBudget = new Double(value.toString());

        ExternalContext ec = context.getExternalContext();
        String avaiableTxt = ec.getRequestParameterMap()
                .get(FIELD_AVAILABLE_VALUE);
        
        Boolean reset = Boolean.FALSE;
        
        if(ON.equalsIgnoreCase(ec.getRequestParameterMap()
                .get(FIELD_RESET))){
            reset = Boolean.TRUE;
        }
        
        if (null != avaiableTxt && !avaiableTxt.isEmpty()) {

            Double available = new Double(avaiableTxt);

            if (available > maximunBudget && !reset) {
                throw new ValidatorException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                ConstantADM.VALIDATION_TYPE_BUDGET_TITLE,
                                ConstantADM.VALIDATION_TYPE_BUDGET_DESC));
            }
        }

    }
}
