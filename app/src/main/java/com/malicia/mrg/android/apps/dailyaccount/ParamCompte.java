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

import com.malicia.mrg.Fonction;
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
 
public class ParamCompte extends Activity {
 
	private ListView maListViewPerso;
    private static final int ACTIVITY_CREATE=0;
    private static final int DELETE_ID = Menu.FIRST ;
    
    private int mRowId;
        
    final ParamCompte context = this;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masterlist);
        
        Bundle extras = getIntent().getExtras();
        mRowId = extras.getInt("RowId");
        
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
        map.put("couleur", String.valueOf(Color.YELLOW));
	    map.put("objectname", this.getString(R.string.Parametre_compte));
        map.put("IdRessouce", String.valueOf(R.layout.affichageitem2));
	    listItem.add(map);
	
	    map = new HashMap<String, String>();
	    map.put("objectname", this.getString(R.string.Nom));
	    map.put("objectmois", String.valueOf(_DataPool.Nom_Compte(mRowId)));
	    map.put("variable", _DataPool.v_Nom_Compte);
	    map.put("index", String.valueOf(mRowId));
	    map.put("img",  String.valueOf(R.drawable.paramconfig));
	    map.put("IdRessouce", String.valueOf(R.layout.affichageitem4));
	    listItem.add(map);
	 
	    map = new HashMap<String, String>();
        map.put("couleur", String.valueOf(Color.YELLOW));
	    map.put("objectname", this.getString(R.string.Salaire_mens));
	    map.put("IdRessouce", String.valueOf(R.layout.affichageitem2));
	    listItem.add(map);
	
	    map = new HashMap<String, String>();
	    map.put("objectname", this.getString(R.string.Montant));
	    map.put("objectmois", String.valueOf(_DataPool.MtSalaireMensuel(mRowId)));
	    map.put("variable", _DataPool.v_MtSalaireMensuel);
	    map.put("index", String.valueOf(mRowId));
	    map.put("img",  String.valueOf(R.drawable.paramconfig));
	    map.put("IdRessouce", String.valueOf(R.layout.affichageitem4));
	    listItem.add(map);
	
	    map = new HashMap<String, String>();
	    map.put("objectname", this.getString(R.string.Jour_de_Paye));
	    map.put("objectmois", String.valueOf(_DataPool.ValJourDePaye(mRowId)));
	    map.put("variable", _DataPool.v_ValJourDePaye);
	    map.put("index", String.valueOf(mRowId));
	    map.put("img",  String.valueOf(R.drawable.paramconfig));
	    map.put("IdRessouce", String.valueOf(R.layout.affichageitem4));
	    listItem.add(map);
	
	    map = new HashMap<String, String>();
        map.put("couleur", String.valueOf(Color.YELLOW));
	    map.put("objectname", this.getString(R.string.Prelevement_mens));
	    map.put("IdRessouce", String.valueOf(R.layout.affichageitem2));
	    listItem.add(map);
	
	    map = new HashMap<String, String>();
	    map.put("objectname", this.getString(R.string.Montant));
	    map.put("objectmois", String.valueOf(_DataPool.MtPrelevementMensuel(mRowId)));
	    map.put("variable", _DataPool.v_MtPrelevementMensuel);
	    map.put("index", String.valueOf(mRowId));
	    map.put("img",  String.valueOf(R.drawable.paramconfig));
	    map.put("IdRessouce", String.valueOf(R.layout.affichageitem4));
	    listItem.add(map);
	
	    map = new HashMap<String, String>();
        map.put("couleur", String.valueOf(Color.YELLOW));
	    map.put("objectname", this.getString(R.string.epargne_mens));
	    map.put("IdRessouce", String.valueOf(R.layout.affichageitem2));
	    listItem.add(map);
	
	    map = new HashMap<String, String>();
	    map.put("objectname", this.getString(R.string.Montant));
	    map.put("objectmois", String.valueOf(_DataPool.MtAEpargner(mRowId)));
	    map.put("variable", _DataPool.v_MtAEpargner);
	    map.put("index", String.valueOf(mRowId));
	    map.put("img",  String.valueOf(R.drawable.paramconfig));
	    map.put("IdRessouce", String.valueOf(R.layout.affichageitem4));
	    listItem.add(map);
	
	    map = new HashMap<String, String>();
        map.put("couleur", String.valueOf(Color.YELLOW));
	    map.put("objectname", this.getString(R.string.Budget));
	    map.put("IdRessouce", String.valueOf(R.layout.affichageitem2));
	    listItem.add(map);
	
	    String IBudget;
	    
	    _DataPool.Recalcul_Compte(mRowId);
	    
	    _DataPool.NewBudget(mRowId , 0);
	    
	    for (int i=0; i<=_DataPool.NbBudget(mRowId); i++) {
	
	    	IBudget = _DataPool.NumBudget(mRowId, i);
	    	
	    	if (_DataPool.IsExistBudget(IBudget) || i == 0){
		    	map = new HashMap<String, String>();
		        map.put("couleur", String.valueOf(_DataPool.BudgetColor(IBudget)));
		        map.put("budget", IBudget);
		        map.put("objectname", _DataPool.NomCompteBudget(IBudget));
		        map.put("objectmois", String.valueOf(_DataPool.BudgetMois(IBudget)));
		        map.put("objectjour", String.valueOf(_DataPool.BudgetJour(IBudget)));
		        map.put("iv_forward", String.valueOf(R.drawable.forward_arrow));
		        map.put("img"       , String.valueOf(R.drawable.parambudget));
			    map.put("IdRessouce", String.valueOf(R.layout.affichageitem));
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
	
	    		String SSCpte = map.get("budget");
	    		if (SSCpte!=null){
	    			String[] S = _DataPool.UndoNumBudget(SSCpte);
	    	        EditerBudget(mRowId,Integer.parseInt(S[3]));
	    		} else {
	    	        if (map.get("variable")!=null && map.get("index")!=null ) {
	    	        	
	    	        	PromptDialog PDialog = new PromptDialog(context , map.get("objectname") , map.get("objectname") ,  map.get("objectmois")  , map.get("attributchamp")) {  
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
		String SSCpte = map.get("budget");
		if (SSCpte!=null){
			String[] S = _DataPool.UndoNumBudget(SSCpte);
			if (Integer.parseInt(S[3])!=0){
				menu.add(0, DELETE_ID, 0, R.string.menu_delete);
			}
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
        		String SSCpte = map.get("budget");
    			String[] S = _DataPool.UndoNumBudget(SSCpte);
                _DataPool.DelBudget(mRowId, Integer.parseInt(S[3]));
                populateFields();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    
    
    
    
	private void EditerBudget(final Integer compte , int budget) {
	    if (budget == 0){budget = _DataPool.NewBudget(compte);}
	    final int Num_Bud = budget;
	  	  
        PromptDialog nombudget = new PromptDialog(this, R.string.Nom_Budget,R.string.Budget , _DataPool.BudgetNom(_DataPool.NumBudget(compte,Num_Bud)), "" ) {  
      	  @Override  
      	  public boolean onOkClicked(Context context , String input) {  
      		  input = input.trim();
		   		  if (!input.equalsIgnoreCase("")) {
		   			  _DataPool.BudgetNom(_DataPool.NumBudget(compte,Num_Bud),input);
		   			  populateFields();
		   			  return true; // true = close dialog
		   		  }
				  return false;
      	  }
        };
                  
        PromptDialog montantbudget = new PromptDialog(this, R.string.Montant_Budget, R.string.amount , String.valueOf(_DataPool.BudgetMois(_DataPool.NumBudget(compte,Num_Bud))) , PromptDialog.TypeChamp_Numerique) {  
		   	  @Override  
		   	  public boolean onOkClicked(Context context , String input) {  
		   		  if (Fonction.isIntNumber(input)) {
		   			  _DataPool.BudgetMois(_DataPool.NumBudget(compte,Num_Bud),input); 
		   			  _DataPool.BudgetJour(_DataPool.NumBudget(compte,Num_Bud),_DataPool.MtBudgetJour(input));
		   			  populateFields();
		   			  return true; // true = close dialog
		   		  }
				  return false;
		   	  }  
		  };  

		  montantbudget.show();
		  nombudget.show();
		  
    }


	
	
}
	
