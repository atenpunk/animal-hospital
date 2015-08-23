package co.th.aten.network.web;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;

@SessionScoped
@Named
public class TransferWeeklyController implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private Logger log;
	@Inject
	@DBDefault
	private EntityManager em;
	@Inject
	private CurrentUserManager currentUser;
	@Inject
	private FacesContext facesContext;
	@Inject
	private CustomerControl customerControl;
	
	private Date startDate;
	private Date endDate;

	@PostConstruct
	public void init(){
		log.info("init method TransferWeeklyController");
		Calendar calStart = Calendar.getInstance();
		calStart.add(Calendar.DATE, -30);
		calStart.set(Calendar.HOUR_OF_DAY, 0);
		calStart.set(Calendar.MINUTE, 0);
		calStart.set(Calendar.SECOND, 0);
		calStart.set(Calendar.MILLISECOND, 0);
		startDate = calStart.getTime();
		Calendar calEnd = Calendar.getInstance();
		calEnd.set(Calendar.HOUR_OF_DAY, 0);
		calEnd.set(Calendar.MINUTE, 0);
		calEnd.set(Calendar.SECOND, 0);
		calEnd.set(Calendar.MILLISECOND, 0);
		endDate = calEnd.getTime();
		search();
	}

	public void search(){
		try{
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
