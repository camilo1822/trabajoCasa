package co.bdigital.admin.bco.acs502.massivecloseaccount;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import co.bdigital.admin.util.ConstantADM;

/**
 * @author hansel.ospino@pragma.com.co
 *
 */
@Stateless(mappedName = "MassiveCloseAccountInitialBean")
@LocalBean
public class MassiveCloseAccountInitialBean {

    /**
     * Metodo que encola mensaje para JMS de envio de correo recibiendo un
     * objeto {@link Serializable}.
     * 
     * @param backoutRequestType
     * @throws JMSException
     * @throws NamingException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void executeService(Serializable backoutRequestType)
            throws JMSException, NamingException {

        InitialContext initialContext = null;
        Context myEnv = null;
        QueueConnectionFactory sampleQCF;
        QueueConnection queueConn = null;
        Queue backoutQ;
        ObjectMessage objMsg;

        QueueSender queueSender = null;
        QueueSession queueSession = null;

        try {
            initialContext = new InitialContext();
            myEnv = (Context) initialContext
                    .lookup(ConstantADM.COMMON_STRING_ENVIROMENT);

            sampleQCF = (QueueConnectionFactory) myEnv
                    .lookup(ConstantADM.COMMON_STRING_QUEUE_FACTORY);

            queueConn = (QueueConnection) sampleQCF.createConnection();

            queueSession = queueConn.createQueueSession(false, 0);

            backoutQ = (Queue) myEnv
                    .lookup(ConstantADM.COMMON_STRING_QUEUE_MASSIVE_CLOSE_ACCOUNT);

            queueSender = queueSession.createSender(backoutQ);

            objMsg = queueSession.createObjectMessage();

            objMsg.setObject(backoutRequestType);

            queueSender.send(objMsg);

        } finally {

            if (null != queueSender) {
                queueSender.close();
            }

            if (null != queueSession) {
                queueSession.close();
            }

            if (null != queueConn) {
                queueConn.close();
            }

            if (null != myEnv) {
                myEnv.close();
            }

            if (null != initialContext) {
                initialContext.close();
            }
        }
    }

    /**
     * Metodo que encola mensaje para JMS de envio de correo recibiendo un
     * {@link String} en formato JSON.
     * 
     * @param jmsStringMessage
     * @throws JMSException
     * @throws NamingException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void sendJmsStringMessage(String jmsStringMessage)
            throws JMSException, NamingException {

        InitialContext initialContext = null;
        Context myEnv = null;
        QueueConnectionFactory sampleQCF;
        QueueConnection queueConn = null;
        Queue backoutQ;
        TextMessage textMsg;

        QueueSender queueSender = null;
        QueueSession queueSession = null;

        try {
            initialContext = new InitialContext();
            myEnv = (Context) initialContext
                    .lookup(ConstantADM.COMMON_STRING_ENVIROMENT);

            sampleQCF = (QueueConnectionFactory) myEnv
                    .lookup(ConstantADM.COMMON_STRING_QUEUE_FACTORY);

            queueConn = (QueueConnection) sampleQCF.createConnection();

            queueSession = queueConn.createQueueSession(false, 0);

            backoutQ = (Queue) myEnv
                    .lookup(ConstantADM.COMMON_STRING_QUEUE_MASSIVE_CLOSE_ACCOUNT);

            queueSender = queueSession.createSender(backoutQ);

            textMsg = queueSession.createTextMessage();

            textMsg.setText(jmsStringMessage);

            queueSender.send(textMsg);

        } finally {

            if (null != queueSender) {
                queueSender.close();
            }

            if (null != queueSession) {
                queueSession.close();
            }

            if (null != queueConn) {
                queueConn.close();
            }

            if (null != myEnv) {
                myEnv.close();
            }

            if (null != initialContext) {
                initialContext.close();
            }
        }
    }

}
