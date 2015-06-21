package co.th.aten.network.web;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.security.annotation.Authenticated;

@SessionScoped
@Named
public class LanguageController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443351151396868724L;

	@Inject
	Logger log;

	@Inject
	@Authenticated
	private Instance<UserLogin> user;

	@Inject
	private MessageFactory factory;
	@Inject
	private Messages messages;
	private Locale locale;
	
    public String switchLocale(String lang) {
    	log.info("locale = "+lang);
        locale = new Locale(lang);
    	log.info("locale.getLanguage = "+locale.getLanguage());
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        return FacesContext.getCurrentInstance().getViewRoot().getViewId() +
            "?faces-redirect=true";
    }

	@PostConstruct
	public void init(){
		log.info("init method LanguageController");
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		log.info("locale.getLanguage = "+locale.getLanguage());
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	
 
}
