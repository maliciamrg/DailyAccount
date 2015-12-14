package com.malicia.mrg.android.apps.dailyaccount;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

public class Graphique_Budget {

    static XYSeries Balance[] = new XYSeries[20];
	static String Compte_Present[ ]= new String[20];
    static String IndexBudget[ ]= new String[20];
    static float MtduJour[ ]= new float[20];
    static float Mtsum[ ]= new float[20];
    static float MtBal[ ]= new float[20];
    static float Mtbudget[ ]= new float[20];
    
    public static void Create(Context context) {
//		return ChartFactory.getLineChartIntent( context,  getDemoDataset(), getDemoRenderer(), "Evolution des 2 derniers mois");
	    Intent intent = ChartFactory.getLineChartIntent( context,  getDemoDataset(), getDemoRenderer(), "Evolution des 2 derniers mois");
	    context.startActivity(intent);	
	}
 

  private static XYMultipleSeriesDataset getDemoDataset()  {
	  
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    
	String DATE_FORMAT = "yyyy-MM-dd";
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
    
 // Calcul Nbjt
	int nbjt = -1 * _DataPool.Param_nb_jour_graph();
	
	Cursor MinDateCursor = _OperationDbAdapter.MinDate();
	MinDateCursor.moveToFirst();
	if (MinDateCursor.getCount() > 0) {
		String MinDate = MinDateCursor.getString(0);
		
		long t1 = Calendar.getInstance().getTime().getTime();
		int diff;
		try {
			long t2 = sdf.parse(MinDate).getTime();
			diff = (int) ( -1 * (t1 - t2)/(1000 * 60 * 60 * 24)) ;
		} catch (ParseException e1) {
			diff = -999;
		}
		
		diff = diff - 1 ;
		
		if ( diff > nbjt ) {
			nbjt = diff ;
		}
	} else {
		nbjt = -1;
	}
 
    Calendar c1 = Calendar.getInstance(); 
    c1.add(Calendar.DATE,nbjt);
	String dDate = sdf.format(c1.getTime());
//
	
	
	
//    Cursor SumAmountCursor = _OperationDbAdapter.SumAmount(dDate);
    Cursor SumAmountCursor = _OperationDbAdapter.SumAmount(dDate);
    SumAmountCursor.moveToFirst();

    XYSeries Zero = new XYSeries("Zero");
    

	
	int num_cpt;
	
	String pa;
	
    int count;
	if (SumAmountCursor.getCount() > 0 ) {                  
    	
    	
    	for (int nbj = nbjt; nbj<1; nbj++) {
    	
    		c1 = Calendar.getInstance(); 
	        c1.add(Calendar.DATE,nbj);      
	    	String dDate2 = sdf.format(c1.getTime());
	    	
    		pa ="9999-12-31";
    		if (!SumAmountCursor.isAfterLast()) {
		    	pa = SumAmountCursor.getString(1).trim();
	    		}
    		
	    	String pb = dDate2.trim();
	    	while (pa.compareTo(pb) == 0 ) {
	    		num_cpt = ConstitutionNumCpt(SumAmountCursor.getString(2));
	    		MtduJour[num_cpt] = MtduJour[num_cpt] + Float.parseFloat(SumAmountCursor.getString(0));
	    		SumAmountCursor.moveToNext();
	    		
	    		pa ="9999-12-31";
	    		if (!SumAmountCursor.isAfterLast()) {
			    	pa = SumAmountCursor.getString(1).trim();
		    		}
	    		
	    		}
	 
	        
	    	//boucle de remplissage
	    	count = 0;
	        while (count < 20 ) {
	        	if (Compte_Present[count] != null) {
			    	Mtsum[count] = Mtsum[count] + MtduJour[count];
			    	Mtbudget[count] = Mtbudget[count] + _DataPool.BudgetJour(Compte_Present[count]);
			    	MtBal[count] = Mtbudget[count] - Mtsum[count];
			    	
			    	Balance[count].add(nbj, MtBal[count]);
			    	MtduJour[count] = 0;
	        	}
	    	    count++;
	        }

	    	Zero.add( nbj, 0);

	    	
    	}
    	
    }
    
	
	
    dataset.addSeries(Zero);
    
	count = 0;
    while (count < 20 ) {
    	if (Compte_Present[count] != null) {
            dataset.addSeries(Balance[count]);
    	}
	    count++;
    }

	MinDateCursor.close();
	SumAmountCursor.close();
    return dataset;
  }



private static int ConstitutionNumCpt(String indexBudget) {

	int count = 0;
    int num_cpt = 0;
	while (count < 20 ) {
    	if (Compte_Present[count] == null) {
    		num_cpt  = count;
    	}else if (Compte_Present[count].compareTo(indexBudget)==0) {
    		num_cpt  = count;
    	}
	    count++;
    }

	if (Compte_Present[num_cpt] == null) {
		Compte_Present[num_cpt] = indexBudget ;
		Mtbudget[num_cpt] = - _DataPool.BudgetJour(indexBudget);
		String [] s = _DataPool.UndoNumBudget(indexBudget);
		Balance[num_cpt] = new XYSeries(_DataPool.Nom_Compte(Integer.valueOf(s[1])) + " "  + _DataPool.BudgetNom(indexBudget));
	}
		
	return num_cpt;
}


private static XYMultipleSeriesRenderer getDemoRenderer() {
	  
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    renderer.setAxisTitleTextSize(16);
    renderer.setChartTitleTextSize(20);
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    renderer.setPointSize(5f);
    renderer.setMargins(new int[] {20, 30, 15, 0});
    
    XYSeriesRenderer r = new XYSeriesRenderer();
    r.setColor(Color.DKGRAY);
    r.setFillPoints(false);
    renderer.addSeriesRenderer(r);
    
	int count = 0;
    while (count < 20 ) {
		if (Compte_Present[count] != null) {
		    r = new XYSeriesRenderer();
//		    r.setPointStyle(PointStyle.CIRCLE);
		    r.setColor(_DataPool.BudgetColor(Compte_Present[count]));
		    r.setFillPoints(false);
		    renderer.addSeriesRenderer(r);
		}		
	    count++;
    }
    
    renderer.setAxesColor(Color.DKGRAY);
    renderer.setLabelsColor(Color.LTGRAY);
    return renderer;
  }




}