package co.th.aten.network.control;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.AddressAmphures;
import co.th.aten.network.entity.AddressDistricts;
import co.th.aten.network.entity.AddressProvinces;
import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.util.StringUtil;

public class CustomerStore extends BasicStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7501846161962161896L;

	@Inject
	private Logger log;

	@Inject
	@DBDefault
	private EntityManager em;

	public Integer getMaxCustomerId() {
		log.debug("find getMaxCustomerId");
		try{
			Integer max = (Integer)em.createQuery("Select max(customerId) From MemberCustomer")
					.getSingleResult();
			if (max!=null) {
				return max;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String getAddressByMemberId(int memberId){
		try{
			MemberCustomer memberCustomer = em.find(MemberCustomer.class, new Integer(memberId));
			if (memberCustomer!=null) {
				String address = "";
				address += StringUtil.n2b(memberCustomer.getAddressNoSendDoc());
				address += " "+StringUtil.n2b(memberCustomer.getAddressBuildingSendDoc());
				address += " "+StringUtil.n2b(memberCustomer.getAddressVillageSendDoc());
				address += " "+StringUtil.n2b(memberCustomer.getAddressLaneSendDoc());
				address += " "+StringUtil.n2b(memberCustomer.getAddressRoadSendDoc());
				if(memberCustomer.getProvinceIdSendDoc()!=null 
						&& memberCustomer.getProvinceIdSendDoc().intValue()>0){
					AddressProvinces addr  = em.find(AddressProvinces.class, memberCustomer.getProvinceIdSendDoc());
					if(addr!=null){
						address += " จ."+StringUtil.n2b(addr.getProvinceName());
					}
				}
				if(memberCustomer.getAmphurIdSendDoc()!=null 
						&& memberCustomer.getAmphurIdSendDoc().intValue()>0){
					AddressAmphures addr  = em.find(AddressAmphures.class, memberCustomer.getAmphurIdSendDoc());
					if(addr!=null){
						address += " อ."+StringUtil.n2b(addr.getAmphurName());
					}
				}
				if(memberCustomer.getDistrictIdSendDoc()!=null 
						&& memberCustomer.getDistrictIdSendDoc().intValue()>0){
					AddressDistricts addr  = em.find(AddressDistricts.class, memberCustomer.getDistrictIdSendDoc());
					if(addr!=null){
						address += " ต."+StringUtil.n2b(addr.getDistrictName());
						address += " "+StringUtil.n2b(addr.getPostCode());
					}
				}
				return address;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}

	public String genSqlUnder(int memberId){
		try{
			if(memberId!=0){
				String sqlMemberId = memberId+",";
				String sql = "From MemberCustomer " +
						" Where customerId = "+ memberId;
				boolean chk = true;
				while(chk){
					String subSql = "";
					List<MemberCustomer> customerList = em.createQuery(sql,MemberCustomer.class).getResultList();
					for(MemberCustomer cus:customerList){
						subSql += (cus.getLowerLeftId()==null?"":cus.getLowerLeftId().intValue()+",");
						subSql += (cus.getLowerRightId()==null?"":cus.getLowerRightId().intValue()+",");
						sqlMemberId += subSql;
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
				sqlMemberId = sqlMemberId.substring(0, sqlMemberId.length()-1);
				sqlMemberId = "("+sqlMemberId+")";
				return sqlMemberId;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "(0)";
	}

	public void refresh(MemberCustomer customer) {
		em.refresh(customer);
	}

	public void insert(MemberCustomer member) {
		em.persist(member);
	}

	public void delete(MemberCustomer member) {
		em.remove(member);
	}

	public void insertOrUpdate(MemberCustomer member) {
		MemberCustomer chk = em.find(MemberCustomer.class, member.getCustomerId());
		if (chk == null) {
			em.persist(member);
		} else {	
			em.merge(member);
		}
	}
}
