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
import java.util.HashMap;

import com.malicia.mrg.PromptDialog;
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
 
public class Param extends Activity {
 
	private ListView maListViewPerso;
    private static final int ACTIVITY_CREATE=0;
    private static final int DELETE_ID = Menu.FIRST ;
    
    final Param context = this;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masterlist);
        
        populateFields();
 
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }
	
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        populateFields();
    }

    
    @Override
    protected void onPause() {
        super.onPause();
        populateFields();
    }
   

	private void populateFields() {   
    //Recuperation de la listview creee dans le fichier main.xml
    maListViewPerso = (ListView) findViewById(R.id.listviewperso);

    //Creation de la ArrayList qui nous permettra de remplire la listView
    ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

    //On declare la HashMap qui contiendra les informations pour un item
    HashMap<String, String> map;

    map = new HashMap<String, String>();
    map.put("objectname", this.getString(R.string.Param_nb_jour_graph));
    map.put("objectmois", String.valueOf(_DataPool.Param_nb_jour_graph()));
    map.put("variable", _DataPool.v_NbBudget);
    map.put("attributchamp","NUMERIQUE");
    map.put("index", "0");
    map.put("img",  String.valueOf(R.drawable.paramconfig));
    listItem.add(map);

    
    map = new HashMap<String, String>();
    map.put("couleur", String.valueOf(Color.YELLOW));
    map.put("IdRessouce", String.valueOf(R.layout.affichageitem2));
    map.put("objectname", this.getString(R.string.Compte));
    listItem.add(map);

    _DataPool.NewCompte(0);
    
    for (int i=0; i<=_DataPool.Param_Nb_CompteU(); i++) {

    	if (_DataPool.IsExistCompte(String.valueOf(i)) || i == 0) {

        	_DataPool.Recalcul_Compte(i);

        	map = new HashMap<String, String>();
            map.put("compte", String.valueOf(i));
            map.put("couleur", String.valueOf(_DataPool.CompteColor(i)));
            map.put("objectname", _DataPool.Nom_Compte(i));
            map.put("objectmois", String.valueOf(_DataPool.BrutCompte(i)));
            map.put("objectjour", String.valueOf(_DataPool.MtBudgetJour(String.valueOf(_DataPool.BrutCompte(i)))));
            map.put("iv_forward", String.valueOf(R.drawable.forward_arrow));
            map.put("img"       , String.valueOf(R.drawable.paramcompte));
            listItem.add(map);			

    	}
    }

    //Creation d'un SimpleAdapter qui se chargera de mettre les items present dans notre list (listItem) dans la vue affichageitem
    _MySimpleAdapter mSchedule = new _MySimpleAdapter(this.getBaseContext(), listItem, R.layout.affichageitem,
           new String[] {"img","objectname","objectmois","objectjour","iv_forward"}, new int[] {R.id.img, R.id.objectname, R.id.objectmois , R.id.objectjour , R.id.iv_forward});

    
    //On attribut e notre listView l'adapter que l'on vient de creer
    maListViewPerso.setAdapter(mSchedule);
    
    //Enfin on met un ecouteur d'evenement sur notre listView
    maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
		@Override
    	@SuppressWarnings("unchecked")
     	public void onItemClick(AdapterView<?> a, View v, int position, long id) {

			//on recupere la HashMap contenant les infos de notre item (titre, description, img)
    		final HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);

    		String Cpte = map.get("compte");
    		if (Cpte!=null){
    			int CpteN = Integer.parseInt(Cpte);
        		if (CpteN==0){CpteN=_DataPool.NewCompte();};
        		Intent j = new Intent(getBaseContext(), ParamCompte.class );
        		j.putExtra("RowId", CpteN);
        		startActivityForResult(j, ACTIVITY_CREATE);
    		} else {
    	        if (map.get("variable")!=null && map.get("index")!=null ) {
    	        	
    	        	PromptDialog PDialog = new PromptDialog(context , map.get("objectname") , map.get("objectname") ,  map.get("objectmois") , map.get("attributchamp")) {  
        	        	  @Override  
        	        	  public boolean onOkClicked(Context context , String input) {  
        	        		  input = input.trim();
        	  		   		  if (!input.equalsIgnoreCase("")) {
        	  		   			  _DataPool.setParam ( map.get("variable")  , input , map.get("index"));
            	  		   		  populateFields();
        	  		   			  return true; // true = close dialog
        	  		   		  }
        	  				  return false;
        	        	  }
        			};
        			PDialog.show();
    	        }
        	     
    		}        		
    	}
     });
     registerForContextMenu(maListViewPerso);
	}
    
    
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        int i = (int) ((AdapterContextMenuInfo) menuInfo).id;
        //on recupere la HashMap contenant les infos de notre item (titre, description, img)
		@SuppressWarnings("unchecked")
		final HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition( i );
		if (map.get("compte")!=null){
        	menu.add(0, DELETE_ID, 0, R.string.menu_delete);
		};
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    			
                //on recupere la HashMap contenant les infos de notre item (titre, description, img)
        		@SuppressWarnings("unchecked")
				final HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition( (int) info.id);
        		String Cpte = map.get("compte");
                
                _DataPool.DelCompte(Cpte);
                populateFields();
                return true;
        }
        return super.onContextItemSelected(item);
    }
    

}
	
