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

import java.util.Calendar;

import com.malicia.mrg.Fonction;
import com.malicia.mrg.android.market.licensing.popup_box;

import android.database.Cursor;
import android.graphics.Color;

public class _DataPool {

	public static int NbJourParMois = 30;
	
	public static String v_Nom_Compte				=	"Nom_Compte";
	public static String v_MtSalaireMensuel 		=	"MtSalaireMensuel";
	public static String v_ValJourDePaye			=	"ValJourDePaye";
	public static String v_MtPrelevementMensuel 	=	"MtPrelevementMensuel";
	public static String v_MtAEpargner 				=	"MtAEpargner";
	public static String v_NbBudget 				=	"NbBudget";
	public static String v_BudgetNom 				=	"BudgetNom";
	public static String v_BudgetMensuel 			=	"BudgetMensuel ";
	public static String v_BudgetJour 				=	"BudgetJour";
	public static String v_BrutCompte 				=	"BrutCompte";
	public static String v_SumBudget 				=	"SumBudget";
	
	public static void RazParam() {
		_OperationDbAdapter.deleteAllParam();
	}
	
	public static int NewBudget(Integer Compte ) {
		return NewBudget(Compte , 999);
	}
	
	public static int NewBudget(Integer Compte , Integer index ) {
		if (index==999){
			index = _DataPool.NextFreeBudget(Compte);
//			NbBudget(Compte,(NbBudget(Compte)+1));
//			index = NbBudget(Compte);
		}
		if (index==0){
			
			// Calcul du budget allDay
	        _DataPool.BudgetNom(NumBudget(Compte, index),"ByDay");
	        
	        //    Sum des budget du compte 
	        int SumBudget = 0;
	        for (int i=1; i<=NbBudget(Compte); i++) {
	        	SumBudget = SumBudget + Integer.valueOf(BudgetMois(NumBudget(Compte,i)));
	        }
	        SumBudget(Compte,String.valueOf(SumBudget));
	        
	        //    Montant Restant
	        int MtRestant = BrutCompte(Compte) - SumBudget(Compte);
	        if (MtRestant < 0 ) { MtRestant = 0; };
	        
	        _DataPool.BudgetMois(NumBudget(Compte, index),String.valueOf(MtRestant));
	        _DataPool.BudgetJour(NumBudget(Compte, index),MtBudgetJour(String.valueOf(MtRestant)));
	        return index;
		} else {
			// Valeur par default
	        _DataPool.BudgetNom(NumBudget(Compte, index),"Nouveau");
	        _DataPool.BudgetMois(NumBudget(Compte, index),"1500");
	        _DataPool.BudgetJour(NumBudget(Compte, index),MtBudgetJour("1500"));
	        return index;
		}
	}

	public static Boolean IsExistBudget ( String IndexBudget ) {
		String r = getParam(v_BudgetNom, false,"Vide" , IndexBudget) ;
		return !r.equals("Vide");			
	}
	

	public static Integer NextFreeBudget ( Integer IndexCompte ) {
		int r = _DataPool.NbBudget(IndexCompte) + 1 ;
	    for (int i=0; i<=r; i++) {
	    	String ret = getParam(v_BudgetNom, false,"Vide" , _DataPool.NumBudget(IndexCompte, i)) ;
	    	if (ret.equals("Vide")) {
	    		r = i;    		
	    	}
	    }
    	if (popup_box.Is_License_Ok || r == 0) {
    		return r ;
    	} else {
			return 1;
    	}
	}
	
	public static void DelBudget(Integer Compte , Integer Budget ) {
		if (Budget!=0){
		 	String id = NumBudget(Compte, Budget);
			_OperationDbAdapter.deleteParam(v_BudgetNom, id);
			_OperationDbAdapter.deleteParam(v_BudgetMensuel, id);
			_OperationDbAdapter.deleteParam(v_BudgetJour, id);
			//TODO report de operation sur le budget 0
			NbBudget(Compte,(NbBudget(Compte)-1));
		}
	}

	public static int NewCompte() {
		return NewCompte(999);
	}
	
