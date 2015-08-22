package co.th.aten.network.control;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.TransactionScorePackage;


public class TransactionPackageControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463221528014730255L;

	@Inject
	Logger log;

	@Inject
	private TransactionPackageStore transactionPackageStore;
	
	public void insert(TransactionScorePackage trx) {
		transactionPackageStore.insert(trx);
	}
	
	public void delete(TransactionScorePackage trx) {
		transactionPackageStore.delete(trx);
	}
	
	public void insertOrUpdate(TransactionScorePackage trx) {
		transactionPackageStore.insertOrUpdate(trx);
	}
}
