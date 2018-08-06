package co.bdigital.admin.component;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import co.bdigital.admin.model.ActionRol;
import co.bdigital.admin.util.ConstantADM;
import co.bdigital.shl.tracer.CustomLogger;

/**
 *
 * @author juandiego
 */
@ManagedBean(name = "treeBasicVwBean")
public class BasicVwBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private transient TreeNode root;
    private static CustomLogger logger;

    public BasicVwBean() {
        logger = new CustomLogger(this.getClass());
    }

    @PostConstruct
    public void init() {

        List<ActionRol> actionsRol = getActionsByRol();

        root = new DefaultTreeNode(ConstantADM.TREE_DEFAULT_NODE, null);

        for (ActionRol action : actionsRol) {
            TreeNode node = new DefaultTreeNode(
                    new LinkNode(action.getName(), action.getUrl()), root);
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            logger.error(ConstantADM.TIME_UNIT_SLEEP, e);
        }
    }

    public List<ActionRol> getActionsByRol() {

        @SuppressWarnings("unchecked")
        List<ActionRol> actionsRol = (List<ActionRol>) FacesContext
                .getCurrentInstance().getExternalContext().getSessionMap()
                .get(ConstantADM.ACTIONS_BY_ROL_SESION);

        return actionsRol;
    }

    public TreeNode getRoot() {
        return root;
    }
}