	public static int NewCompte( Integer index ) {
		if (index==999){
			index = _DataPool.NextFreeCompte();
//			Param_Nb_CompteU(Param_Nb_CompteU()+1);
//			index = Param_Nb_CompteU();
		}

        _DataPool.Nom_Compte(index,"Nouveau");
        _DataPool.MtSalaireMensuel(index,"1200");
        _DataPool.ValJourDePaye(index,"27");
        _DataPool.MtPrelevementMensuel(index,"1000");
        _DataPool.MtAEpargner(index,"200");
        
        Recalcul_Compte(index);
        
        return index;
	}

	public static Boolean IsExistCompte ( String IndexCompte ) {
		String r = getParam(v_Nom_Compte, false,"Vide" , IndexCompte) ;
		return !r.equals("Vide");			
	}
	
	public static Integer NextFreeCompte (  ) {
		int r = _DataPool.Param_Nb_CompteU() + 1 ;
	    for (int i=0; i<=r; i++) {
	    	String ret = getParam(v_Nom_Compte, false,"Vide" , String.valueOf(i)) ;
	    	if (ret.equals("Vide")) {
	    		r = i;    		
	    	}
	    }
    	if (popup_box.Is_License_Ok || r == 0) {
    		return r ;
    	} else {
			return 1;
    	}
	}
		
	public static void DelCompte(String id) {
		_OperationDbAdapter.deleteParam(v_Nom_Compte, id);
		_OperationDbAdapter.deleteParam(v_MtSalaireMensuel, id);
		_OperationDbAdapter.deleteParam(v_ValJourDePaye, id);
		_OperationDbAdapter.deleteParam(v_MtPrelevementMensuel, id);
		_OperationDbAdapter.deleteParam(v_MtAEpargner, id);
		Param_Nb_CompteU(Param_Nb_CompteU()-1);
        for (int i=1; i<=NbBudget(Integer.valueOf(id)); i++) {
        	DelBudget(Integer.valueOf(id),i);
        }
	}


	public static int CompteColor ( Integer IndexCompte ) {
		if (IndexCompte == 0 ){
			return Color.DKGRAY;
		} else {
			switch (IndexCompte)
			{
			case 1: 
				return Color.rgb(200, 200, 255);	
			case 2: 
				return Color.rgb(200, 255, 200);	
			case 3: 
				return Color.rgb(255, 200, 200);	
			case 4: 
				return Color.CYAN;	
			case 5: 
				return Color.MAGENTA;	
			case 6: 
				return Color.GRAY;	
			default: 
				return Color.WHITE;
			}
		}
	}

	public static int BudgetColor ( String IndexBudget ) {
		String[] s = UndoNumBudget(IndexBudget);
		int IBudget = Integer.parseInt(s[3]);
		if (IBudget>5){IBudget=5;};
		int C = CompteColor(Integer.parseInt(s[1]));
		int R = Color.red(C);R = R - (IBudget * 40); if (R < 0) {R=0;};
		int G = Color.green(C);G = G - (IBudget * 40); if (G < 0) {G=0;};
		int B = Color.blue(C);B = B - (IBudget * 40); if (B < 0) {B=0;};
		return Color.rgb(R,G,B);
	}
	
    public static void Recalcul_Compte( Integer index ) {

    	//   Brut du compte
        BrutCompte( index , String.valueOf(Integer.valueOf(MtSalaireMensuel(index)) 
		 - Integer.valueOf(MtPrelevementMensuel(index)) 
		 - Integer.valueOf(MtAEpargner(index))));
	}
    	

    
    public static String FindBudgetNum(String NomBudget) {
        for (int i=1; i<=_DataPool.Param_Nb_CompteU(); i++) {
            for (int y=0; y<=_DataPool.NbBudget(i); y++) {
            	if (_DataPool.NomCompteBudget(i, y).compareTo(NomBudget)==0){
            		return _DataPool.NumBudget(i, y);
            	};
            }
        }
        return _DataPool.NumBudget(1,0);
    }

