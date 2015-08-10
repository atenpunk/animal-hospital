package co.th.aten.network.control;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.solder.logging.Logger;


public class TransactionHeaderControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463221528014730255L;

	@Inject
	Logger log;

	@Inject
	private TransactionHeaderStore transactionHeaderStore;
	
	public String getAddressByHeaderId(int headerId){
		return transactionHeaderStore.getAddressByHeaderId(headerId);
	}
}
