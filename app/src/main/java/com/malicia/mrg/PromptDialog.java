package com.malicia.mrg;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.AttributeSet;
import android.widget.EditText;

/** 
     * helper for Prompt-Dialog creation 
     */  
    public abstract class PromptDialog extends AlertDialog.Builder implements OnClickListener {  
    private static Context contextG = null; 
    private EditText input;  
    public static String TypeChamp_Numerique = "NUMERIQUE";
     /** 
      * @param context 
      * @param string resource id 
      * @param string2 resource id 
      */  
     public PromptDialog(Context context, String string, String string2 , String Default , String PromptTextViewType) {  
    	 super(context);  
    	 contextG = context;
    	 setTitle(string);  
    	 setMessage(string2);  

    	 Creation(context , Default , PromptTextViewType );
     }  

     public PromptDialog(Context context, Integer id, Integer id2 , String Default, String PromptTextViewType) {  
    	 super(context);  
    	 contextG = context;
    	 setTitle(id);  
    	 setMessage(id2);  

    	 Creation(context , Default , PromptTextViewType );
     }  
      

     private void Creation(Context context , String Default , String PromptTextViewType) {

    	 if (PromptTextViewType.equalsIgnoreCase(TypeChamp_Numerique)) {
        	 input = new EditText(context);  
    	 } else {
        	 input = new EditText(context);  
    	 }
    	 
		 input.setText(Default);
		 setView(input);  
		
		 setPositiveButton(R.string.ok, this);  
		 setNegativeButton(R.string.cancel, this);  
     }
     /** 
      * will be called when "cancel" pressed. 
      * closes the dialog. 
      * can be overridden. 
      * @param dialog 
      */  
     public void onCancelClicked(DialogInterface dialog) {  
      dialog.dismiss();  
     }  
      
     @Override  
     public void onClick(DialogInterface dialog, int which) {  
      if (which == DialogInterface.BUTTON_POSITIVE) {  
       if (onOkClicked(contextG , input.getText().toString())) {  
        dialog.dismiss();  
       }
      } else {  
       onCancelClicked(dialog);  
      }  
     }  
      
     /** 
      * called when "ok" pressed. 
      * @param input 
      * @return true, if the dialog should be closed. false, if not. 
      */  
     public abstract boolean onOkClicked(Context context , String input);  
    }  