    public static Boolean IsIndexBudget(String IndexBudget) {
    	String[] s = UndoNumBudget(IndexBudget);
    	if (Fonction.isIntNumber(s[1]) && Fonction.isIntNumber(s[3])){
    		Integer ICompte = Integer.parseInt(s[1]);
    		Integer IBudget = Integer.parseInt(s[3]);
    		if (_DataPool.Param_Nb_CompteU() < ICompte){
    			// creation des compte manquant
        		for (int i=(_DataPool.Param_Nb_CompteU()+1); i<=ICompte; i++) {
        			_DataPool.NewCompte();
    	        }
    		}
    		if (_DataPool.NbBudget(ICompte) < IBudget){
    			// creation des budget manquant
        		for (int i=(_DataPool.NbBudget(ICompte)+1); i<=IBudget; i++) {
        			_DataPool.NewBudget(i,IBudget);
    	        }
    		}
	        return true;
    	} else {
    		_OperationDbAdapter.updateAllBudget(IndexBudget,_DataPool.NumBudget(1, 0));
    		return false;
    	}
    }

    public static String[] EnteteCompte(int IndexCompte , int IndexBudget) {
        //Periode commence a Budget_Date_Bascule du mois precedente
		//            fini a la date du jour
		//0 = Date du jour
		//1 = Date de debut de periode
		//2 = Total compte sur la periode Tous budget
		//3 = Total compte sur la periode Budget
		//4 = Affichage Budget/all Budget du compte
		//5 = Budget sur la periode Compte budget
		//6 = Budget sur la periode Compte
		//7 = Balance sur la periode Compte budget
		//8 = Balance sur la periode Compte
		//9 = Affichage Balance/all Balance du compte
		//10 = Affichage Date du jour + restant
		//11 = Affichage restant
		//12 = Affichage Budget/Affichage Balance
		//13 = all Budget du compte/all Balance du compte
    	String[] Ret = {"","","","","","","","","","","","","","",""};	
		
		// Date du jour   
        java.text.DecimalFormat Month2digit = new java.text.DecimalFormat("00"); 
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        String mMonth = Month2digit.format(c.get(Calendar.MONTH )+1);
        String mDay = Month2digit.format(c.get(Calendar.DAY_OF_MONTH));
        Ret[0] = (mYear + "-" + mMonth + "-" + mDay);

		// Date de debut de periode     
        int NbJour;
        int num_cpt;
		int nDay = Integer.parseInt(mDay);
		int nMonth = Integer.parseInt(mMonth);
		String dDay;
		String dMonth;
		String dYear;
		String dDate;
		int Budget_Date_Bascule = Integer.parseInt(_DataPool.ValJourDePaye(IndexCompte));
		if (nDay  > Budget_Date_Bascule ) {
			NbJour = (nDay - Budget_Date_Bascule);
			dDay = Month2digit.format(Budget_Date_Bascule);
			dMonth = Month2digit.format(nMonth);
			dYear= "" + mYear;
		} else {
			NbJour = (nDay + (30 - Budget_Date_Bascule));
			dDay =Month2digit.format(Budget_Date_Bascule);
			if (nMonth < 2) {
				dMonth = "12";
				dYear= "" + (mYear -1);
			} else {
				dMonth = Month2digit.format(nMonth - 1);
				dYear= "" + mYear;
			}
		}
		Ret[1] = dYear + "-" + dMonth + "-" + dDay ;
		
		//Total compte sur la periode tous budget
        Cursor SumAmountCpt = _OperationDbAdapter.SumAmountCpt(Ret[1],IndexCompte ); 
		SumAmountCpt.moveToFirst();
        if (SumAmountCpt.getCount() > 0 ) { 
     	   try  
     	   {  
     		Ret[2] =  SumAmountCpt.getString(0);
     	   }
    	   catch( Exception e )  
    	   {  
    	   }  
     	}
        if (Ret[2]==null){Ret[2]="0";};
        if (Ret[2]==""){Ret[2]="0";};
        SumAmountCpt = null;
		
        //Total compte sur la periode budget
        Cursor SumAmountBudget = _OperationDbAdapter.SumAmountBudget(Ret[1],_DataPool.NumBudget(IndexCompte,IndexBudget ));
        SumAmountBudget.moveToFirst();
        if (SumAmountBudget.getCount() > 0 ) { 
     	   try  
     	   {  
     		Ret[3] =  SumAmountBudget.getString(0);
     	   }
    	   catch( Exception e )  
    	   {  
    	   }  
     	}
        if (Ret[3]==null){Ret[3]="0";};
        if (Ret[3]==""){Ret[3]="0";};
        SumAmountBudget = null;

        //
        int P1;
        int P2; 
        if (Float.parseFloat(Ret[3]) != 0) {P1 = ((Math.round((Float.parseFloat(Ret[3])*100))/100));} else {P1 = 0;}
        if (Float.parseFloat(Ret[2]) != 0) {P2 = ((Math.round((Float.parseFloat(Ret[2])*100))/100));} else {P2 = 0;}
        Ret[4] = "" + P1 + "(" + P2 + ")";
        
        
		//Budget sur la periode compte budget
        Ret[5] = String.valueOf(NbJour * _DataPool.BudgetJour(_DataPool.NumBudget(IndexCompte,IndexBudget )));
        
        //budget sur la periode compte
        Ret[6] = String.valueOf(NbJour * Float.valueOf(_DataPool.MtBudgetJour(String.valueOf(_DataPool.BrutCompte(IndexCompte)))));

        //Balance
        int P21;
        int P22; 
        Ret[7] = String.valueOf(Float.parseFloat(Ret[5]) - Float.parseFloat(Ret[3]));
        Ret[8] = String.valueOf(Float.parseFloat(Ret[6]) - Float.parseFloat(Ret[2])); 
        if (Float.parseFloat(Ret[7]) != 0) {P21 = ((Math.round((Float.parseFloat(Ret[7]))*100))/100);} else {P21 = 0;}
        if (Float.parseFloat(Ret[8]) != 0) {P22 = ((Math.round((Float.parseFloat(Ret[8]))*100))/100);} else {P22 = 0;}
        Ret[9] = "" + P21 + "/" + P22 + "";

        //Affichage Date du jour + restant     
        Ret[10] = mYear + "-" + mMonth + "-" + mDay + " [" + NbJour + "]";

        //Affichage Date du jour + restant     
        Ret[11] = "[" + NbJour + "]";

        Ret[12] = "" + P22 + "(" + P2 + ")";
        Ret[13] = "" + P21 + "(" + P1 + ")";

        return Ret;
    }

    
    
