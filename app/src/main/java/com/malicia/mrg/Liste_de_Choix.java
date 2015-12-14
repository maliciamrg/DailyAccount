package com.malicia.mrg;

import java.util.ArrayList;
import java.util.HashMap;

import com.malicia.mrg.android.apps.dailyaccount.DailyAccount;
import com.malicia.mrg.android.apps.dailyaccount._DataPool;
import com.malicia.mrg.android.apps.dailyaccount._DataPool;
import com.malicia.mrg.android.apps.dailyaccount.R;
import com.malicia.mrg.android.apps.dailyaccount._MySimpleAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Liste_de_Choix  {
	
	static ArrayList<HashMap<String, String>> listItem;
	
	public static void Start_new(Context ctx,int R_layout_masterlist,int R_id_listviewperso,int R_layout_affichageitem) {
		ListView maListViewPerso ;
		
	    listItem = new ArrayList<HashMap<String, String>>();

		LayoutInflater li = LayoutInflater.from(ctx);
		View view = li.inflate(R_layout_masterlist, null);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		
		builder.setTitle(R.string.Liste_des_Budget);
		
		builder.setView(view);
		
		final AlertDialog ad  = builder.create();
		
		ad.show();
		
	//redefinir la taille
		
	    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(ad.getWindow().getAttributes());
	    lp.width = WindowManager.LayoutParams.FILL_PARENT;
	    lp.height = WindowManager.LayoutParams.FILL_PARENT;
	    ad.getWindow().setAttributes(lp);
	
		
	//alimentation liste
		
	    //Recuperation de la listview creee dans le fichier main.xml
	    maListViewPerso = (ListView) ad.findViewById(R_id_listviewperso);
	    
	    //Creation de la ArrayList qui nous permettra de remplire la listView
//	    ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
	
	    //On declare la HashMap qui contiendra les informations pour un item
	    HashMap<String, String> map;
	
	    int ip = 0 ;
	    for (int i=1; i<=_DataPool.Param_Nb_CompteU(); i++) {
	        for (int y=0; y<=_DataPool.NbBudget(i); y++) {
	        	
	        	if (_DataPool.IsExistBudget(_DataPool.NumBudget(i, y))) {
		        	
		    		String[] Ret =_DataPool.EnteteCompte(i,y);
		    		
		    		map = new HashMap<String, String>();
		    		if (i != ip) {
		    		map.put("textglob1",_DataPool.Nom_Compte(i)); 
		    	    map.put("textglob2",Ret[0]); 
		    	    map.put("textglob3",Ret[12]); 
		    		};
		    	    map.put("textglob4",_DataPool.BudgetNom(_DataPool.NumBudget(i, y)));
		    	    map.put("textglob5",Ret[11]);
		    	    map.put("textglob6",Ret[13]);
		    	    map.put("couleur", String.valueOf(_DataPool.BudgetColor(_DataPool.NumBudget(i, y))));
		    	    map.put("Compte", _DataPool.NumBudget(i, y));
		    	    listItem.add(map);
		            
		    	    ip = i;
		            Ret = null;
	        		
	        	}
	        }
	    }
	    
	    //Creation d'un SimpleAdapter qui se chargera de mettre les items present dans notre list (listItem) dans la vue affichageitem
	    _MySimpleAdapter mSchedule = new _MySimpleAdapter( ctx, listItem, R_layout_affichageitem,
	           new String[] {"textglob1","textglob2","textglob3","textglob4","textglob5","textglob6"}, new int[] {R.id.textglob1, R.id.textglob2 , R.id.textglob3,R.id.textglob4, R.id.textglob5 , R.id.textglob6 });
	
	    //On attribut e notre listView l'adapter que l'on vient de creer
	    maListViewPerso.setAdapter(mSchedule);
	    
	    
	//listener listview
	
	    maListViewPerso.setOnItemClickListener(new ListView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
	                try {
	                    // Remembers the selected Index
	                	_DataPool.Param_Compte_Principal(listItem.get(i).get("Compte"));
	                	//Sortir 
	                    DailyAccount.fillEntete();

	                	ad.cancel();                    
	                }
	                catch(Exception e) {
	                    System.out.println("Nay, cannot get the selected index");
	                }
	            }
	        });
		}
	

}
