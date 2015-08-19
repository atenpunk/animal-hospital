package co.th.aten.network.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.MasterConfigkey;
import co.th.aten.network.i18n.AppBundleKey;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

@ViewScoped
@Named
public class CurrencyController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443351151396868724L;

	@Inject
	private Logger log;
	@Inject
	@DBDefault
	private EntityManager em;
	@Inject
	private CurrentUserManager currentUser;
	@Inject
	private MessageFactory factory;
	@Inject
	private Messages messages;
	private double usd;
	private MasterConfigkey config;

	@PostConstruct
	public void init(){
		log.info("init method CurrencyController");
		try{
			config = em.find(MasterConfigkey.class, "MONEY_USD");
			usd = 0;
			if(config!=null){
				usd = Double.parseDouble(StringUtil.n2b(config.getValue01()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void save(){
		try{
			log.info("save CurrencyController USD = "+usd);
			if(usd>0){
				config.setValue01(String.valueOf(usd));
				em.merge(config);
				messages.info(new AppBundleKey("menu.label.system.editCurrency.success",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
			}else{
				messages.error(new AppBundleKey("menu.label.system.editCurrency.failValue",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
			}
		}catch(Exception ex){
			ex.printStackTrace();
			messages.error(new AppBundleKey("menu.label.system.editCurrency.fail",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}
	}

	public double getUsd() {
		return usd;
	}

	public void setUsd(double usd) {
		this.usd = usd;
	}
}
