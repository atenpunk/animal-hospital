package co.th.aten.network.control;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.Role;
import co.th.aten.network.entity.User;
import co.th.aten.network.entity.UserRole;
import co.th.aten.network.producer.DBDefault;

public class UserStore extends BasicStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7974450769728194068L;

	@Inject
	private Logger log;

	@Inject
	@DBDefault
	private EntityManager em;

	public User authicate(String loginName, String password) {
		log.info("authicate user");
		User user = getUser(loginName);
		if (user != null) {			
			if (user.getPassword().equals(password)) {
				log.infov("login success,{0}", user.getLoginName());
				updateLoginTime(user.getUserId());				
				refresh(user);
				log.info("user first name="+user.getUserName());
				//auditService.auditLogin(user.getUserid(), "SUCCESS");
				return user;
			} else {
				log.infov("login fail,{0}", user.getLoginName());
//				updateLoginFail(user.getUserid(), user.getInvalidpwcount());
				//user = userStore.getUserById(user.getUserid());
				//auditService.auditLogin(user.getUserid(), "FAIL("+user.getInvalidpwcount()+")");
				return null;
			}
		}

		return null;
	}

	public List<User> getUsersList(String searchString) {
		String orderBy = "loginName";
		return em.createQuery("From User where " +
				" (loginName like :search " +
				" or userName like :search " +
				"  )" +
				" and status = 0 order by " + buildOrderBy(orderBy), User.class)
				.setParameter("search", buildSearchString(searchString))
				.getResultList();
	}

	public User getUser(int userId) {
		log.debug("find users where userId=" + userId);
		List<User> users = em.createQuery("From User where userid=:userId ", User.class)
				.setParameter("userId", userId)
				.getResultList();
		if (users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	public User getUser(String loginName) {
		log.debug("find users where loginname=" + loginName);
		List<User> users = em.createQuery("From User where loginName=:login  and status = 0 ", User.class)
				.setParameter("login", loginName)
				.getResultList();
		if (users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	public User getUserById(int userId) {
		log.debug("find user where userId=" + userId);
		return em.createQuery("From User where userId=:userId and status = 0 ", User.class)
				.setParameter("userId", userId)
				.getSingleResult();
	}

	public User getUserByIdAll(int userId) {
		log.debug("find users where userId=" + userId);
		return em.createQuery("From User where userId=:userId ", User.class)
				.setParameter("userId", userId)
				.getSingleResult();
	}

	public void updateLoginTime(int userId) {
		em.createQuery("update User set lastLogin = :lastTimeLogin where userId = :userId ")
				.setParameter("lastTimeLogin", new Date(), TemporalType.TIMESTAMP)
				.setParameter("userId", userId)
				.executeUpdate();
	}

//	public void updateLoginFail(int userId, int invalidCount) {
//		//		user.setInvalidpwcount((short)(user.getInvalidpwcount()+1));
//		//		user.setLastinvalidpwd(new Date());		
//		em.createQuery(
//				"update User set lastinvalidpwd = :lastInvalidPwd , invalidpwcount = :invalidCount where userid = :userId ")
//				.setParameter("lastInvalidPwd", new Date(), TemporalType.TIMESTAMP)
//				.setParameter("invalidCount", (short) (invalidCount + 1))
//				.setParameter("userId", userId)
//				.executeUpdate();
//	}

	@SuppressWarnings("unchecked")
	public List<UserRole> getUserRole(int userId) {
		return em.createQuery("From UserRole where userRolePK.userId=:userId order by role.name")
				.setParameter("userId", userId)
				.getResultList();
	}

	public Role getRole(int roleId) {
		return em.find(Role.class, roleId);
	}

	public Role getRole(String name) {
		List<Role> roles = em.createQuery("from Role where name = :name order by name", Role.class)
				.setParameter("name", name)
				.getResultList();
		if (roles.size() > 0) {
			return roles.get(0);
		}
		return null;
	}

	public List<Role> getRoleList() {
		return em.createNamedQuery("Role.findAll", Role.class).getResultList();
	}

	public void refresh(User user) {
		em.refresh(user);
	}

	public void create(User user, List<UserRole> roles) {
		user.setStatus(0);

		//cleanPostOffice(user);
		//set default password as login name
//		user.setPassword();

		em.persist(user);
		if (roles != null && roles.size() > 0) {
			for (UserRole r : roles) {
				r.getUserRolePK().setUserId(user.getUserId());
				em.persist(r);
			}
		} else {
			addDefaultRole(user.getUserId());
		}

	}

	private void addDefaultRole(int userId) {
		UserRole ur = new UserRole(userId, 57);
		em.persist(ur);
	}

	public void update(User user, List<UserRole> roles) {
		log.info("update user---");
		log.info("user id=" + user.getUserId());
		log.info("login name=" + user.getLoginName());

		if (roles != null && roles.size() > 0) {
			List<UserRole> currentRoles = getUserRole(user.getUserId());
			log.info("current roles,size=" + currentRoles.size());
			//remove 
			for (UserRole ur : currentRoles) {
				log.info("check role=" + ur.toString());
				log.info("roles contains=" + roles.contains(ur));
				if (!roles.contains(ur)) {
					log.info("remove role=" + ur.toString());
					if (ur.getUserRolePK().getUserId() != 1 && ur.getUserRolePK().getRoleId() != 1) {
						em.remove(ur);
					}
				}
			}
			//add
			for (UserRole ur : roles) {
				log.info("check for add role=" + ur.toString());
				if (!currentRoles.contains(ur)) {
					em.persist(ur);
				}
			}
		} else {
			addDefaultRole(user.getUserId());
		}

		log.info("-----------");
	}


	public void updateUserRole(User user) {

	}

	public void remove(int userId) {
		if (userId != 1) {
			int rows = em.createQuery("update User set status = 2 where userid = :userId")
					.setParameter("userId", userId)
					.executeUpdate();
			log.infov("set iscancel ,userId={0},updated={1}", userId, rows);
		}
	}

	public void disable(int userId) {
		if (userId != 1) {
			int rows = em.createQuery("update User set status = 1 where userId = :userId")
					.setParameter("userId", userId)
					.executeUpdate();
			log.infov("set isdisable ,userId={0},updated={1}", userId, rows);
		}
	}

	public void enable(int userId) {
		int rows = em.createQuery("update User set status = 0 where userId = :userId")
				.setParameter("userId", userId)
				.executeUpdate();
		log.infov("unset isdisable ,userId={0},updated={1}", userId, rows);
	}

	public boolean checkOldPassword(int userId, String oldPassword) {
		User user = em.find(User.class, userId);
		if (user != null) {
			if (user.getPassword().equals(oldPassword)) {
				return true;
			}
		}
		return false;
	}

	public boolean changePassword(int userId, String newPassword) {
		int row = em.createQuery("update User set password = :hpasswd ," +
				" forceChange = :forceChange where userId = :userId")
				.setParameter("hpasswd", newPassword)
				.setParameter("userId", userId)
				.setParameter("forceChange", (short) 0)
				.executeUpdate();
		log.infov("change password,userId={0},success={1}", userId, row);
		if (row == 1) {
			return true;
		}
		return false;
	}

	public void forceChange(int userId) {
		int row = em.createQuery("update User set forceChange = :forceChange where userId = :userId")
				.setParameter("forceChange", (short) 1)
				.setParameter("userId", userId)
				.executeUpdate();
		log.infov("forceChange,userId={0},success={1}", userId, row);
	}

	public void resetPassword(int userId) {
		User user = em.find(User.class, userId);
		if (user != null) {
			changePassword(userId, user.getLoginName());
			forceChange(userId);
		}
	}

	public boolean checkLoginName(int userId, String loginName) {
		int count = 0;
		if (userId != 0) {
			count = em.createQuery("select count(*) from User where loginName = :loginName " +
					" and userId <> :userId " +
					" and status = :cancel ", Long.class)
					.setParameter("loginName", loginName)
					.setParameter("userId", userId)
					.setParameter("cancel", (short) 0)
					.getSingleResult().intValue();
		} else {
			count = em.createQuery("select count(*) from User where loginName = :loginName " +
					" and status = :cancel ", Long.class)
					.setParameter("loginName", loginName)
					.setParameter("cancel", (short) 0)
					.getSingleResult().intValue();
		}
		if (count > 0) {
			return false;
		}
		return true;
	}

}
