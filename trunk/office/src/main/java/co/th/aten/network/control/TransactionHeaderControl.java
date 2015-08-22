package co.th.aten.network.control;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.MemberCustomer;


public class TransactionHeaderControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463221528014730255L;

	@Inject
	Logger log;

	@Inject
	private TransactionHeaderStore transactionHeaderStore;
	
	public String getAddressByHeaderId(int headerId){
		return transactionHeaderStore.getAddressByHeaderId(headerId);
	}
	
	public List<Object[]> genProductPackgateByMember(MemberCustomer member, Date startDate, Date endDate){
		return transactionHeaderStore.genProductPackageByMember(member, startDate, endDate);
	}
	
	public int myScoreTotal(MemberCustomer member, Date date){
		return transactionHeaderStore.myScoreTotal(member,date);
	}
	
	public int myScoreDate(MemberCustomer member, Date startDate, Date endDate){
		return transactionHeaderStore.myScoreDate(member, startDate, endDate);
	}
	
	public int sumScoreTotal(Date date, String sqlUnder){
		return transactionHeaderStore.sumScoreTotal(date, sqlUnder);
	}
	
	public int sumScoreDate(Date startDate, Date endDate, String sqlUnder){
		return transactionHeaderStore.sumScoreDate(startDate, endDate, sqlUnder);
	}
}
