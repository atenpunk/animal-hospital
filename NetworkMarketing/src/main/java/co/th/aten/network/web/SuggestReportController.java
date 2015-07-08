package co.th.aten.network.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;
import org.richfaces.component.SortOrder;

import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.MemberPosition;
import co.th.aten.network.entity.MemberSide;
import co.th.aten.network.model.InfoSuggestReportModel;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

@ViewScoped
@Named
public class SuggestReportController implements Serializable{

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

	private SortOrder orderOrder = SortOrder.unsorted;
	private SortOrder memberIdOrder = SortOrder.unsorted;
	private SortOrder memberNameOrder = SortOrder.unsorted;
	private SortOrder regisDateOrder = SortOrder.unsorted;
	private SortOrder positionOrder = SortOrder.unsorted;
	private SortOrder recommentOrder = SortOrder.unsorted;
	private SortOrder honorOrder = SortOrder.unsorted;
	private SortOrder sideOrder = SortOrder.unsorted;

	private List<InfoSuggestReportModel> infoSuggestReportModelList;
	private String searchCustomer;

	@PostConstruct
	public void init(){
		log.info("init method SuggestReportController");
		infoSuggestReportModelList = new ArrayList<InfoSuggestReportModel>();
		searchCustomer = "";
		if(currentUser.getCurrentAccount().getCustomerId()!=null)
			report(currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue());
	}

	public void sortByOrder() {
		log.info("-------> sortByOrder()");
		memberIdOrder = SortOrder.unsorted;
		memberNameOrder = SortOrder.unsorted;
		regisDateOrder = SortOrder.unsorted;
		positionOrder = SortOrder.unsorted;
		recommentOrder = SortOrder.unsorted;
		honorOrder = SortOrder.unsorted;
		sideOrder = SortOrder.unsorted;
		if (orderOrder.equals(SortOrder.ascending)) {
			setOrderOrder(SortOrder.descending);
		} else {
			setOrderOrder(SortOrder.ascending);
		}
	}

	public void sortByMemberId() {
		log.info("-------> sortByMemberId()");
		orderOrder = SortOrder.unsorted;
		memberNameOrder = SortOrder.unsorted;
		regisDateOrder = SortOrder.unsorted;
		positionOrder = SortOrder.unsorted;
		recommentOrder = SortOrder.unsorted;
		honorOrder = SortOrder.unsorted;
		sideOrder = SortOrder.unsorted;
		if (memberIdOrder.equals(SortOrder.ascending)) {
			setMemberIdOrder(SortOrder.descending);
		} else {
			setMemberIdOrder(SortOrder.ascending);
		}
	}

	public void sortByMemberName() {
		log.info("-------> sortByMemberName()");
		orderOrder = SortOrder.unsorted;
		memberIdOrder = SortOrder.unsorted;
		positionOrder = SortOrder.unsorted;
		regisDateOrder = SortOrder.unsorted;
		recommentOrder = SortOrder.unsorted;
		honorOrder = SortOrder.unsorted;
		sideOrder = SortOrder.unsorted;
		if (memberNameOrder.equals(SortOrder.ascending)) {
			setMemberNameOrder(SortOrder.descending);
		} else {
			setMemberNameOrder(SortOrder.ascending);
		}
	}

	public void sortByPosition() {
		log.info("-------> sortByPosition()");
		orderOrder = SortOrder.unsorted;
		memberIdOrder = SortOrder.unsorted;
		regisDateOrder = SortOrder.unsorted;
		memberNameOrder = SortOrder.unsorted;
		recommentOrder = SortOrder.unsorted;
		honorOrder = SortOrder.unsorted;
		sideOrder = SortOrder.unsorted;
		if (positionOrder.equals(SortOrder.ascending)) {
			setPositionOrder(SortOrder.descending);
		} else {
			setPositionOrder(SortOrder.ascending);
		}
	}

	public void sortByRecomment() {
		log.info("-------> sortByRecomment()");
		orderOrder = SortOrder.unsorted;
		memberIdOrder = SortOrder.unsorted;
		memberNameOrder = SortOrder.unsorted;
		regisDateOrder = SortOrder.unsorted;
		positionOrder = SortOrder.unsorted;
		honorOrder = SortOrder.unsorted;
		sideOrder = SortOrder.unsorted;
		if (recommentOrder.equals(SortOrder.ascending)) {
			setRecommentOrder(SortOrder.descending);
		} else {
			setRecommentOrder(SortOrder.ascending);
		}
	}