	//*************//
	// FETCH PARAM //
	//*************//
	private static String fetchParamLike (String Champ , String Indice , String NumeroOccurence) {
		
		Cursor Param;
		String retour = "";
		
		Param = _OperationDbAdapter.selectParamLike(Champ , Indice);
		
		if ( Param.getCount() > 0 & Param.getCount() >= Integer.parseInt(NumeroOccurence)) {
			Param.moveToFirst();
			for (int i=2; i<=Integer.parseInt(NumeroOccurence); i++) {	
				Param.moveToNext();
			}
			retour = Param.getString(3);
		}
		
		Param.close();
		
		return retour;

	}
	private static String fetchParamBudgetMax (String Champ , String Indice ) {
		
		Cursor Param;
		String retour = "";
		Indice = Indice + "%";
		Param = _OperationDbAdapter.selectParamLike(Champ , Indice);
		
		if ( Param.getCount() > 0 ) {
			Param.moveToFirst();
			retour = Param.getString(2);
			for (int i=2; i<=Param.getCount(); i++) {	
				Param.moveToNext();
				if ( Param.getString(2).compareTo(retour) > 0) {
					retour = Param.getString(2);
				}
			}
		}
		
		Param.close();
		
		return retour;

	}
	private static String fetchParamCompteMax (String Champ , String Indice ) {
		
		Cursor Param;
		int retour = 0;
		Indice = Indice + "%";
		Param = _OperationDbAdapter.selectParamLike(Champ , Indice);
		
		if ( Param.getCount() > 0 ) {
			Param.moveToFirst();
			try {retour = Integer.parseInt(Param.getString(2));}catch (Exception e){retour=0;}
			for (int i=2; i<=Param.getCount(); i++) {	
				Param.moveToNext();
				if ( Param.getString(2).compareTo(String.valueOf(retour)) > 0) {
					try {retour = Integer.parseInt(Param.getString(2));}catch (Exception e){}
				}
			}
		}
		
		Param.close();
		
		return String.valueOf(retour);

	}

