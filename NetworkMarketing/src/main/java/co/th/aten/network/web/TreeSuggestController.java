package co.th.aten.network.web;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.annotation.Authenticated;

//@ViewScoped
@SessionScoped
@Named
public class TreeSuggestController implements Serializable{

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

	@Inject
	@DBDefault
	private EntityManager em;

	private String html;

	//	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
	//	private DecimalFormat df = new DecimalFormat("0000000");

	@PostConstruct
	public void init(){
		log.info("init method TreeSuggestController");

		genAutoTagTree();
	}

	private void genAutoTagTree(){
		html = "";
		
		StringBuffer str = new StringBuffer();
		str.append("<table>");
		str.append("<tr>");
		str.append("<td>");
		str.append("<div class=\"tree\">");
		for(int index01=1;index01<2;index01++){
			str.append("<ul>");
			str.append("<li>");
			str.append("<div>");
			str.append(genData());
			str.append("</div>");
			str.append("<ul>");
			for(int index02=2;index02<12;index02++){
				str.append("<li>");
				str.append("<div>");
				str.append(genData());
				str.append("</div>");
				str.append("<ul>");
				for(int index03=4;index03<6;index03++){
					str.append("<li>");
					str.append("<div>");
					str.append(genData());
					str.append("</div>");
//					str.append("<ul>");
//					for(int index04=6;index04<8;index04++){
//						str.append("<li>");
//						str.append("<div>");
//						str.append(genData());
//						str.append("</div>");
//						str.append("<ul>");
//						for(int index05=6;index05<8;index05++){
//							str.append("<li>");
//							str.append("<div>");
//							str.append(genData());
//							str.append("</div>");
//							str.append("</li>");
//						}
//						str.append("</ul>");
//						str.append("</li>");
//					}
//					str.append("</ul>");
					str.append("</li>");
				}
				str.append("</ul>");
				str.append("</li>");
			}			
			str.append("</ul>");
			str.append("</li>");
			str.append("</ul>");
		}
		str.append("</div>");
		str.append("</td>");
		str.append("</tr>");
		str.append("</table>");
		html = str.toString();
	}

	private String genData(){
		StringBuffer str = new StringBuffer();
		str.append("<a class=\"tooltip\" href=\"#\">");
		str.append("<img src=\"../resources/gfx/1.png\" width=\"55px\" height=\"55px\"/>");
		str.append("<br/>");
		str.append("<h>AAAAAAA</h>");
		str.append("<br/>");
		str.append("<h>BBBBBBB</h>");
		str.append("");
		str.append("");
		str.append("");
		str.append("");
		str.append("");
		str.append("</a>");
		return str.toString();
	}

	private MemberCustomer getCustomerById(long customerId){
		try{
			MemberCustomer customer = em.find(MemberCustomer.class, new Integer(new BigDecimal(customerId).intValue()));
			return customer;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}


}
