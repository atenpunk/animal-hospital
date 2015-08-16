package co.th.aten.network.control;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.solder.logging.Logger;

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
