package com.appli.chacalprog;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerHelper {
	private static final int DATE_DIALOG_ID = 0;
	private final Activity activity;
	private TextView textView;
	int yearInput,monthInput,dayInput;


	public DatePickerHelper(Activity activity) {
		this.activity = activity;
	}

	public void init(final TextView textView) {
		this.textView = textView;
		final Calendar cal=Calendar.getInstance();
		yearInput=cal.get(Calendar.YEAR);
		monthInput=cal.get(Calendar.MONTH)+1;
		dayInput=cal.get(Calendar.DAY_OF_MONTH);
		fillTextView();
		textView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (textView.isEnabled()) {
					activity.showDialog(DATE_DIALOG_ID);
					return true; // processed=true
				} else {
					return false; // processed=false
				}
			}
		});
	}

/*	public void setTextFieldValue(Date date) {
		textView.setText(DateUtils.formatDate(date));
	}
*/
	public Dialog createDialog(int id) {
		DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int day) {
					yearInput=year;
				monthInput=month+1;
				dayInput=day;
				fillTextView( );
			}
		};

		if (DATE_DIALOG_ID == id) {
			return new DatePickerDialog(activity, listener, yearInput, monthInput-1,dayInput);
		}
		return null;
	}

	private void fillTextView(){
		String sMonth="";
		if (monthInput<10){
			sMonth="0";
		}
		textView.setText(dayInput + "/"   + sMonth+ monthInput + "/" + yearInput );
		
	}

}
