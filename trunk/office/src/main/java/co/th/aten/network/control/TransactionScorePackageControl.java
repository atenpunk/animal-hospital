package co.th.aten.network.control;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.TransactionScorePackage;


public class TransactionScorePackageControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463221528014730255L;

	@Inject
	Logger log;

	@Inject
	private TransactionScorePackageStore transactionScorePackageStore;
	
	public void insert(TransactionScorePackage trx) {
		transactionScorePackageStore.insert(trx);
	}
	
	public void delete(TransactionScorePackage trx) {
		transactionScorePackageStore.delete(trx);
	}
	
	public void insertOrUpdate(TransactionScorePackage trx) {
		transactionScorePackageStore.insertOrUpdate(trx);
	}
}
