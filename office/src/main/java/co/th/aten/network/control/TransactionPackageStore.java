package co.th.aten.network.control;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.TransactionScorePackage;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;

public class TransactionPackageStore extends BasicStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7501846161962161896L;

	@Inject
	private Logger log;

	@Inject
	@DBDefault
	private EntityManager em;

	@Inject
	private CurrentUserManager currentUser;
	

	public void insert(TransactionScorePackage trx) {
		em.persist(trx);
	}

	public void delete(TransactionScorePackage trx) {
		em.remove(trx);
	}

	public void insertOrUpdate(TransactionScorePackage trx) {
		TransactionScorePackage chk = em.find(TransactionScorePackage.class, trx.getTransactionScorePackagePK());
		if (chk == null) {
			em.persist(trx);
		} else {	
			em.merge(trx);
		}
	}
}
