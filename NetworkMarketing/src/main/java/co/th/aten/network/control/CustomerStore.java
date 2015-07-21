package co.th.aten.network.control;

import java.io.Serializable;

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
			MemberCustomer memberCustomer = em.find(MemberCustomer.class, memberId);
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
						address += " "+StringUtil.n2b(addr.getProvinceName());
					}
				}
				if(memberCustomer.getAmphurIdSendDoc()!=null 
						&& memberCustomer.getAmphurIdSendDoc().intValue()>0){
					AddressAmphures addr  = em.find(AddressAmphures.class, memberCustomer.getAmphurIdSendDoc());
					if(addr!=null){
						address += " "+StringUtil.n2b(addr.getAmphurName());
					}
				}
				if(memberCustomer.getDistrictIdSendDoc()!=null 
						&& memberCustomer.getDistrictIdSendDoc().intValue()>0){
					AddressDistricts addr  = em.find(AddressDistricts.class, memberCustomer.getDistrictIdSendDoc());
					if(addr!=null){
						address += " "+StringUtil.n2b(addr.getDistrictName());
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

	public void refresh(MemberCustomer customer) {
		em.refresh(customer);
	}
}