	public void sortByHonor() {
		log.info("-------> sortByHonor()");
		orderOrder = SortOrder.unsorted;
		memberIdOrder = SortOrder.unsorted;
		memberNameOrder = SortOrder.unsorted;
		regisDateOrder = SortOrder.unsorted;
		positionOrder = SortOrder.unsorted;
		recommentOrder = SortOrder.unsorted;
		sideOrder = SortOrder.unsorted;
		if (honorOrder.equals(SortOrder.ascending)) {
			setHonorOrder(SortOrder.descending);
		} else {
			setHonorOrder(SortOrder.ascending);
		}
	}

	public void sortBySide() {
		log.info("-------> sortBySide()");
		orderOrder = SortOrder.unsorted;
		memberIdOrder = SortOrder.unsorted;
		regisDateOrder = SortOrder.unsorted;
		memberNameOrder = SortOrder.unsorted;
		positionOrder = SortOrder.unsorted;
		recommentOrder = SortOrder.unsorted;
		honorOrder = SortOrder.unsorted;
		if (sideOrder.equals(SortOrder.ascending)) {
			setSideOrder(SortOrder.descending);
		} else {
			setSideOrder(SortOrder.ascending);
		}
	}

	public void sortByRegisDate() {
		log.info("-------> sortByRegisDate()");
		orderOrder = SortOrder.unsorted;
		memberIdOrder = SortOrder.unsorted;
		memberNameOrder = SortOrder.unsorted;
		positionOrder = SortOrder.unsorted;
		recommentOrder = SortOrder.unsorted;
		honorOrder = SortOrder.unsorted;
		sideOrder = SortOrder.unsorted;
		if (regisDateOrder.equals(SortOrder.ascending)) {
			setRegisDateOrder(SortOrder.descending);
		} else {
			setRegisDateOrder(SortOrder.ascending);
		}
	}

