package co.th.aten.network.control;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.TransactionScoreMatchingWeekly;


public class TransactionScoreMatchingWeeklyControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463221528014730255L;

	@Inject
	Logger log;
	@Inject
	private TransactionScoreMatchingWeeklyStore transactionScoreMatchingWeeklyStore;
	
	public Integer getMaxRoundId() {
		return transactionScoreMatchingWeeklyStore.getMaxRoundId();
	}
	
	public int sumMathing(MemberCustomer member, Date date){
		return transactionScoreMatchingWeeklyStore.sumMathing(member, date);
	}
	
	public void insert(TransactionScoreMatchingWeekly trx) {
		transactionScoreMatchingWeeklyStore.insert(trx);
	}
	
	public void delete(TransactionScoreMatchingWeekly trx) {
		transactionScoreMatchingWeeklyStore.delete(trx);
	}
	
	public void insertOrUpdate(TransactionScoreMatchingWeekly trx) {
		transactionScoreMatchingWeeklyStore.insertOrUpdate(trx);
	}
	
}
