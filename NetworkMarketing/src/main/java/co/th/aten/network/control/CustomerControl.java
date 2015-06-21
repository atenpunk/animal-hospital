package co.th.aten.network.control;

import java.io.Serializable;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.seam.security.Identity;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.security.Restrictions;
import co.th.aten.network.security.annotation.Authenticated;


public class CustomerControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463221528014730255L;

	@Inject
	Logger log;

	@Inject
	private CustomerStore customerStore;


	@Inject
	@Authenticated
	private Instance<UserLogin> userLogin;

	@Inject
	Identity identity;
	@Inject
	Restrictions restrictions;
	
	public int customerIdInsert(){
		Integer max = customerStore.getMaxCustomerId();
		if(max==null){
			return 1;
		}else{
			return (max.intValue()+1);
		}
	}
	
}
