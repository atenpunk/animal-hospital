package co.th.aten.network.control;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.solder.logging.Logger;


public class StockProductControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463221528014730255L;

	@Inject
	Logger log;

	@Inject
	private StockProductStore stockProductStore;
	
	public int productIdInsert(){
		Integer max = stockProductStore.getMaxProductId();
		if(max==null){
			return 1;
		}else{
			return (max.intValue()+1);
		}
	}
	
}
