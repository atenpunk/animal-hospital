package co.th.aten.network.control;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.AddressAmphures;
import co.th.aten.network.entity.AddressDistricts;
import co.th.aten.network.entity.AddressProvinces;
import co.th.aten.network.entity.MemberCustomer;
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

	public List<Object[]> genProductPackageByMember(MemberCustomer member, Date startDate, Date endDate){
		try{
			String sql = "Select th.trxHeaderId, th.customerId, sp.productId, sp.pv " +
					" From TransactionSellHeader th, TransactionSellDetail td, StockProduct sp " +
					" Where th.trxHeaderDatetime between :startDate And :endDate " +
					" And th.customerId = :customerId " +
					" And th.trxHeaderStatus <> 99 " +// 99 = cancel
					" And th.trxHeaderId = td.trxHeaderId " +
					" And td.productId = sp.productId " +
					" And sp.productType = 2 "; // 2 = package
			List<Object[]> objectList = em.createQuery(sql,Object[].class)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("customerId", member.getCustomerId())
					.getResultList();
			return objectList;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public int myScoreTotal(MemberCustomer member, Date date){
		try{
			if(member !=null){
				String sql = "select sum(totalPv) " +
						" from TransactionSellHeader " +
						" where customerId =:memberId " +
						" and trxHeaderStatus <> 99 " +
						" and trxHeaderDatetime < :dateTime ";
				Calendar calStart = Calendar.getInstance();
				calStart.setTime(date);
				calStart.set(Calendar.HOUR_OF_DAY, 23);
				calStart.set(Calendar.MINUTE, 59);
				calStart.set(Calendar.SECOND, 59);
				calStart.set(Calendar.MILLISECOND, 999);
				try{
					BigDecimal score = (BigDecimal)em.createQuery(sql)
							.setParameter("memberId", member.getCustomerId())
							.setParameter("dateTime", calStart.getTime())
							.getSingleResult();
					return StringUtil.n2b(score).intValue();
				}catch(Exception ex){
					log.info("myScoreTotal error : "+ex.getMessage());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	public int myScoreDate(MemberCustomer member, Date startDate, Date endDate){
		try{
			if(member !=null){
				String sql = "select sum(totalPv) " +
						" from TransactionSellHeader " +
						" where customerId =:memberId " +
						" and trxHeaderStatus <> 99 " +
						" and trxHeaderDatetime between :startDate and :endDate ";
				try{
					BigDecimal score = (BigDecimal)em.createQuery(sql)
							.setParameter("memberId", member.getCustomerId())
							.setParameter("startDate", startDate)
							.setParameter("endDate", endDate)
							.getSingleResult();
					return StringUtil.n2b(score).intValue();
				}catch(Exception ex){
					log.info("myScoreTotal error : "+ex.getMessage());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}


	public int sumScoreTotal(Date date, String sqlMemberUnder){
		try{
			String sqlSum = "select sum(totalPv) " +
					" from TransactionSellHeader " +
					" where customerId in " + sqlMemberUnder +
					" and trxHeaderStatus <> 99 "+
					" and trxHeaderDatetime < :dateTime ";
			Calendar calStart = Calendar.getInstance();
			calStart.setTime(date);
			calStart.set(Calendar.HOUR_OF_DAY, 0);
			calStart.set(Calendar.MINUTE, 0);
			calStart.set(Calendar.SECOND, 0);
			calStart.set(Calendar.MILLISECOND, 0);
			try{
				BigDecimal score = (BigDecimal)em.createQuery(sqlSum)
						.setParameter("dateTime", calStart.getTime())
						.getSingleResult();
				return StringUtil.n2b(score).intValue();
			}catch(Exception ex){
				log.info("sumScoreTotal error : "+ex.getMessage());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	public int sumScoreDate(Date startDate, Date endDate, String sqlMemberUnder){
		try{
			String sqlSum = "select sum(totalPv) " +
					" from TransactionSellHeader " +
					" where customerId in " + sqlMemberUnder +
					" and trxHeaderStatus <> 99 " +
					" and trxHeaderDatetime between :startDate and :endDate ";
			try{
				BigDecimal score = (BigDecimal)em.createQuery(sqlSum)
						.setParameter("startDate", startDate)
						.setParameter("endDate", endDate)
						.getSingleResult();
				return StringUtil.n2b(score).intValue();
			}catch(Exception ex){
				log.info("sumScoreDate error : "+ex.getMessage());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
}
