package co.th.aten.network.control;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.producer.DBDefault;

public class StockProductStore extends BasicStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7501846161962161896L;

	@Inject
	private Logger log;

	@Inject
	@DBDefault
	private EntityManager em;

	public Integer getMaxProductId() {
		log.debug("find getMaxProductId");
		try{
			Integer max = (Integer)em.createQuery("Select max(productId) From StockProduct")
					.getSingleResult();
			if (max!=null) {
				return max;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public void refresh(MemberCustomer customer) {
		em.refresh(customer);
	}
}
