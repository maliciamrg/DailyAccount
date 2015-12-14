package com.malicia.mrg;

public class Fonction {
	
    public static boolean isIntNumber(String num){
        try{
        Integer.parseInt(num);
        } catch(NumberFormatException nfe) {
        return false;
        }
        return true;
        }

	public static boolean RetBoolean(String chaine) {
		if (chaine.compareTo("1") == 0 ){ 
			return true;
		}
		return false;
	}
}
