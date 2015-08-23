package co.th.aten.network.model;

import java.io.Serializable;
import java.util.Date;

public class CalculateDailyModel implements Serializable {
		
	private int roundId;
	private Date dateCalculate;
	private Date datePayment;
	
	public int getRoundId() {
		return roundId;
	}
	public void setRoundId(int roundId) {
		this.roundId = roundId;
	}
	public Date getDateCalculate() {
		return dateCalculate;
	}
	public void setDateCalculate(Date dateCalculate) {
		this.dateCalculate = dateCalculate;
	}
	public Date getDatePayment() {
		return datePayment;
	}
	public void setDatePayment(Date datePayment) {
		this.datePayment = datePayment;
	}
		
}
