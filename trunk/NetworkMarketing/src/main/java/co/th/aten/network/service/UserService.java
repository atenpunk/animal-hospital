package co.th.aten.network.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.UserControl;
import co.th.aten.network.control.UserStore;
import co.th.aten.network.entity.Role;
import co.th.aten.network.entity.User;
import co.th.aten.network.entity.UserRole;
import co.th.aten.network.security.annotation.AdminOrSys;
import co.th.aten.network.util.HashUtil;


@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5105334552629163651L;

	@Inject
	Logger log;

	@Inject
	UserStore store;
	@Inject
	UserControl control;

	//	@Inject
	//	private AuditService auditService;

	//private Users user;

	public User getUser(int userId) {
		return store.getUser(userId);
	}

	public Role getRole(int roleId) {
		return store.getRole(roleId);
	}

	public Role getRole(String name) {
		return store.getRole(name);
	}

	public List<Role> getRoleList() {
		return store.getRoleList();
	}

	public User authenticate(String loginName, String password) {
		log.info("authenticate user");
		HashUtil hashUtil = new HashUtil();
		log.infov("**={0}",hashUtil.hash(password));
		return store.authicate(loginName, hashUtil.hash(password));
	}

	public void updateLoginTime(int userId) {
		store.updateLoginTime(userId);
	}

	public List<UserRole> getUserRole(int userId) {
		//log.infov("getUserRole,userId={0}",userId);
		return store.getUserRole(userId);
	}

	@AdminOrSys
	public List<User> getUsersList(String searchString) {
		return control.getUsersList(searchString);
	}

	public void register(User user, List<UserRole> roles) {
		store.create(user, roles);
	}
	
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@AdminOrSys
	public void create(User user, List<UserRole> roles) {
		HashUtil hashUtil = new HashUtil();
		user.setPassword(hashUtil.hash(user.getLoginName()));
		store.create(user, roles);
	}

//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@AdminOrSys
	public void update(User user, List<UserRole> roles) throws Exception {
		store.update(user, roles);
	}

//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@AdminOrSys
	public void delete(int userId) {
		store.remove(userId);
	}

//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@AdminOrSys
	public void disable(int userId) {
		store.disable(userId);
	}

//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@AdminOrSys
	public void enable(int userId) {
		store.enable(userId);
	}

	@AdminOrSys
	public void resetPassword(int userId){
		store.resetPassword(userId);
	}
	
	public boolean checkLoginName(int userId,String loginName){
		return store.checkLoginName(userId,loginName);
	}
	
	
	public boolean checkOldPassword(int userId, String oldPassword) {
		HashUtil hashUtil = new HashUtil();
		return store.checkOldPassword(userId, hashUtil.hash(oldPassword));
	}

	public boolean changePassword(int userId, String newPassword) {
		HashUtil hashUtil = new HashUtil();
		return store.changePassword(userId, hashUtil.hash(newPassword));
	}

	
	public boolean validate(User user){
		return control.validate(user);
	}
	
}
