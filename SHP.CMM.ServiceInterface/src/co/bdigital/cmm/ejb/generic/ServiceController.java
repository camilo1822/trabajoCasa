/**
 * 
 */
package co.bdigital.cmm.ejb.generic;

import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;

/**
 * @author ricardo.paredes
 *
 */
public interface ServiceController {

    public ResponseMessageObjectType executeOperation(
            RequestMessageObjectType request);

}
