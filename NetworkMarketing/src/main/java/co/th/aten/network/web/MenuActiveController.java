package co.th.aten.network.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;

@SessionScoped
@Named
public class MenuActiveController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443351151396868724L;

	@Inject
	Logger log;

	@Inject
	private MessageFactory factory;
	@Inject
	private Messages messages;
	@Inject
	private CurrentUserManager currentUser;
	@Inject
	@DBDefault
	private EntityManager em;
	
	private String menuHome;
	private String menuProfile;
	private String menuFlowTeam;
	private String menuInfoSuggest;
	private String menuTrading;
	private String menuReport;

	@PostConstruct
	public void init(){
		log.info("init method MenuActiveController");
		menuHome = "active";
		menuProfile = "";
		menuFlowTeam = "";
		menuInfoSuggest = "";
		menuTrading = "";
		menuReport = "";
	}
	
	public void homeActive(){
		menuHome = "active";
		menuProfile = "";
		menuFlowTeam = "";
		menuInfoSuggest = "";
		menuTrading = "";
		menuReport = "";
	}
	
	public void profileActive(){
		menuHome = "";
		menuProfile = "active";
		menuFlowTeam = "";
		menuInfoSuggest = "";
		menuTrading = "";
		menuReport = "";
	}
	
	public void flowTeamActive(){
		menuHome = "";
		menuProfile = "";
		menuFlowTeam = "active";
		menuInfoSuggest = "";
		menuTrading = "";
		menuReport = "";
	}
	
	public void suggestActive(){
		menuHome = "";
		menuProfile = "";
		menuFlowTeam = "";
		menuInfoSuggest = "active";
		menuTrading = "";
		menuReport = "";
	}
	
	public void tradingActive(){
		menuHome = "";
		menuProfile = "";
		menuFlowTeam = "";
		menuInfoSuggest = "";
		menuTrading = "active";
		menuReport = "";
	}
	
	public void reportActive(){
		menuHome = "";
		menuProfile = "";
		menuFlowTeam = "";
		menuInfoSuggest = "";
		menuTrading = "";
		menuReport = "active";
	}	

	public String getMenuHome() {
		return menuHome;
	}

	public void setMenuHome(String menuHome) {
		this.menuHome = menuHome;
	}

	public String getMenuProfile() {
		return menuProfile;
	}

	public void setMenuProfile(String menuProfile) {
		this.menuProfile = menuProfile;
	}

	public String getMenuFlowTeam() {
		return menuFlowTeam;
	}

	public void setMenuFlowTeam(String menuFlowTeam) {
		this.menuFlowTeam = menuFlowTeam;
	}

	public String getMenuInfoSuggest() {
		return menuInfoSuggest;
	}

	public void setMenuInfoSuggest(String menuInfoSuggest) {
		this.menuInfoSuggest = menuInfoSuggest;
	}

	public String getMenuTrading() {
		return menuTrading;
	}

	public void setMenuTrading(String menuTrading) {
		this.menuTrading = menuTrading;
	}

	public String getMenuReport() {
		return menuReport;
	}

	public void setMenuReport(String menuReport) {
		this.menuReport = menuReport;
	}
	
}