	//***********//
	// GET PARAM //
	//***********//
	private static String getParam (String Champ , Boolean  Type_Integer  ) {
		
		return getParam(Champ,Type_Integer,"","0");

	}
	private static String getParam (String Champ , Boolean  Type_Integer , String Indice  ) {
		
		return getParam(Champ,Type_Integer,"",Indice);

	}
	private static String getParam(String Champ, boolean Type_Integer,Integer Indice) {
		
		return getParam(Champ,Type_Integer,"",String.valueOf(Indice));

	}
	private static String getParam (String Champ , String Default_Value  ) {
		
		return getParam(Champ,false,Default_Value,"0");

	}
	private static String getParam (String Champ , Boolean  Type_Integer , String Default_Value , String Indice) {
		
		Cursor Param;
		String retour;
		
		Param = _OperationDbAdapter.selectParam( Champ , Indice);
		
       
		if ( Param.getCount() > 0 ) {
			Param.moveToFirst();
			retour = Param.getString(3);
		} else if ( !Default_Value.equalsIgnoreCase("") ) {
			retour = Default_Value;
		} else if (Type_Integer) {
			retour = "0";	
		} else {
			retour = "";
		}
		
		Param.close();
		
		return retour;

	}
	
	
	
	
	//***********//
	// SET PARAM //
	//***********//
	private static void setParam (String Champ , String Valeur ) {
		setParam (Champ ,Valeur ,"0");
	}
	
	static void setParam (String Champ , String Valeur  , String indice) {
		
		Cursor Param;
		
		Param = _OperationDbAdapter.selectParam( Champ , indice);
		int Ret = Param.getCount();
		Param.close();
		if ( Ret > 0 ) {;
			_OperationDbAdapter.updateParam( Champ , indice, Valeur );
		} else {
			_OperationDbAdapter.insertParam( Champ , indice, Valeur );
		}
	}
	
	
	
	
	
	public static String NomCompteBudget ( Integer Compte , Integer index) {
		return "" + Nom_Compte(Compte) + "|" + BudgetNom(NumBudget(Compte, index));
	}
	public static String NomCompteBudget ( String Compte , String index) {
		return "" + Nom_Compte(Integer.parseInt(Compte)) + "|" + BudgetNom(NumBudget(Compte, index));
	}
	public static String NomCompteBudget ( String indexCompte ) {
		String [] s = UndoNumBudget(indexCompte);
		return "" + Nom_Compte(Integer.parseInt(s[1])) + "|" + BudgetNom(indexCompte);
	}
	public static String NumBudget ( Integer Compte , Integer index) {
		return "" + String.valueOf(Compte) + "|" + String.valueOf(index);
	}
	public static String NumBudget ( String Compte , String index) {
		return "" + Compte + "|" + index;
	}
	public static String[] UndoNumBudget ( String IndexBudget) {
		String[] s = IndexBudget.split("|");
		return s;
	}
		
	
	public static String MtBudgetJour ( String MtMens ) {
		if (Integer.valueOf(MtMens) > 0) {
			return Float.toString(Integer.parseInt(MtMens) / NbJourParMois);
		}
		return "0";
	}	
	
	public static Integer SumBudget (Integer index) {
		return Integer.parseInt(getParam(v_SumBudget,true,index)) ;
	}
	public static void SumBudget (Integer index , String Valeur ) {
		setParam (v_SumBudget  , Valeur , String.valueOf(index));
	}
	
	public static Integer BrutCompte (Integer index) {
		return Integer.parseInt(getParam(v_BrutCompte,true,index)) ;
	}
	public static void BrutCompte (Integer index , String Valeur ) {
		setParam (v_BrutCompte  , Valeur , String.valueOf(index));
	}
	
	
	
	
	
