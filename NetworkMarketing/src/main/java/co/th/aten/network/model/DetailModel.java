package co.th.aten.network.model;

import java.io.Serializable;
import java.util.Date;

import co.th.aten.network.entity.UserLogin;

public class DetailModel implements Serializable {	

	private String Text;
	private String value;
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
