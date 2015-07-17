package co.th.aten.network.control;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.solder.logging.Logger;


public class TransactionReceiptControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463221528014730255L;

	@Inject
	Logger log;

	@Inject
	private TransactionReceiptStore transactionReceiptStore;
	
	public String generateReceiptNo(){
		return transactionReceiptStore.generateReceiptNo();
	}
	
}
