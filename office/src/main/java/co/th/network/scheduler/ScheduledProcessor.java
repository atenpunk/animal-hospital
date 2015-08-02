package co.th.network.scheduler;

import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.producer.DBDefault;

@Stateless
public class ScheduledProcessor {
	
	@Inject
	@DBDefault
	private EntityManager em;
	
	@Inject
	Logger log;
 
//	@Schedule(hour="*",minute = "*",second="0/10")
    @Schedule(hour="00",minute = "00",second="01")
    public void execute() {
    	try{
			log.info("##### Start ScheduledProcessor #####");

//			String sql = "From MemberCustomer ";
//			log.info("SQL = "+sql);
//			List<MemberCustomer> customerList = em.createQuery(sql,MemberCustomer.class)
//					.getResultList();
//			int index = 0;
//			for(MemberCustomer cus:customerList){
//				log.info("INDEX = "+(++index));
//				log.info("cus.getCustomerId()     = "+cus.getCustomerId());
//				log.info("cus.getCustomerMember() = "+cus.getCustomerMember());
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
    }
}  