package co.th.network.scheduler;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.producer.DBDefault;

@Stateless
public class ScheduledScoreWeeklyProcessor {
	
	@Inject
	@DBDefault
	private EntityManager em;
	
	@Inject
	Logger log;
 
//	@Schedule(hour="*",minute = "*",second="0/10")
    @Schedule(dayOfWeek="Mon",hour="03",minute = "01",second="01")
    public void execute() {
		long startTime = System.currentTimeMillis();
    	try{
    		log.info("##################################################");
			log.info("##### Start Scheduled Score Weekly Processor #####");
    		log.info("##################################################");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		log.info("Scheduled Score Weekly Processor Time = "+((endTime-startTime)/1000d)+"s");
    }
}  