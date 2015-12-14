package com.malicia.mrg.android.apps.dailyaccount;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class _MySimpleCursorAdapter extends SimpleCursorAdapter {
	private final String[] names;
	private final int[] id;
	private String Label0;
	
	public _MySimpleCursorAdapter(Context co, int i, Cursor cu , String[] st , int[] it) {
		super(co, i, cu,st,it);
		this.names = st;
		this.id = it;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
	    
		// alimentation des champs de maniere standard
        int count = 0;

        TextView text ;
        while (count < id.length ) {
    		Label0 = cursor.getString(cursor.getColumnIndex(names[count].toString()));
    		text = (TextView) view.findViewById(id[count]);
    	    text.setText(Label0);
        	text.setTextColor(Color.WHITE);
        	
    	    count++;
    	    
        }
       	// Particulariter
		String IndexBudget = cursor.getString(cursor.getColumnIndex(_OperationDbAdapter.KEY_Budget));
	    if (IndexBudget.compareTo("")  !=  0 ) {
	        count = 0;
	        while (count < id.length ) {
	    		text = (TextView) view.findViewById(id[count]);
	    		try {
		        	text.setTextColor(_DataPool.BudgetColor(IndexBudget));
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    	    count++;
	        }
	    }

    	

	}

}