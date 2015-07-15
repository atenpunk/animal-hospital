package co.th.aten.network.control;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.solder.logging.Logger;


public class StockCategoryControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463221528014730255L;

	@Inject
	Logger log;

	@Inject
	private StockCategoryStore stockCategoryStore;
	
	public int catalogIdInsert(){
		Integer max = stockCategoryStore.getMaxCatalogId();
		if(max==null){
			return 1;
		}else{
			return (max.intValue()+1);
		}
	}
	
}
