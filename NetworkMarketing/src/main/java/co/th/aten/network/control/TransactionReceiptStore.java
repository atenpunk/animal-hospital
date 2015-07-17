package co.th.aten.network.control;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.TransactionReceipt;
import co.th.aten.network.producer.DBDefault;

public class TransactionReceiptStore extends BasicStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7501846161962161896L;

	@Inject
	private Logger log;

	@Inject
	@DBDefault
	private EntityManager em;

	public String generateReceiptNo(){
		DecimalFormat df = new DecimalFormat("00000");
		SimpleDateFormat sdf = new SimpleDateFormat("yyMM",Locale.US);
		String receiptNo = "";
		List<TransactionReceipt> receiptList = em
		.createQuery("From TransactionReceipt where receiptId = 1 ",TransactionReceipt.class).getResultList();
		if (receiptList.size() > 0) {
			TransactionReceipt rec = receiptList.get(0);
			// check for reset each month
			Calendar toDay = Calendar.getInstance();
			Calendar lastReset = Calendar.getInstance();
			lastReset.setTime(rec.getLastReset());
			if (toDay.get(Calendar.MONTH) != lastReset.get(Calendar.MONTH)) {
				rec.setLastReset(new Date());
				rec.setReceiptRunning(0);
			}
			receiptNo += "ONLINE";
			receiptNo += sdf.format(new Date());
			rec.setReceiptRunning((rec.getReceiptRunning() + 1));
			receiptNo += "-"+df.format(rec.getReceiptRunning());
			em.merge(rec);
		}
		return receiptNo;
	}
}
