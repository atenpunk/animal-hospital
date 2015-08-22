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
	
	public List<Object[]> genProductPackgateByMember(MemberCustomer member, Date date){
		return transactionHeaderStore.genProductPackageByMember(member, date);
	}
	
	public int myScoreTotal(MemberCustomer member, Date date){
		return transactionHeaderStore.myScoreTotal(member,date);
	}
	
	public int myScoreDate(MemberCustomer member, Date date){
		return transactionHeaderStore.myScoreDate(member,date);
	}
	
	public int sumScoreTotal(Date date, String sqlUnder){
		return transactionHeaderStore.sumScoreTotal(date, sqlUnder);
	}
	
	public int sumScoreDate(Date date, String sqlUnder){
		return transactionHeaderStore.sumScoreDate(date, sqlUnder);
	}
}