	public void report(int recommendId){
		try{
			String sql = "From MemberCustomer " +
					" Where recommendId =:recommendId ";
			log.info("report recommendId = "+recommendId);
			log.info("report SQL = "+sql);
			List<MemberCustomer> customerList = em.createQuery(sql,MemberCustomer.class)
					.setParameter("recommendId", new Integer(recommendId))
					.getResultList();
			int index = 0;
			MemberCustomer memberModel = em.find(MemberCustomer.class, new Integer(recommendId));
			InfoSuggestReportModel model = setDataModel(memberModel,++index);
			infoSuggestReportModelList.add(model);
			for(MemberCustomer cus:customerList){
				InfoSuggestReportModel modelMem = setDataModel(cus,++index);
				infoSuggestReportModelList.add(modelMem);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private InfoSuggestReportModel setDataModel(MemberCustomer memSearch, int index){
		InfoSuggestReportModel model = new InfoSuggestReportModel();
		model.setIndex(index);
		model.setCustomerId(StringUtil.n2b(memSearch.getCustomerId()));
		model.setCustomerCode(memSearch.getCustomerMember());
		model.setCustomerName(memSearch.getFirstName());
		model.setRegisDate(memSearch.getRegisDate());
		if(memSearch.getPositionId()!=null){
			model.setPosition(StringUtil.n2b(memSearch.getPositionId().getEnName()));
		}	
		model.setRecomment("");
		model.setHonor("");
		if(memSearch.getSide()!=null 
				&& memSearch.getCustomerId().intValue() != currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue()){
			MemberSide memberSide = em.find(MemberSide.class, memSearch.getSide());
			model.setSide(memberSide!=null?memberSide.getThName():"");
		}
		return model;
	}

	public void search(){
		long startTime = System.currentTimeMillis();
		log.info("##### SEARCH ##### = "+searchCustomer);
		infoSuggestReportModelList = new ArrayList<InfoSuggestReportModel>();
		orderOrder = SortOrder.unsorted;
		memberIdOrder = SortOrder.unsorted;
		memberNameOrder = SortOrder.unsorted;
		regisDateOrder = SortOrder.unsorted;
		positionOrder = SortOrder.unsorted;
		recommentOrder = SortOrder.unsorted;
		honorOrder = SortOrder.unsorted;
		sideOrder = SortOrder.unsorted;
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
				log.info("***** cusId ##### = "+cusId);
				log.info("***** login ##### = "+currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue());
				if(cusId > currentUser.getCurrentAccount().getCustomerId().getCustomerId().longValue()){
					String sql = "From MemberCustomer " +
							" Where customerId = "+currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue();
					boolean chk = true;
					while(chk){
						log.info("SQL + "+sql);
						String subSql = "";
						List<MemberCustomer> customerList = em.createQuery(sql,MemberCustomer.class).getResultList();
						for(MemberCustomer cus:customerList){
							if(StringUtil.n2b(cus.getCustomerId())==cusId){
								report(cus.getCustomerId().intValue());
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
				}else if(cusId == currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue()){
					report(memSearch.getCustomerId().intValue());
				}
			}else{
				report(currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue());
			}
		}catch(Exception e){
			log.info("Error : "+e.getMessage());
		}
		long endTime = System.currentTimeMillis();
		log.info("search() Time = "+((endTime-startTime)/1000d)+"s");
	}


	public void search(String searchCustomer){
		this.searchCustomer = searchCustomer;
		long startTime = System.currentTimeMillis();
		log.info("##### SEARCH ##### = "+searchCustomer);
		infoSuggestReportModelList = new ArrayList<InfoSuggestReportModel>();
		orderOrder = SortOrder.unsorted;
		memberIdOrder = SortOrder.unsorted;
		memberNameOrder = SortOrder.unsorted;
		regisDateOrder = SortOrder.unsorted;
		positionOrder = SortOrder.unsorted;
		recommentOrder = SortOrder.unsorted;
		honorOrder = SortOrder.unsorted;
		sideOrder = SortOrder.unsorted;
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
				log.info("***** cusId ##### = "+cusId);
				log.info("***** login ##### = "+currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue());
				if(cusId > currentUser.getCurrentAccount().getCustomerId().getCustomerId().longValue()){
					String sql = "From MemberCustomer " +
							" Where customerId = "+currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue();
					boolean chk = true;
					while(chk){
						log.info("SQL + "+sql);
						String subSql = "";
						List<MemberCustomer> customerList = em.createQuery(sql,MemberCustomer.class).getResultList();
						for(MemberCustomer cus:customerList){
							if(StringUtil.n2b(cus.getCustomerId())==cusId){
								report(cus.getCustomerId().intValue());
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
				}else if(cusId == currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue()){
					report(memSearch.getCustomerId().intValue());
				}
			}else{
				report(currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue());
			}
		}catch(Exception e){
			log.info("Error : "+e.getMessage());
		}
		this.searchCustomer = "";
		long endTime = System.currentTimeMillis();
		log.info("search() Time = "+((endTime-startTime)/1000d)+"s");
	}

	public String getSearchCustomer() {
		return searchCustomer;
	}

	public void setSearchCustomer(String searchCustomer) {
		this.searchCustomer = searchCustomer;
	}

	public SortOrder getMemberIdOrder() {
		return memberIdOrder;
	}

	public void setMemberIdOrder(SortOrder memberIdOrder) {
		this.memberIdOrder = memberIdOrder;
	}

	public SortOrder getMemberNameOrder() {
		return memberNameOrder;
	}

	public void setMemberNameOrder(SortOrder memberNameOrder) {
		this.memberNameOrder = memberNameOrder;
	}

	public SortOrder getPositionOrder() {
		return positionOrder;
	}

	public void setPositionOrder(SortOrder positionOrder) {
		this.positionOrder = positionOrder;
	}

	public SortOrder getRecommentOrder() {
		return recommentOrder;
	}

	public void setRecommentOrder(SortOrder recommentOrder) {
		this.recommentOrder = recommentOrder;
	}

	public SortOrder getHonorOrder() {
		return honorOrder;
	}

	public void setHonorOrder(SortOrder honorOrder) {
		this.honorOrder = honorOrder;
	}

	public SortOrder getSideOrder() {
		return sideOrder;
	}

	public void setSideOrder(SortOrder sideOrder) {
		this.sideOrder = sideOrder;
	}

	public SortOrder getOrderOrder() {
		return orderOrder;
	}

	public void setOrderOrder(SortOrder orderOrder) {
		this.orderOrder = orderOrder;
	}

	public List<InfoSuggestReportModel> getInfoSuggestReportModelList() {
		return infoSuggestReportModelList;
	}

	public void setInfoSuggestReportModelList(
			List<InfoSuggestReportModel> infoSuggestReportModelList) {
		this.infoSuggestReportModelList = infoSuggestReportModelList;
	}

	public SortOrder getRegisDateOrder() {
		return regisDateOrder;
	}

	public void setRegisDateOrder(SortOrder regisDateOrder) {
		this.regisDateOrder = regisDateOrder;
	}

}
