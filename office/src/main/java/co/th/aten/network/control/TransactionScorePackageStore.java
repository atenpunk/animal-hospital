package co.th.aten.network.control;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.TransactionScorePackage;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;

public class TransactionScorePackageStore extends BasicStore implements Serializable {

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
	
	public void delete(Date date, Integer suggestId) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
		em.createQuery("Delete From TransactionScorePackage " +
				" Where transactionScorePackagePK.suggestId =:suggestId " +
				" And transactionScorePackagePK.trxPackageDate = DATE_FORMAT('"+sdfDate.format(date)+"','%Y-%m-%d') ")
				.setParameter("suggestId", suggestId)
				.executeUpdate();
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
