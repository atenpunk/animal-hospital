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
	@DBDefault
	private EntityManager em;

	private List<TeamBinaryReportModel> teamBinaryReportModelList;
	private String searchCustomer;

	@PostConstruct
	public void init(){
		log.info("init method TeamBinaryReportController");
		teamBinaryReportModelList = new ArrayList<TeamBinaryReportModel>();
		report(null);
	}

	public void report(MemberCustomer memSearch){
		if(memSearch!=null){
			TeamBinaryReportModel model = setDataModel(memSearch,1);
			teamBinaryReportModelList.add(model);
		}else{
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

	private TeamBinaryReportModel setDataModel(MemberCustomer memSearch, int index){
		TeamBinaryReportModel model = new TeamBinaryReportModel();
		model.setIndex(index);
		model.setCustomerId(memSearch.getCustomerMember());
		model.setCustomerName(memSearch.getFirstName());
		if(memSearch.getPositionId()!=null && memSearch.getPositionId().intValue()!=0){
			MemberPosition position = em.find(MemberPosition.class, memSearch.getPositionId());
			model.setPosition(StringUtil.n2b(position.getEnName()));
		}	
		model.setRecomment("");
		model.setHonor("");
		if(memSearch.getSide()!=null){
			MemberSide memberSide = em.find(MemberSide.class, memSearch.getSide());
			model.setSide(memberSide!=null?memberSide.getThName():"");
		}
		return model;
	}

	public void search(){
		long startTime = System.currentTimeMillis();
		log.info("##### SEARCH ##### = "+searchCustomer);
		teamBinaryReportModelList = new ArrayList<TeamBinaryReportModel>();
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
				log.info("##### login ##### = "+currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue());
				if(cusId >= currentUser.getCurrentAccount().getCustomerId().getCustomerId().intValue()){
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

}
