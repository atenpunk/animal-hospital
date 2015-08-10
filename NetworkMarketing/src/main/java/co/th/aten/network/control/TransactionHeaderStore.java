package co.th.aten.network.control;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.AddressAmphures;
import co.th.aten.network.entity.AddressDistricts;
import co.th.aten.network.entity.AddressProvinces;
import co.th.aten.network.entity.TransactionSellHeader;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

public class TransactionHeaderStore extends BasicStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7501846161962161896L;

	@Inject
	private Logger log;

	@Inject
	@DBDefault
	private EntityManager em;

	@Inject
	private CurrentUserManager currentUser;
	
	public String getAddressByHeaderId(int headerId){
		try{
			TransactionSellHeader trx = em.find(TransactionSellHeader.class, new Integer(headerId));
			if (trx!=null) {
				String address = "";
				address += StringUtil.n2b(trx.getAddressNo());
				address += " "+StringUtil.n2b(trx.getAddressBuilding());
				address += " "+StringUtil.n2b(trx.getAddressVillage());
				address += " "+StringUtil.n2b(trx.getAddressLane());
				address += " "+StringUtil.n2b(trx.getAddressRoad());
				if(trx.getProvinceId()!=null 
						&& trx.getProvinceId().intValue()>0){
					AddressProvinces addr  = em.find(AddressProvinces.class, trx.getProvinceId());
					if(addr!=null){
						address += " จ."+StringUtil.n2b(addr.getProvinceName());
					}
				}
				if(trx.getAmphurId()!=null 
						&& trx.getAmphurId().intValue()>0){
					AddressAmphures addr  = em.find(AddressAmphures.class, trx.getAmphurId());
					if(addr!=null){
						address += " อ."+StringUtil.n2b(addr.getAmphurName());
					}
				}
				if(trx.getDistrictId()!=null 
						&& trx.getDistrictId().intValue()>0){
					AddressDistricts addr  = em.find(AddressDistricts.class, trx.getDistrictId());
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
}
