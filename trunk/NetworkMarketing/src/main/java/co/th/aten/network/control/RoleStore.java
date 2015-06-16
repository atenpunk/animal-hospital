package co.th.aten.network.control;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.Role;
import co.th.aten.network.producer.DBDefault;

public class RoleStore extends BasicStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7501846161962161895L;

	@Inject
	private Logger log;

	@Inject
	@DBDefault
	private EntityManager em;

	public List<Role> getList() {
		log.info("get role list order by roleId");
		return em.createQuery("From Role order by roleId ", Role.class).getResultList();
	}

	public Role getOne(int roleId) {
		try {
			return em.createNamedQuery("Role.findByRoleId", Role.class)
					.setParameter("roleId", roleId).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void insert(Role role) {
		em.persist(role);
	}

	public void insertOrUpdate(Role role) {
		Role sb = em.find(Role.class, role.getRoleId());
		if (sb == null) {
			em.persist(role);
		} else {
			em.merge(role);
		}
	}
}
