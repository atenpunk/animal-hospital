package co.th.aten.network.control;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.solder.logging.Logger;
import co.th.aten.network.entity.TransactionScorePackageWeekly;


public class TransactionScorePackageWeeklyControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463221528014730255L;

	@Inject
	Logger log;

	@Inject
	private TransactionScorePackageWeeklyStore transactionScorePackageWeeklyStore;
	
	public void insert(TransactionScorePackageWeekly trx) {
		transactionScorePackageWeeklyStore.insert(trx);
	}
	
	public void delete(TransactionScorePackageWeekly trx) {
		transactionScorePackageWeeklyStore.delete(trx);
	}
	
	public void insertOrUpdate(TransactionScorePackageWeekly trx) {
		transactionScorePackageWeeklyStore.insertOrUpdate(trx);
	}
}
