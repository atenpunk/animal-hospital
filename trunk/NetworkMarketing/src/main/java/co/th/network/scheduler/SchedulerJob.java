package co.th.network.scheduler;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.producer.DBDefault;

public class SchedulerJob implements Job, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SchedulerJob.class.getName());

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try{
			log.info("##### Start SchedulerJob #####");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}