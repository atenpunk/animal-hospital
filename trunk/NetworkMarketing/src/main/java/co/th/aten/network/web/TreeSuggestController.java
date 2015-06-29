package co.th.aten.network.web;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.i18n.AppBundleKey;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.annotation.Authenticated;
import co.th.aten.network.util.StringUtil;

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
	private String searchCustomer;
	private MemberCustomer customerTop;
	private String selectedCustomer;

	//	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
	//	private DecimalFormat df = new DecimalFormat("0000000");

	@PostConstruct
	public void init(){
		log.info("init method TreeSuggestController");

		if(user.get().getCustomerId()!=null){
			genAutoTagTree(user.get().getCustomerId());
		}

	}

	public void action(){
		long startTime = System.currentTimeMillis();
		log.info("##### action() ##### ");
		try{
			Map<String, String> params =FacesContext.getCurrentInstance().
					getExternalContext().getRequestParameterMap();
			String parameterOne = params.get("aaa");
			log.info("action() aaa = "+parameterOne);
			if(parameterOne!=null && parameterOne.length()>0){
				searchCustomer = parameterOne;
				search();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		searchCustomer = "";
		long endTime = System.currentTimeMillis();
		log.info("action() Time = "+((endTime-startTime)/1000d)+"s");
		
	}

	public void search(){
		long startTime = System.currentTimeMillis();
		log.info("##### SEARCH ##### = "+searchCustomer);
		html = "";
		try{
			if(searchCustomer!=null && searchCustomer.trim().length()>0){
				MemberCustomer memSearch = null;
				boolean chkNumber = false;
				try{
					Integer.parseInt(searchCustomer);
					chkNumber = true;
				}catch(Exception ex){
					log.info("Error : "+ex.getMessage());
				}
				if(chkNumber){
					memSearch = (MemberCustomer)em.createQuery("From MemberCustomer Where customerMember =:customerMember ")
							.setParameter("customerMember", searchCustomer).getSingleResult();
				}else{
					searchCustomer = searchCustomer.replaceAll(" or ", "");
					searchCustomer = searchCustomer.replaceAll(" OR ", "");
					memSearch = (MemberCustomer)em.createQuery("From MemberCustomer Where firstName like '%"+searchCustomer+"%' ")
							.getResultList().get(0);
				}
				long cusId = memSearch.getCustomerId().longValue();
				log.info("##### cusId ##### = "+cusId);
				log.info("##### login ##### = "+user.get().getCustomerId().getCustomerId().intValue());
				if(cusId > user.get().getCustomerId().getCustomerId().intValue()){
					String sql = "From MemberCustomer " +
							" Where customerId = "+user.get().getCustomerId().getCustomerId().intValue();
					boolean chk = true;
					boolean chkFound = true;
					while(chk){
						log.info("SQL + "+sql);
						String subSql = "";
						List<MemberCustomer> customerList = em.createQuery(sql,MemberCustomer.class).getResultList();
						for(MemberCustomer cus:customerList){
							if(StringUtil.n2b(cus.getLowerLeftId())==cusId || StringUtil.n2b(cus.getLowerRightId())==cusId){
								genAutoTagTree(memSearch);
								chkFound = false;
								chk = false;
								break;
							}else{
								subSql += (cus.getLowerLeftId()==null?"":cus.getLowerLeftId().intValue()+",");
								subSql += (cus.getLowerRightId()==null?"":cus.getLowerRightId().intValue()+",");
							}
						}
						if(subSql.equals("")){
							// alert ERROR
							chk = false;
							break;
						}
						sql = "From MemberCustomer " +
								" Where customerId in ";
						subSql = subSql.substring(0, subSql.length()-1);
						subSql = "("+subSql+")";
						sql += subSql;
					}
					if(chkFound){
						messages.info(new AppBundleKey("error.label.searchOutLine",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
					}
				}else if(cusId == user.get().getCustomerId().getCustomerId().intValue()){
					genAutoTagTree(user.get().getCustomerId());
				}else {
					// alert ERROR
					messages.info(new AppBundleKey("error.label.searchOutLine",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
				}
			}else{
				messages.info(new AppBundleKey("error.label.pleaseInputText",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
			}
		}catch(Exception e){
			log.info("Error : "+e.getMessage());
			messages.info(new AppBundleKey("error.label.searchOutLine",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}

		long endTime = System.currentTimeMillis();
		log.info("search() Time = "+((endTime-startTime)/1000d)+"s");
	}

	public void genTreeSuggest(String flag){
		long startTime = System.currentTimeMillis();
		log.info("flag = "+flag);
		MemberCustomer customer = null;
		try{
			if(flag!=null && flag.equals("0")){
				// top top
				customer = user.get().getCustomerId();
			}else if(flag!=null && flag.equals("2")){
				// top
				if(customerTop!=null ){
					if(customerTop.getCustomerId()!=user.get().getCustomerId().getCustomerId()){
						customer = em.find(MemberCustomer.class, new Integer(customerTop.getRecommendId()));
					}else{
						customer = user.get().getCustomerId();
					}
				}
			}
			genAutoTagTree(customer);
		}catch(Exception e){
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		log.info("genTreeSuggest(String flag) Time = "+((endTime-startTime)/1000d)+"s");
	}

	private void genAutoTagTree(MemberCustomer customer){
		html = "";
		customerTop = customer;
		if(customer!=null){
			StringBuffer str = new StringBuffer();
			str.append("<table>");
			str.append("<tr>");
			str.append("<td>");
			str.append("<div class=\"tree\">");
			str.append("<ul>");
			str.append("<li>");
			str.append("<div>");
			str.append(genData(customer));
			str.append("</div>");
			List<MemberCustomer> list_02 = em.createQuery("From MemberCustomer " +
					" Where recommendId=:recommendId ",MemberCustomer.class)
					.setParameter("recommendId", customer.getCustomerId())
					.getResultList();
			if(list_02!=null && list_02.size()>0){
				str.append("<ul>");
				for(MemberCustomer model2:list_02){
					str.append("<li>");
					str.append("<div>");
					str.append(genData(model2));
					str.append("</div>");
					List<MemberCustomer> list_03 = em.createQuery("From MemberCustomer " +
							" Where recommendId=:recommendId ",MemberCustomer.class)
							.setParameter("recommendId", model2.getCustomerId())
							.getResultList();
					if(list_03!=null && list_03.size()>0){
						str.append("<ul>");
						for(MemberCustomer model3:list_03){
							str.append("<li>");
							str.append("<div>");
							str.append(genData(model3));
							str.append("</div>");
							List<MemberCustomer> list_04 = em.createQuery("From MemberCustomer " +
									" Where recommendId=:recommendId ",MemberCustomer.class)
									.setParameter("recommendId", model3.getCustomerId())
									.getResultList();
							if(list_04!=null && list_04.size()>0){
								str.append("<ul>");
								for(MemberCustomer model4:list_04){
									str.append("<li>");
									str.append("<div>");
									str.append(genData(model4));
									str.append("</div>");
									str.append("</li>");
								}
								str.append("</ul>");
							}
							str.append("</li>");
						}
						str.append("</ul>");
					}
					str.append("</li>");
				}
				str.append("</ul>");
			}
			str.append("</li>");
			str.append("</ul>");
			str.append("</div>");
			str.append("</td>");
			str.append("</tr>");
			str.append("</table>");
			html = str.toString();
		}

		//		StringBuffer str = new StringBuffer();
		//		str.append("<table>");
		//		str.append("<tr>");
		//		str.append("<td>");
		//		str.append("<div class=\"tree\">");
		//		for(int index01=1;index01<2;index01++){
		//			str.append("<ul>");
		//			str.append("<li>");
		//			str.append("<div>");
		//			str.append(genData());
		//			str.append("</div>");
		//			str.append("<ul>");
		//			for(int index02=2;index02<12;index02++){
		//				str.append("<li>");
		//				str.append("<div>");
		//				str.append(genData());
		//				str.append("</div>");
		//				str.append("<ul>");
		//				for(int index03=4;index03<6;index03++){
		//					str.append("<li>");
		//					str.append("<div>");
		//					str.append(genData());
		//					str.append("</div>");
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
		//					str.append("</li>");
		//				}
		//				str.append("</ul>");
		//				str.append("</li>");
		//			}			
		//			str.append("</ul>");
		//			str.append("</li>");
		//			str.append("</ul>");
		//		}
		//		str.append("</div>");
		//		str.append("</td>");
		//		str.append("</tr>");
		//		str.append("</table>");
		//		html = str.toString();
	}

	private String genData(MemberCustomer customer){
		StringBuffer str = new StringBuffer();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext
				.getExternalContext()
				.getContext();
		String url = servletContext.getContextPath()+"/customer/treeSuggest?aaa="+customer.getCustomerMember();
		str.append("<a class=\"tooltip\" href=\""+url+"\">");
		String img = "";
		if(customer.getPositionId()==1){
			img = "../resources/gfx/1.png";
		}else if(customer.getPositionId()==2){
			img = "../resources/gfx/2.png";
		}else if(customer.getPositionId()==3){
			img = "../resources/gfx/3.png";
		}else if(customer.getPositionId()==4){
			img = "../resources/gfx/4.png";
		}else if(customer.getPositionId()==5){
			img = "../resources/gfx/5.png";
		}
		str.append("<img src=\""+img+"\" width=\"55px\" height=\"55px\"/>");
		str.append("<br/>");
		str.append("<h>"+StringUtil.n2b(customer.getCustomerMember())+"</h>");
		str.append("<br/>");
		String name = StringUtil.n2b(customer.getFirstName()).split(" ")[0];
		if(name!=null && name.length()>9){
			name = name.substring(0, 9);
		}
		str.append("<h>"+name+"</h>");
		str.append("<span>");
		str.append(" <img src=\"../resources/gfx/callout.gif\" class=\"callout\"> ");
		str.append("<h>สมาชิก : "+StringUtil.n2b(customer.getCustomerMember())+"["+StringUtil.n2b(customer.getFirstName())+"]"+"</h>");
		str.append("</span>");
		//		str.append("");
		//		str.append("");
		str.append("</a>");
		return str.toString();
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getSearchCustomer() {
		return searchCustomer;
	}

	public void setSearchCustomer(String searchCustomer) {
		this.searchCustomer = searchCustomer;
	}

	public String getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(String selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}



}
