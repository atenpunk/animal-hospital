package co.th.aten.network.web;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.control.TransactionHeaderControl;
import co.th.aten.network.control.TransactionScoreMatchingControl;
import co.th.aten.network.control.TransactionScorePackageControl;
import co.th.aten.network.i18n.AppBundleKey;
import co.th.aten.network.security.CurrentUserManager;

@Stateless
@Named
@TransactionManagement(TransactionManagementType.BEAN)
public class CalculateWeeklyController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443351151396868724L;

	@PersistenceContext
	private EntityManager em;
	@Resource
	private SessionContext sessionCtx;
	@Inject
	private Messages messages;
	@Inject
	private CustomerControl customerControl;
	@Inject
	private TransactionScoreMatchingControl transactionScoreMatchingControl;
	@Inject
	private TransactionHeaderControl transactionHeaderControl;
	@Inject
	private TransactionScorePackageControl transactionScorePackageControl;
	@Inject
	Logger log;
	@Inject
	private CurrentUserManager currentUser;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
	private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
	private Date dateTime = new Date();

	public void execute() {
		long startTime = System.currentTimeMillis();
		try{
			
			
		}catch(Exception e){
			e.printStackTrace();
			messages.error(new AppBundleKey("error.label.calculate.fail",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}
		log.info("CalculateWeeklyController Processor Time = "+((System.currentTimeMillis()-startTime)/1000d)+"s");
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
}
