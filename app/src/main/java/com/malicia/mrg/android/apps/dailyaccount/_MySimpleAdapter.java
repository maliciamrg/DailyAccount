				
package com.malicia.mrg.android.apps.dailyaccount;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.malicia.mrg.android.apps.dailyaccount.R.drawable;
import com.malicia.mrg.android.apps.dailyaccount.R.string;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore.Images;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class _MySimpleAdapter extends SimpleAdapter {
	private final Context context;
	private final Object[] values;
	private final int[] idresource;
	private final int resource;

	public _MySimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.context = context;
		this.values = data.toArray();
		this.idresource = to;
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
  	   View rowView;
	   TextView textglob1 = null;
	   TextView textglob2 = null;
	   TextView textglob3 = null;
	   TextView textglob4 = null;
	   TextView textglob5 = null;
	   TextView textglob6 = null;
	   TextView objectname = null;
  	   TextView objectmois = null;
  	   TextView objectjour = null;
  	   ImageView img = null;
  	   ImageView iv_forward = null;

  	try {  
  		 String id = (String) ((Map<String,?>) values[position]).get("IdRessouce");
	  	 rowView = inflater.inflate(Integer.parseInt(id) , parent, false);
	  	}
	 	catch( Exception e )  
	 	{  
	 	 rowView = inflater.inflate(resource, parent, false);
	 	} 

	try {  
		textglob1 = (TextView) rowView.findViewById(R.id.textglob1);
		textglob1.setText("");
  	   }catch( Exception e ) {}
    
    try {  
    	textglob2 = (TextView) rowView.findViewById(R.id.textglob2);
    	textglob2.setText("");
  	   }catch( Exception e ) {}
    
    try {  
    	textglob3 = (TextView) rowView.findViewById(R.id.textglob3);
    	textglob3.setText("");
  	   }catch( Exception e ) {}
	    
	try {  
		textglob4 = (TextView) rowView.findViewById(R.id.textglob4);
		textglob4.setText("");
  	   }catch( Exception e ) {}
    
    try {  
    	textglob5 = (TextView) rowView.findViewById(R.id.textglob5);
    	textglob5.setText("");
  	   }catch( Exception e ) {}
    
    try {  
    	textglob6 = (TextView) rowView.findViewById(R.id.textglob6);
    	textglob6.setText("");
  	   }catch( Exception e ) {}
	    
	try {  
		objectname = (TextView) rowView.findViewById(R.id.objectname);
		objectname.setText("");
  	   }catch( Exception e ) {}

	try {  
		objectmois = (TextView) rowView.findViewById(R.id.objectmois);
		objectmois.setText("");
  	   }catch( Exception e ) {}
  	   
	try {  
		objectjour = (TextView) rowView.findViewById(R.id.objectjour);
		objectjour.setText("");
  	   }catch( Exception e ) {}
		
	try {  
  	    img        = (ImageView) rowView.findViewById(R.id.img);
	   }catch( Exception e ) {}
		
	try {  
		iv_forward = (ImageView) rowView.findViewById(R.id.iv_forward);
	   }catch( Exception e ) {}

	//couleur
	Integer c = 0;
	try {  
		c = Integer.parseInt((String) ((Map<String,?>) values[position]).get("couleur"));
		}catch( Exception e ) {}
	   
		Iterator iterator = ((Map<String, ?>) values[position]).keySet().iterator();  // keySet() renvoie un Set de toutes les cles contenues dans le Map
		while(iterator.hasNext()){
		   String maCle = (String) iterator.next();
		   String maValeur = (String) ((Map<String,?>) values[position]).get(maCle);


		   if (maCle=="textglob1"){
			   textglob1.setText(maValeur);
			   if (c!=0){textglob1.setTextColor(c);}
			   }
		   if (maCle=="textglob2"){
			   textglob2.setText(maValeur);
			   if (c!=0){textglob2.setTextColor(c);}
			   }
		   if (maCle=="textglob3"){
			   textglob3.setText(maValeur);
			   if (c!=0){textglob3.setTextColor(c);}
			   }
		   if (maCle=="textglob4"){
			   textglob4.setText(maValeur);
			   if (c!=0){textglob4.setTextColor(c);}
			   }
		   if (maCle=="textglob5"){
			   textglob5.setText(maValeur);
			   if (c!=0){textglob5.setTextColor(c);}
			   }
		   if (maCle=="textglob6"){
			   textglob6.setText(maValeur);
			   if (c!=0){textglob6.setTextColor(c);}
			   }
		   if (maCle=="objectname"){
			   objectname.setText(maValeur);
			   if (c!=0){objectname.setTextColor(c);}
			   }
		   if (maCle=="objectmois"){
			   objectmois.setText(maValeur);
			   if (c!=0){objectmois.setTextColor(c);}
		   	   }
		   if (maCle=="objectjour"){
			   objectjour.setText(maValeur);
			   if (c!=0){objectjour.setTextColor(c);}
			   }
		   if (maCle=="iv_forward"){iv_forward.setImageResource(Integer.parseInt(maValeur));}
		   if (maCle=="img"){img.setImageResource(Integer.parseInt(maValeur));}
		   
		}
		
		return rowView;
	}

}
