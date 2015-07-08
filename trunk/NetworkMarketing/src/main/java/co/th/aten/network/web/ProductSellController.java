package co.th.aten.network.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;

@ViewScoped
@Named
public class ProductSellController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443351151396868724L;

	@Inject
	private Logger log;

	@Inject
	private CurrentUserManager currentUser;

	@Inject
	private MessageFactory factory;
	
	@Inject
	private Messages messages;

	@Inject
	@DBDefault
	private EntityManager em;

	@PostConstruct
	public void init(){
		log.info("init method ProductSellController");
		long startTime = System.currentTimeMillis();
		
		
		long endTime = System.currentTimeMillis();
		log.info("init method ProductSellController Time = "+((endTime-startTime)/1000d)+"s");
	}
}
