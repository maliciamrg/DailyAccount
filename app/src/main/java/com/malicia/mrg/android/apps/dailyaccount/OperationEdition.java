/*
 * Copyright (C) 2008 Google Inc.
 *  *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.malicia.mrg.android.apps.dailyaccount;

import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.malicia.mrg._MySpinner;
import com.malicia.mrg.android.apps.dailyaccount.R;
import com.malicia.mrg.android.apps.dailyaccount.R;

public class OperationEdition extends Activity {

	private String[] PayeeList;   
	private ArrayAdapter<String> adapter;
    private AutoCompleteTextView mPayeeText;
    private _MySpinner mBudget;
    private EditText mAmountText;
    private EditText mMemoText;
    private Long mRowId;
    //private _OperationDbAdapter mDbHelper;
    
    private Button mPickDate;
    private String mDate;
    private int mYear;
    private int mMonth;
    private int mDay;

    static final int DATE_DIALOG_ID = 0;
    
    @SuppressLint("ParserError")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mDbHelper = new _OperationDbAdapter(this);
//        _OperationDbAdapter.open(this);

        setContentView(R.layout.operation_edition);
        setTitle(R.string.edit_note);

        mPickDate = (Button) findViewById(R.id.pickDate );
        mPayeeText = (AutoCompleteTextView ) findViewById(R.id.payee_cash);
        mAmountText  = (EditText) findViewById(R.id.Amount_cash);
        mMemoText = (EditText) findViewById(R.id.memo_cash);
        Button confirmButton = (Button) findViewById(R.id.confirm);
        mBudget = (_MySpinner) findViewById(R.id.BudgetList);

        // get the current date       
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH );
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
        mRowId = (savedInstanceState == null) ? null :
            (Long) savedInstanceState.getSerializable(_OperationDbAdapter.KEY_ROWID);
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(_OperationDbAdapter.KEY_ROWID)
									: null;
		}


		CreePayeeList();       
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, PayeeList);
		mPayeeText.setAdapter(adapter);

        CreeBudgetList();
//        adapterB = new ArrayAdapter<String>(this, R.layout.list_item, BudgetList);
//        mBudget.setAdapter(adapterB);
		
		populateFields();
		
        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                setResult(RESULT_OK);
                saveState();
                finish();
            }

        });

		
        // add a click listener to the button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });


        // display the current date (this method is below)
        updateDisplay();
        
        
    }

    @SuppressLint("ParserError")
	private void CreePayeeList() {
        Cursor PayeeCursor = _OperationDbAdapter.fetchAllPayee();
        
        ArrayList<String> strings = new ArrayList<String>();
        PayeeCursor.moveToFirst();
        if (PayeeCursor.getCount() > 0 ) { strings.add(PayeeCursor.getString(0)); }
        for(PayeeCursor.moveToFirst(); PayeeCursor.moveToNext(); PayeeCursor.isAfterLast()) {
           String mTitleRaw = PayeeCursor.getString(0);
           strings.add(mTitleRaw);
        }
        PayeeList = (String[]) strings.toArray(new String[strings.size()]);
    }

    private void CreeBudgetList() {
//        ArrayList<String> strings = new ArrayList<String>();
        for (int i=1; i<=_DataPool.Param_Nb_CompteU(); i++) {
            for (int y=0; y<=_DataPool.NbBudget(i); y++) {
            	mBudget.addItem(_DataPool.NomCompteBudget(i, y),false);
//            	strings.add(_DataPool.NomCompteBudget(i, y));
            }
        }
//        BudgetList = (String[]) strings.toArray(new String[strings.size()]);
    }

	private void populateFields() {

       
        if (mRowId != null) {
            Cursor note = _OperationDbAdapter.fetchNote(mRowId);
            startManagingCursor(note);
            mPickDate.setText(note.getString(
                    note.getColumnIndexOrThrow(_OperationDbAdapter.KEY_DATE)));
            mPayeeText.setText(note.getString(
                    note.getColumnIndexOrThrow(_OperationDbAdapter.KEY_PAYEE)));
            mAmountText.setText(note.getString(
                    note.getColumnIndexOrThrow(_OperationDbAdapter.KEY_AMOUNT)));
            mMemoText.setText(note.getString(
                    note.getColumnIndexOrThrow(_OperationDbAdapter.KEY_MEMO)));
            // correction des Mauvais IndexBudget
            String IndexBudget = note.getString(note.getColumnIndexOrThrow(_OperationDbAdapter.KEY_Budget));
            if (!_DataPool.IsIndexBudget(IndexBudget)){
            	_OperationDbAdapter.updateAllBudget(IndexBudget, _DataPool.NumBudget(1, 0));
            }
            //
            mBudget.selectItem(_DataPool.NomCompteBudget(note.getString(
            		note.getColumnIndexOrThrow(_OperationDbAdapter.KEY_Budget))));
        } else {
        	updateDisplay();
    	}
    }
	
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(_OperationDbAdapter.KEY_ROWID, mRowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    private void saveState() {
        String date = (String) mPickDate.getText();
        String payee = mPayeeText.getText().toString();
        String amount = mAmountText.getText().toString();
        String memo = mMemoText.getText().toString();
        String IndexBudget = _DataPool.FindBudgetNum(mBudget.getSelected());

        if (mRowId == null) {
            long id = _OperationDbAdapter.createNote(date, payee , amount , memo , IndexBudget );
            if (id > 0) {
                mRowId = id;
            }
        } else {
        	_OperationDbAdapter.updateNote(mRowId, date, payee , amount , memo , IndexBudget );
        }
    }
 
    // updates the date in the TextView
    private void updateDisplay() {
        java.text.DecimalFormat Month2digit = new java.text.DecimalFormat("00"); 
        mPickDate.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
            		.append(mYear).append("-")
        			.append(Month2digit.format(mMonth + 1)).append("-")
            		.append(Month2digit.format(mDay)).append(" "));
        mBudget.selectItem(_DataPool.NomCompteBudget(_DataPool.Param_Compte_Principal()));
    }    
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
	    new DatePickerDialog.OnDateSetListener() {
	
	        public void onDateSet(DatePicker view, int year, 
	                              int monthOfYear, int dayOfMonth) {
	            mYear = year;
	            mMonth = monthOfYear;
	            mDay = dayOfMonth;
	            updateDisplay();
	        }
	    };
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
        	mDate = (String) mPickDate.getText();
        	mYear = Integer.parseInt((String) mDate.subSequence(0, 4));
        	mMonth = Integer.parseInt((String) mDate.subSequence(5, 7))-1;
        	mDay = Integer.parseInt((String) mDate.subSequence(8, 10));
            return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }


}
