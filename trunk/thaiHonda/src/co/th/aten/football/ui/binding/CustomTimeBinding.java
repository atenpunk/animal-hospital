/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.ui.binding;

import java.util.Calendar;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import org.springframework.binding.form.FormModel;
import org.springframework.richclient.form.binding.support.CustomBinding;

/**
 *
 * @author Aten
 */
public class CustomTimeBinding extends CustomBinding {

    private JSpinner timeSpinner;
    private Date theDate;
    private String timeFormat = "HH:mm:ss";

    public CustomTimeBinding(FormModel formModel, String formPropertyPath) {
        super(formModel, formPropertyPath, Date.class);
        this.theDate = (getValue() == null) ? new Date() : (Date)getValue();
        SpinnerDateModel sm = new SpinnerDateModel(theDate, null, null, Calendar.HOUR_OF_DAY);
        timeSpinner = new JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(timeSpinner, timeFormat);
        timeSpinner.setEditor(de);
    }

    public CustomTimeBinding(FormModel formModel, String formPropertyPath,String customTimeFormat) {
        super(formModel, formPropertyPath, Date.class);
        this.theDate = (getValue() == null) ? new Date() : (Date)getValue();
        this.timeFormat = customTimeFormat;
        SpinnerDateModel sm = new SpinnerDateModel(theDate, null, null, Calendar.HOUR_OF_DAY);
        timeSpinner = new JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(timeSpinner, timeFormat);
        timeSpinner.setEditor(de);
    }

    @Override
    protected void valueModelChanged(Object newValue) {
       timeSpinner.setValue((Date)newValue);
    }

    @Override
    protected JComponent doBindControl() {
        return timeSpinner;
    }

    @Override
    protected void readOnlyChanged() {
        enabledChanged();
    }

    @Override
    protected void enabledChanged() {
       timeSpinner.setEnabled(isEnabled() && !isReadOnly());
    }

    /**
     * @return the timeFormat
     */
    public String getTimeFormat() {
        return timeFormat;
    }

    /**
     * @param timeFormat the timeFormat to set
     */
    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }
}
