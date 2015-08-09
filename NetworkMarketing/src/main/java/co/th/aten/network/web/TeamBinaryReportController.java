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

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.MemberPosition;
import co.th.aten.network.entity.MemberSide;
import co.th.aten.network.model.TeamBinaryReportModel;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

@ViewScoped
@Named
public class TeamBinaryReportController implements Serializable{

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
	private CustomerControl customerControl;
	@Inject
	@DBDefault
	private EntityManager em;

	private SortOrder orderOrder = SortOrder.unsorted;
	private SortOrder memberIdOrder = SortOrder.unsorted;
	private SortOrder memberNameOrder = SortOrder.unsorted;
	private SortOrder positionOrder = SortOrder.unsorted;
	private SortOrder recommentOrder = SortOrder.unsorted;
	private SortOrder honorOrder = SortOrder.unsorted;
	private SortOrder sideOrder = SortOrder.unsorted;

	private List<TeamBinaryReportModel> teamBinaryReportModelList;
	private String searchCustomer;
	private int countLeftSide;
	private int countRightSid;
	private String styleLeft;
	private String styleRight;

	@PostConstruct
	public void init(){
		log.info("init method TeamBinaryReportController");
		teamBinaryReportModelList = new ArrayList<TeamBinaryReportModel>();
		searchCustomer = "";
		countLeftSide = 0;
		countRightSid = 0;
		styleLeft = "";
		styleRight = "";
		report(null);
	}

	public void sortByOrder() {
		log.info("-------> sortByOrder()");
		memberIdOrder = SortOrder.unsorted;
		memberNameOrder = SortOrder.unsorted;
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

	public void report(MemberCustomer memSearch){
		if(memSearch!=null){
			TeamBinaryReportModel model = setDataModel(memSearch,1);
			teamBinaryReportModelList.add(model);
		}else{
			countLeftSide = 0;
			countRightSid = 0;
			String sql = "From MemberCustomer " +
					" Where customerId = "+currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue();
			boolean chk = true;
			int index = 0;
			while(chk){
				log.info("SQL + "+sql);
				String subSql = "";
				List<MemberCustomer> customerList = em.createQuery(sql,MemberCustomer.class).getResultList();
				for(MemberCustomer cus:customerList){
					TeamBinaryReportModel model = setDataModel(cus,++index);
					if(memSearch==null){
						if(cus.getCustomerId().intValue() != currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue()){
							if(cus.getSide()!=null && cus.getSide().intValue()==1){
								countLeftSide++;
							}else if(cus.getSide()!=null && cus.getSide().intValue()==2){
								countRightSid++;
							}
						}
					}
					teamBinaryReportModelList.add(model);
					subSql += (cus.getLowerLeftId()==null?"":cus.getLowerLeftId().intValue()+",");
					subSql += (cus.getLowerRightId()==null?"":cus.getLowerRightId().intValue()+",");
				}
				if(subSql.equals("")){
					chk = false;
					break;
				}
				sql = "From MemberCustomer " +
						" Where customerId in ";
				subSql = subSql.substring(0, subSql.length()-1);
				subSql = "("+subSql+")";
				sql += subSql;
			}
		}
	}

	public void genDataSide(long side){
		styleLeft = "";
		styleRight = "";
		try{
			if(side==1){
				styleLeft = "FONT-SIZE: 20px;BACKGROUND-COLOR: #ff0000;COLOR: #ffffff;";
			}else{
				styleRight = "FONT-SIZE: 20px;BACKGROUND-COLOR: #ff0000;COLOR: #ffffff;";
			}
			teamBinaryReportModelList = new ArrayList<TeamBinaryReportModel>();
			String sql = "From MemberCustomer " +
					" Where customerId = "+currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue();
			boolean chk = true;
			int index = 0;
			while(chk){
				log.info("SQL + "+sql);
				String subSql = "";
				List<MemberCustomer> customerList = em.createQuery(sql,MemberCustomer.class).getResultList();
				for(MemberCustomer cus:customerList){
					if(cus.getCustomerId().intValue() != currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue()){
						if(cus.getSide()!=null && cus.getSide().intValue()==side){
							TeamBinaryReportModel model = setDataModel(cus,++index);
							teamBinaryReportModelList.add(model);
						}
					}
					subSql += (cus.getLowerLeftId()==null?"":cus.getLowerLeftId().intValue()+",");
					subSql += (cus.getLowerRightId()==null?"":cus.getLowerRightId().intValue()+",");
				}
				if(subSql.equals("")){
					chk = false;
					break;
				}
				sql = "From MemberCustomer " +
						" Where customerId in ";
				subSql = subSql.substring(0, subSql.length()-1);
				subSql = "("+subSql+")";
				sql += subSql;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private TeamBinaryReportModel setDataModel(MemberCustomer memSearch, int index){
		TeamBinaryReportModel model = new TeamBinaryReportModel();
		model.setIndex(index);
		model.setCustomerId(StringUtil.n2b(memSearch.getCustomerId()));
		model.setCustomerCode(memSearch.getCustomerMember());
		model.setCustomerName(customerControl.genNameMenber(memSearch));
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
		teamBinaryReportModelList = new ArrayList<TeamBinaryReportModel>();
		styleLeft = "";
		styleRight = "";
		orderOrder = SortOrder.unsorted;
		memberIdOrder = SortOrder.unsorted;
		memberNameOrder = SortOrder.unsorted;
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
							if(StringUtil.n2b(cus.getLowerLeftId())==cusId || StringUtil.n2b(cus.getLowerRightId())==cusId){
								report(memSearch);
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
					report(memSearch);
				}
			}else{
				report(null);
			}
		}catch(Exception e){
			log.info("Error : "+e.getMessage());
		}
		long endTime = System.currentTimeMillis();
		log.info("search() Time = "+((endTime-startTime)/1000d)+"s");
	}

	public List<TeamBinaryReportModel> getTeamBinaryReportModelList() {
		return teamBinaryReportModelList;
	}

	public void setTeamBinaryReportModelList(
			List<TeamBinaryReportModel> teamBinaryReportModelList) {
		this.teamBinaryReportModelList = teamBinaryReportModelList;
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

	public int getCountLeftSide() {
		return countLeftSide;
	}

	public void setCountLeftSide(int countLeftSide) {
		this.countLeftSide = countLeftSide;
	}

	public int getCountRightSid() {
		return countRightSid;
	}

	public void setCountRightSid(int countRightSid) {
		this.countRightSid = countRightSid;
	}

	public String getStyleLeft() {
		return styleLeft;
	}

	public void setStyleLeft(String styleLeft) {
		this.styleLeft = styleLeft;
	}

	public String getStyleRight() {
		return styleRight;
	}

	public void setStyleRight(String styleRight) {
		this.styleRight = styleRight;
	}

}
