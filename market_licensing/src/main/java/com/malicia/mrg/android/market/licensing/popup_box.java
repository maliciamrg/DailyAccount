package com.malicia.mrg.android.market.licensing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.vending.licensing.AESObfuscator;
import com.android.vending.licensing.LicenseChecker;
import com.android.vending.licensing.LicenseCheckerCallback;
import com.android.vending.licensing.ServerManagedPolicy;

import java.util.Calendar;

public class popup_box {
	
    private static final String PREFS_NAME = "Popup_Box_PrefsFile";
    private static long DtJour ;
    private static long Jour = 24 * 3600 * 1000 ;
    
	public static Boolean Is_License_Ok = false ;
	public static String License_txt = "" ;
	
    private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArj3nKA34RF+P3vwErA4155WUaxxbbfVsksNl8zJmw9X7dT9tn68kFgPPzrJc1UUrMk0EGCsQKGQXQT/DXxiSUSrLlVqiI0jBzF5LfpJPZbnJ8h+y3UQx1fu0o6MPloMxItIy9LEdNRRMHxMRP2Rf9t4sTuSKS2Okgfc+d/BkuxZEiVFRijeVyxFmO9/MzcdthQy2/7UoTsmuBrmGzivE8mEQjsBZtesg7IQkdMyQXKCDZbd56RsCoQg8IcXriTWfskorz0vTMHIaG94pxI27d1Mt/6sIFiXr80dou59Uf9pHHnFTLLXktL6JOYTsnM9fLGYAv7UaHdyCx/x+gtLhvQIDAQAB";

    // Generate your own 20 random bytes, and put them here.
    private static final byte[] SALT = new byte[] {
        -46, 65, 30, -128, -103, -57, 74, -64, 51, 88, -95, -45, 77, -117, -36, -113, -11, 32, -64,
        89
    };

    private static TextView mStatusText;
    //private static Button mCheckLicenseButton;

    private static LicenseCheckerCallback mLicenseCheckerCallback;
    private static LicenseChecker mChecker;
    // A handler on the UI thread.
    private static Handler mHandler;

    private static Context ctx2;
	private static ProgressBar progressBar1;
	private static AlertDialog ad;
	private static View View;
	private static Boolean Show_i;
	
	public static void Start_new(Object obj_this, Boolean Show , String App_name) {

		ctx2 = (Context) obj_this;
		Show_i = Show;
        Is_License_Ok = false;
        License_txt = App_name;
		
		mHandler = new Handler();

		//gestion sauvegarde
	    SharedPreferences settings = ctx2.getSharedPreferences(PREFS_NAME, 0);
        long DateSvgOK = settings.getLong("date", 0);
        //comparairon vaec la date du jour
        DtJour = Calendar.getInstance().getTime().getTime();
/*        final Calendar c = Calendar.getInstance();
    	String DATE_FORMAT = "yyyyMMdd";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        String mYear = String.valueOf(c.get(Calendar.YEAR));
        String mMonth = String.valueOf(c.get(Calendar.MONTH));
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        DtJour = Long.parseLong( mYear + mMonth + mDay) ;
*/	    
        if ((DateSvgOK + (10 * Jour)) < DtJour) {
        	
	        // Try to use more data here. ANDROID_ID is a single point of attack.
	        String deviceId = Secure.getString(ctx2.getContentResolver(), Secure.ANDROID_ID);
	        // Library calls this when it's done.
	        mLicenseCheckerCallback = new MyLicenseCheckerCallback();
	        // Construct the LicenseChecker with a policy.
	        mChecker = new LicenseChecker(
	        		ctx2, new ServerManagedPolicy(ctx2,
	                new AESObfuscator(SALT, ctx2.getPackageName(), deviceId)),
	            BASE64_PUBLIC_KEY);
	        
	        if  (((DateSvgOK + (30 * Jour )) < DtJour ) || (DateSvgOK==0)) {
	        	Show_i = true ;
	        }
	        
	        if (Show_i){
	
	        	LayoutInflater li = LayoutInflater.from(ctx2);
	    		View = li.inflate(R.layout.popup, null);
	    		
	    		AlertDialog.Builder builder = new AlertDialog.Builder(ctx2);
	    		
	    		builder.setTitle(R.string.Verification_License);
	    		
	    		builder.setView(View);
	    		
	    		progressBar1 = (ProgressBar) View.findViewById(R.id.progressBar1);
	    		mStatusText = (TextView ) View.findViewById(R.id.textView1);
	    		
	    		ad  = builder.create();
	    		
	    		ad.show();
	    		
	    		//redefinir la taille
	    		
	    	    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    	    lp.copyFrom(ad.getWindow().getAttributes());
	    	    lp.width = WindowManager.LayoutParams.FILL_PARENT;
	    	    lp.height = WindowManager.LayoutParams.FILL_PARENT;
	    	    ad.getWindow().setAttributes(lp);	
	        	
	        }
	
		// go
		    doCheck();
	        
        } else {
            Is_License_Ok = true;        	
        }
		    
	}
	    

 /*   protected Dialog onCreateDialog(int id) {
        // We have only one dialog.
        return new AlertDialog.Builder(ctx2)
            .setTitle(com.android.vending.licensing.R.string.unlicensed_dialog_title)
            .setMessage(com.android.vending.licensing.R.string.unlicensed_dialog_body)
            .setPositiveButton(com.android.vending.licensing.R.string.buy_button, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                        "http://market.android.com/details?id=" + ctx2.getPackageName()));
                    ctx2.startActivity(marketIntent);
                }
            })
            .setNegativeButton(com.android.vending.licensing.R.string.quit_button, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
//                    finish();
                }
            })
            .create();
    }
*/
	
    private static void doCheck() {
//        mCheckLicenseButton.setEnabled(false);
//        setProgressBarIndeterminateVisibility(true);
        if (Show_i){
        	mStatusText.setText(R.string.checking_license);
        	progressBar1.invalidate();
        }
        mChecker.checkAccess(mLicenseCheckerCallback);
    }

    private static void displayResult(final String result) {
        mHandler.post(new Runnable() {
            public void run() {
            	License_txt = License_txt + " " + result;
                if (Show_i){
                	mStatusText.setText(result);
                	if (Is_License_Ok) {
                		ad.cancel();
                	}
                } 

//                setProgressBarIndeterminateVisibility(false);
//                mCheckLicenseButton.setEnabled(true);
            }
        });
    }

    private static class MyLicenseCheckerCallback implements LicenseCheckerCallback {

    	public void allow() {
            Is_License_Ok = true;
            displayResult(ctx2.getString(R.string.allow));
            mChecker.onDestroy();
            // stockage prefs
            SharedPreferences settings = ctx2.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putLong("date", DtJour);
            editor.commit();
//            License_txt = License_txt;
        }

        public void dontAllow() {
            Is_License_Ok = false;            
            displayResult(ctx2.getString(R.string.dont_allow));
            mChecker.onDestroy();
//            License_txt = License_txt + " " + ctx2.getString(com.android.vending.licensing.R.string.dont_allow);
//            ((Activity) obj_this2).setTitle(popup_box.License_txt);
//            ad.cancel();        
        }

        public void applicationError(ApplicationErrorCode errorCode) {
            Is_License_Ok = false;            
            String result = String.format(ctx2.getString(R.string.application_error), errorCode);
            displayResult(result);
            mChecker.onDestroy();
//            License_txt = License_txt + " " + result;
//            ((Activity) obj_this2).setTitle(popup_box.License_txt);
//            ad.cancel();
        }

    }


}
