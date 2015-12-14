/*
 *
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.malicia.mrg.Liste_de_Choix;
import com.malicia.mrg.android.market.licensing.popup_box;

public class DailyAccount extends ListActivity {
	
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;

    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
	private static final int DELETE_ALL = Menu.FIRST + 2;
	private static final int EXPORT_ALL = Menu.FIRST + 3;
	private static final int Edit_Param = Menu.FIRST + 4;
	private static final int graph = Menu.FIRST + 6;
	private static final int IMPORT_ALL = Menu.FIRST + 7;
	private static final int Raz_Param = Menu.FIRST + 8;
//	private static final int Compte_Param = Menu.FIRST + 9;
	private static Context context = null;
	
	private String Operations = "Export.qif";


//	public static _OperationDbAdapter mDbHelper;
//	public static _DataPool DP;
	public static String LineType ;
	private static TextView textglob1;
	private static TextView textglob2;
	private static TextView textglob3;
	private static TextView textglob4;
	private static TextView textglob5;
	private static TextView textglob6;

//	private FileWriter filewriter;
	private OutputStreamWriter osw;
//	private Intent achartIntent;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        mDbHelper = new _OperationDbAdapter(this);
//        DP = new _DataPool();
//        mDbHelper.open();
        _OperationDbAdapter.open(this);

        context = this;
        
        textglob1 = (TextView) findViewById(R.id.textglob1 );
        textglob2 = (TextView) findViewById(R.id.textglob2 );
        textglob3 = (TextView) findViewById(R.id.textglob3 );
        textglob4 = (TextView) findViewById(R.id.textglob4 );
        textglob5 = (TextView) findViewById(R.id.textglob5 );
        textglob6 = (TextView) findViewById(R.id.textglob6 );
        
        textglob1.setOnTouchListener(new CustomTouchListener());
        textglob2.setOnTouchListener(new CustomTouchListener());
        textglob3.setOnTouchListener(new CustomTouchListener());
        textglob4.setOnTouchListener(new CustomTouchListener());
        textglob5.setOnTouchListener(new CustomTouchListener());
        textglob6.setOnTouchListener(new CustomTouchListener());
        
  //      Liste_de_Choix.Start_new(context, R.layout.masterlist, R.id.listviewperso) ;                


  //****************************************************************************     
  //*                                                                          *     
  // hook pour jump ecrande config budget
//        Intent j = new Intent(this, Param2.class );
//        j.putExtra("RowId", 1);
//        startActivityForResult(j, ACTIVITY_CREATE);
  // a reactiver apres hook  

//        popup_box pbox = null;
//		pbox.Start_new(context,false,this.getString(R.string.app_name));
//        this.setTitle(pbox.License_txt);

        fillData();
        registerForContextMenu(getListView());

  //*                                                                          *     
  //****************************************************************************     
    }
    
	public static void fillEntete() {
//      refonte avec datapool
		String[] S = _DataPool.UndoNumBudget(_DataPool.Param_Compte_Principal());
		String[] Ret =_DataPool.EnteteCompte(Integer.parseInt(S[1]),Integer.parseInt(S[3]));
//      textglob3.setText(Ret[9]);
        textglob1.setText(_DataPool.Nom_Compte(Integer.parseInt(S[1]))); 
        textglob2.setText(Ret[0]); 
        textglob3.setText(Ret[12]); 
        textglob4.setText(_DataPool.BudgetNom(_DataPool.NumBudget(S[1], S[3])));
        textglob5.setText(Ret[11]);
        textglob6.setText(Ret[13]);

        textglob1.setTextColor(_DataPool.BudgetColor(_DataPool.Param_Compte_Principal()));
        textglob2.setTextColor(_DataPool.BudgetColor(_DataPool.Param_Compte_Principal()));
        textglob3.setTextColor(_DataPool.BudgetColor(_DataPool.Param_Compte_Principal()));
        textglob4.setTextColor(_DataPool.BudgetColor(_DataPool.Param_Compte_Principal()));
        textglob5.setTextColor(_DataPool.BudgetColor(_DataPool.Param_Compte_Principal()));
        textglob6.setTextColor(_DataPool.BudgetColor(_DataPool.Param_Compte_Principal()));
        Ret = null;
        
//    	if (!pbox.Is_License_Ok) {
//    		((Activity) context).setTitle( context.getText(R.string.app_name) + " [Unregistered]");
//    	}
	}

	private void fillData() {

		fillEntete();
	       
	    Cursor OperationsCursor = _OperationDbAdapter.fetchAllOperations();
        startManagingCursor(OperationsCursor);

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{_OperationDbAdapter.KEY_Budget,_OperationDbAdapter.KEY_DATE,_OperationDbAdapter.KEY_PAYEE,_OperationDbAdapter.KEY_AMOUNT};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text4,R.id.text1,R.id.text2,R.id.text3};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter Operations = 
            new _MySimpleCursorAdapter( this, R.layout.main_list, OperationsCursor, from, to) ;

        setListAdapter(Operations);

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//      Menu sous forme de liste
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID	, 0, R.string.menu_insert).setIcon(R.drawable.add);
        menu.add(1, Edit_Param	, 0, R.string.Edit_Param).setIcon(R.drawable.config);
        menu.add(2, Raz_Param	, 0, R.string.menu_Raz_Param).setIcon(R.drawable.corbeille);
        menu.add(3, IMPORT_ALL	, 0, R.string.menu_import_all).setIcon(R.drawable.databaseimport);
        menu.add(4, EXPORT_ALL	, 0, R.string.menu_export_all).setIcon(R.drawable.databaseexport);
        menu.add(5, DELETE_ALL	, 0, R.string.menu_delete_all).setIcon(R.drawable.databasedelete);
//        menu.add(6, Compte_Param, 0, R.string.Compte_Param);
//    	if (pbox.Is_License_Ok) {
    		menu.add(7, graph, 0, R.string.Graph);        
//    	}
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case INSERT_ID:
                createNote();
                return true;
/*        	case Compte_Param:
        		CompteParam();
	            return true;
*/	        case Edit_Param:
        		EditParam();
	            return true;
        	case graph:
        		TraceGraph();
	            return true;
            case DELETE_ALL:
            	_OperationDbAdapter.deleteAllNote();
                fillData();
                return true;
            case IMPORT_ALL:
            	CallimportAllNote();
                fillData();
                return true;
            case EXPORT_ALL:
            	CallexportAllNote();
            	return true;
            case Raz_Param:
            	_DataPool.RazParam();
            	return true;            	
        }

        return super.onMenuItemSelected(featureId, item);
    }

    private void CallimportAllNote() {
		try {
			importAllNote();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
    
    private void importAllNote() throws IOException{

    	String dat = null;
    	String payee = null;
    	String amount = null;
    	String IndexBudget = null;
    	String memo = null;
    	int lng ;
    	
    	FileInputStream fIn = openFileInput(Operations);
        InputStreamReader isr = new InputStreamReader(fIn);
        BufferedReader bufRead = new BufferedReader(isr);

        String line = bufRead.readLine();
       
/*
	    File file = new File(Environment.getExternalStorageDirectory(), Operations);
	    FileReader input = new FileReader(file);
	    BufferedReader bufRead = new BufferedReader(input);
    	String line = (String) bufRead.readLine();
*/

		while (line != null){
	    	lng = line.length();
	    	String VTest = line.substring(0,1);
	    	if (line.compareTo("") != 0){
				if ( VTest.compareTo("D") == 0  ){ 
					dat = line.substring(1, lng); 
				}
				if ( VTest.compareTo("P") == 0 ){ 
					payee = line.substring(1, lng); 
				}
				if ( VTest.compareTo("T") == 0 ){ 
					amount = String.valueOf( -1 * Float.parseFloat(line.substring(1, lng))); 
				}
				if ( VTest.compareTo("M") == 0  ){ 
					// par default les operation sont sur le premier compte et le budget allday
					IndexBudget = _DataPool.NumBudget(1,0);
					// Memo par default
					memo = line.substring(1, lng); 
//     				"M" + "[" + Index Compte budget + "]" + "[" + nom Compte budget + "]" + memo + "\n"
					String s = line.substring(1, lng); 
					if (s.compareTo("") != 0 ){
						IndexBudget = "";
						String nBudget = "";
						StringTokenizer st = new StringTokenizer(s,"[]");
						while (st.hasMoreTokens()) {
							if (st.hasMoreTokens()) {
								 IndexBudget 	= st.nextToken();
								 if (_DataPool.IsIndexBudget(IndexBudget)){
									 memo = "";
								 } else {
									 IndexBudget = _DataPool.NumBudget(1,0);
								 }
							};
							if (st.hasMoreTokens()) {nBudget 		= st.nextToken();};
							if (st.hasMoreTokens()) {memo    		= st.nextToken();};
						}
						
					}
				}
				if ( VTest.compareTo("^") == 0  ){ 
					_OperationDbAdapter.createNote(dat, payee , amount , memo , IndexBudget ); 
			    	dat = null;
			    	payee = null;
			    	amount = null;
			    	memo = null;
					IndexBudget = null;
				}
			}
	    	line = bufRead.readLine();
	    }

	}

    private void CallexportAllNote() {
		try {
			exportAllNote();
		} catch (IOException e1) {
			e1.printStackTrace();
			Toast toast = Toast.makeText(context, R.string.exportout , Toast.LENGTH_SHORT);
			toast.show();
		}
    }
    
    private void exportAllNote() throws IOException {
    	String mt;
    	Cursor OperationsCursor = _OperationDbAdapter.fetchAllOperations();
	    OperationsCursor.moveToFirst();
	    
	    if (OperationsCursor.getCount() > 0 ) {
	    	
		    String Operations ="Export.qif";
           
            FileOutputStream fOut = openFileOutput(Operations,
                                                        MODE_WORLD_READABLE);
            osw = new OutputStreamWriter(fOut); 

                
/*		    File test = new File(Environment.getExternalStorageDirectory(), Operations);
		    
		    Boolean ret = new File(Environment.getExternalStorageDirectory(), Operations).delete();		    

		    File file = new File(Environment.getExternalStorageDirectory(), Operations);		    
		    file.createNewFile();
		    filewriter = new FileWriter(file,false);
*/	
//		    filewriter.write("!Type:Cash" + "\n");
            osw.write("!Type:Cash" + "\n");
		    mt = String.valueOf( -1 * Float.parseFloat(OperationsCursor.getString(3)));
		    ecrire(OperationsCursor.getString(1), OperationsCursor.getString(2),mt,OperationsCursor.getString(4),OperationsCursor.getString(5));
	        for(OperationsCursor.moveToFirst(); OperationsCursor.moveToNext(); OperationsCursor.isAfterLast()) {
			    mt = String.valueOf( -1 * Float.parseFloat(OperationsCursor.getString(3)));
			    ecrire(OperationsCursor.getString(1),OperationsCursor.getString(2), mt ,OperationsCursor.getString(4),OperationsCursor.getString(5));
	        }
		    
//		    filewriter.close();

            osw.flush();
            osw.close();
	    }

	}

	private void ecrire(String dat , String payee , String amount , String memo , String IndexBudget) throws IOException {
	    
		osw.write("D" + dat + "\n");		
		osw.write("P" + payee + "\n");		
		osw.write("T" + amount + "\n");		
//     "M" + "[" + Index Compte budget + "]" + "[" + nom Compte budget + "]" + memo + "\n"
		osw.write("M" + "[" + IndexBudget + "]" + "[" + _DataPool.NomCompteBudget(IndexBudget) + "]" + memo + "\n");		
		osw.write("^" + "\n");		
/*
	    filewriter.write("D" + dat + "\n");		
	    filewriter.write("P" + payee + "\n");		
	    filewriter.write("T" + amount + "\n");		
//     "M" + "[" + Index Compte budget + "]" + "[" + nom Compte budget + "]" + memo + "\n"
	    filewriter.write("M" + "[" + IndexBudget + "]" + "[" + _DataPool.NomCompteBudget(IndexBudget) + "]" + memo + "\n");		
	    filewriter.write("^" + "\n");		
*/	}

	@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                _OperationDbAdapter.deleteNote(info.id);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createNote() {
        Intent i = new Intent(this, OperationEdition.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }


/*    private void CompteParam() {
        Intent j = new Intent(this, Param2.class );
        startActivityForResult(j, ACTIVITY_CREATE);
    }
  */  
    private void EditParam() {
        Intent j = new Intent(this, Param.class );
        startActivityForResult(j, ACTIVITY_CREATE);
    }
    
    private void TraceGraph() {
//        Intent j = new Intent(this, Graphique_Budget.class );
//        startActivityForResult(j, ACTIVITY_CREATE);
		Graphique_Budget.Create(this);
//    	startActivity(achartIntent,ACTIVITY_CREATE);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, OperationEdition.class);
        i.putExtra(_OperationDbAdapter.KEY_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    	fillData();
    }
    
    public class CustomTouchListener implements View.OnTouchListener {     
        public boolean onTouch(View view, MotionEvent motionEvent) {
        switch(motionEvent.getAction()){            
                case MotionEvent.ACTION_DOWN:
                	if (popup_box.Is_License_Ok) {
                		Liste_de_Choix.Start_new(context, R.layout.masterlist, R.id.listviewperso, R.layout.affichageliste) ;                
//                ((TextView)view).setTextColor(0xFFFFFFFF); //white
                	}
           		break;          
          
                case MotionEvent.ACTION_CANCEL:             
                case MotionEvent.ACTION_UP:
//                ((TextView)view).setTextColor(0xFF000000); //black
                    break;
        } 
            return false;   
        } 
    }
    
}
