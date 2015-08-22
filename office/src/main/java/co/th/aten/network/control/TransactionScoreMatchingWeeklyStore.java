package co.th.aten.network.control;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.TransactionScoreMatchingWeekly;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

public class TransactionScoreMatchingWeeklyStore extends BasicStore implements Serializable {

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

	public int sumMathing(MemberCustomer member, Date date){
		try{
			if(member !=null){
				String sql = "select sum(matchingPv) " +
						" from TransactionScoreMatchingWeekly " +
						" where transactionScoreMatchingWeeklyPK.customerId = :memberId " +
						" and transactionScoreMatchingWeeklyPK.trxMatchingDate < :dateTime ";
				Calendar calStart = Calendar.getInstance();
				calStart.setTime(date);
				calStart.set(Calendar.HOUR_OF_DAY, 0);
				calStart.set(Calendar.MINUTE, 0);
				calStart.set(Calendar.SECOND, 0);
				calStart.set(Calendar.MILLISECOND, 0);
				try{
					BigDecimal score = (BigDecimal)em.createQuery(sql)
							.setParameter("memberId", member.getCustomerId())
							.setParameter("dateTime", calStart.getTime())
							.getSingleResult();
					return StringUtil.n2b(score).intValue();
				}catch(Exception ex){
					log.info("sumMathing error : "+ex.getMessage());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	public Integer getMaxRoundId() {
		log.debug("find getMaxRoundId");
		try{
			Integer max = (Integer)em.createQuery("Select max(transactionScoreMatchingWeeklyPK.roundId) From TransactionScoreMatchingWeekly")
					.getSingleResult();
			if (max!=null) {
				return max;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	public void insert(TransactionScoreMatchingWeekly trx) {
		em.persist(trx);
	}

	public void delete(TransactionScoreMatchingWeekly trx) {
		em.remove(trx);
	}

	public void insertOrUpdate(TransactionScoreMatchingWeekly trx) {
		TransactionScoreMatchingWeekly chk = em.find(TransactionScoreMatchingWeekly.class, trx.getTransactionScoreMatchingWeeklyPK());
		if (chk == null) {
			em.persist(trx);
		} else {	
			em.merge(trx);
		}
	}
}
