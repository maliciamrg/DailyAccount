package com.malicia.mrg.android.apps.dailyaccount;

public class Compte {
    
	public int NbChamps = 6;
	public String Champs [][] = 
		{{
			"Nom_Compte" , 
			"MtSalaireMensuel" ,
			"ValJourDePaye" , 
			"MtPrelevementMensuel" , 
			"MtAEpargner" , 
			"Activer" 
		} ,
		{
			"" , 
			"" ,
			"" , 
			"" , 
			"" , 
			"" 
		}} ;	

    
    public void Initialize(String startNom_Compte, String startMtSalaireMensuel, String startValJourDePaye, String startMtPrelevementMensuel, String startMtAEpargner , String string) {
    	Champs[0][1] = startNom_Compte;
    	Champs[1][1] = startMtSalaireMensuel;
    	Champs[2][1] = startValJourDePaye;
    	Champs[3][1] = startMtPrelevementMensuel;
    	Champs[4][1] = startMtAEpargner;
    	Champs[5][1] = String.valueOf(string);
    }
    
    public String getNom_Compte() 							{ return Champs[0][1];   					}
    public void setNom_Compte(String newValue) 				{ Champs[0][1] = newValue; 					}
    public String getMtSalaireMensuel() 					{ return Champs[1][1];   					}
    public void setMtSalaireMensuel(String newValue) 		{ Champs[1][1] = newValue; 					}
    public String getValJourDePaye() 						{ return Champs[2][1];   					}
    public void setValJourDePaye(String newValue) 			{ Champs[2][1] = newValue; 					}
    public String getMtPrelevementMensuel() 				{ return Champs[3][1];   					}
    public void setMtPrelevementMensuel(String newValue) 	{ Champs[3][1] = newValue; 					}
    public String getMtAEpargner() 							{ return Champs[4][1];   					}
    public void setMtAEpargner(String newValue) 			{ Champs[4][1] = newValue; 					}
    public Boolean getActiver() 							{ return Boolean.valueOf(Champs[5][1]);   	}
    public void setActiver(Boolean newValue) 				{ Champs[5][1] = String.valueOf(newValue); 	}
    
    
}