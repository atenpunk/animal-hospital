package co.th.aten.network.control;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.TransactionScoreMatching;


public class TransactionScoreMatchingControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463221528014730255L;

	@Inject
	Logger log;

	@Inject
	private TransactionScoreMatchingStore transactionScoreMatchingStore;
	
	public Integer getMaxRoundId() {
		return transactionScoreMatchingStore.getMaxRoundId();
	}
	
	public int sumMathing(MemberCustomer member, Date date){
		return transactionScoreMatchingStore.sumMathing(member, date);
	}
	
	public void insert(TransactionScoreMatching trx) {
		transactionScoreMatchingStore.insert(trx);
	}
	
	public void delete(TransactionScoreMatching trx) {
		transactionScoreMatchingStore.delete(trx);
	}
	
	public void insertOrUpdate(TransactionScoreMatching trx) {
		transactionScoreMatchingStore.insertOrUpdate(trx);
	}
	
}
