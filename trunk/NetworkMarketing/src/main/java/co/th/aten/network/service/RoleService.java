package co.th.aten.network.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.RoleStore;
import co.th.aten.network.entity.Role;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RoleService implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2250171386309176322L;

	@Inject
	private Logger log;
	
	@Inject
	private RoleStore roleStore;
	
	public List<Role> getRoleList(){
		return roleStore.getList();
	}
	
	public Role getRole(int roleId){
		return roleStore.getOne(roleId);
	}
}
