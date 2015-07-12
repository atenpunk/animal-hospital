package co.th.aten.network.model;

import java.math.BigDecimal;
import java.util.Date;

public class TariffModel {

    private Date effectiveDate;
    private String hour;
    private Integer mode;    
	private BigDecimal workday;
    private BigDecimal holiday;
    private boolean chkAction;
    private int countMode;
    private boolean chkFirst;
    private String modeD;
    
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	public BigDecimal getWorkday() {
		return workday;
	}
	public void setWorkday(BigDecimal workday) {
		this.workday = workday;
	}
	public BigDecimal getHoliday() {
		return holiday;
	}
	public void setHoliday(BigDecimal holiday) {
		this.holiday = holiday;
	}
	public boolean isChkAction() {
		return chkAction;
	}
	public void setChkAction(boolean chkAction) {
		this.chkAction = chkAction;
	}
	public int getCountMode() {
		return countMode;
	}
	public void setCountMode(int countMode) {
		this.countMode = countMode;
	}
	public boolean isChkFirst() {
		return chkFirst;
	}
	public void setChkFirst(boolean chkFirst) {
		this.chkFirst = chkFirst;
	}
	public String getModeD() {
		return modeD;
	}
	public void setModeD(String modeD) {
		this.modeD = modeD;
	}
	

}