	public static Integer Param_nb_jour_graph () {
		return Integer.parseInt(getParam("Param_nb_jour_graph","60")) ;
	}
	public static void Param_nb_jour_graph (Integer Valeur) {
		setParam ("Param_nb_jour_graph" , String.valueOf(Valeur) );
	}
	public static String Param_Compte_Principal () {
		return getParam("Param_Compte_Principal","1|0") ;
	}
	public static void Param_Compte_Principal (String Compte) {
		setParam ("Param_Compte_Principal" , Compte );
	}	
//	public static String Param_Compte_Principal () {
//		return getParam("Param_Compte_Principal","Nouveau") ;
//	}
//	public static void Param_Compte_Principal (String Valeur) {
//		setParam ("Param_Compte_Principal" , Valeur );
//	}

	public static Integer Param_Nb_CompteU () {
		String IndexCompte = fetchParamCompteMax(v_Nom_Compte,"");
    	if (popup_box.Is_License_Ok || Integer.valueOf(IndexCompte) == 0 ) {
    		return Integer.valueOf(IndexCompte) ;
    	} else {
			return 1;
    	}
//		return Integer.parseInt(getParam("Param_Nb_CompteU",true)) ;
	}
	public static void Param_Nb_CompteU (Integer Valeur) {
		setParam ("Param_Nb_CompteU" , String.valueOf(Valeur) );
	}

	public static String Nom_Compte (Integer index) {
		return getParam(v_Nom_Compte,false ,"Nouveau Compte",String.valueOf(index)) ;
	}
	public static void Nom_Compte (Integer index , String Valeur ) {
		setParam (v_Nom_Compte  , Valeur , String.valueOf(index));
	}

	public static String MtSalaireMensuel (Integer index) {
		return getParam(v_MtSalaireMensuel,true,index) ;
	}
	public static void MtSalaireMensuel (Integer index , String Valeur ) {
		setParam (v_MtSalaireMensuel  , Valeur , String.valueOf(index));
        Recalcul_Compte(index);
	}

	public static String ValJourDePaye (Integer index) {
		return getParam(v_ValJourDePaye,true,"27",String.valueOf(index)) ;
	}


	public static void ValJourDePaye (Integer index , String Valeur ) {
		setParam (v_ValJourDePaye  , Valeur , String.valueOf(index));
	}
	
	public static String MtPrelevementMensuel (Integer index) {
		return getParam(v_MtPrelevementMensuel,true,index) ;
	}
	public static void MtPrelevementMensuel (Integer index , String Valeur ) {
		setParam (v_MtPrelevementMensuel  , Valeur , String.valueOf(index));
        Recalcul_Compte(index);
	}

	public static String MtAEpargner (Integer index) {
		return getParam(v_MtAEpargner,true,index) ;
	}
	public static void MtAEpargner (Integer index , String Valeur ) {
		setParam (v_MtAEpargner  , Valeur , String.valueOf(index));
        Recalcul_Compte(index);
	}

	
	
	public static Integer NbBudget (Integer index) {
//		String s = getParam(v_NbBudget,true,String.valueOf(index));
		String IndexBudget = fetchParamBudgetMax(v_BudgetNom,String.valueOf(index));
		String s[] = UndoNumBudget(IndexBudget);
    	if (popup_box.Is_License_Ok || Integer.parseInt(s[3])== 0) {
    		return Integer.parseInt(s[3]) ;
    	} else {
			return 1;
    	}
	}
	public static void NbBudget (Integer index , Integer Valeur ) {
		setParam (v_NbBudget  , String.valueOf(Valeur) , String.valueOf(index));
	}
	
	public static String BudgetNom (String index) {
		return getParam(v_BudgetNom, false,"Nom Budget" , index) ;
	}
	public static void BudgetNom (String index , String Valeur ) {
		setParam (v_BudgetNom  , Valeur , index);
	}
	
	public static Integer BudgetMois (String index) {
		return Integer.parseInt(getParam(v_BudgetMensuel, true,index)) ;
	}
	public static void BudgetMois (String index , String Valeur ) {
		setParam (v_BudgetMensuel  , Valeur , index);
	}

	public static Float BudgetJour (String index) {
		return Float.parseFloat(getParam(v_BudgetJour, true , index)) ;
	}
	public static void BudgetJour (String index , String Valeur ) {
		setParam (v_BudgetJour , Valeur , index);
	}
	
}
