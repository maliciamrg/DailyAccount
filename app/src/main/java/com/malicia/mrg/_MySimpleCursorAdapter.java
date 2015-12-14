package com.malicia.mrg;
 
import com.malicia.mrg.android.apps.dailyaccount._OperationDbAdapter;

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
		String label4 = cursor.getString(cursor.getColumnIndex(_OperationDbAdapter.KEY_Budget));
	    if (label4.compareTo("0")  ==  0 ) { 
	        count = 0;
	        while (count < id.length ) {
	    		text = (TextView) view.findViewById(id[count]);
	        	text.setTextColor(Color.rgb(255, 255, 255));
	    	    count++;
	        }
	    }
	    if (label4.compareTo("1")  ==  0 ) { 
	        count = 0;
	        while (count < id.length ) {
	    		text = (TextView) view.findViewById(id[count]);
	        	text.setTextColor(Color.rgb(220, 220, 255));
	    	    count++;
	        }
	    }
	    if (label4.compareTo("2")  ==  0 ) { 
	        count = 0;
	        while (count < id.length ) {
	    		text = (TextView) view.findViewById(id[count]);
	        	text.setTextColor(Color.rgb(180, 180, 255));
	    	    count++;
	        }
	    }
	    if (label4.compareTo("3")  ==  0 ) { 
	        count = 0;
	        while (count < id.length ) {
	    		text = (TextView) view.findViewById(id[count]);
	        	text.setTextColor(Color.rgb(130, 130, 255));
	    	    count++;
	        }
	    }
	    if (label4.compareTo("4")  ==  0 ) { 
	        count = 0;
	        while (count < id.length ) {
	    		text = (TextView) view.findViewById(id[count]);
	        	text.setTextColor(Color.rgb(70, 70, 255));
	    	    count++;
	        }
	    }
	    if (label4.compareTo("5")  ==  0 ) { 
	        count = 0;
	        while (count < id.length ) {
	    		text = (TextView) view.findViewById(id[count]);
	        	text.setTextColor(Color.rgb(0, 0, 255));
	    	    count++;
	        }
	    }

    	

	}

}