				
package com.malicia.mrg.android.apps.dailyaccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class _MySimpleArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	private final int TOL;

	public _MySimpleArrayAdapter(Context context, String[] values , int TypeOfList) {
		super(context, R.layout.objet_list, values);
		this.context = context;
		this.values = values;
		this.TOL = TypeOfList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.objet_list, parent, false);
		
		TextView objectname = (TextView) rowView.findViewById(R.id.objectname);
		TextView objectmois = (TextView) rowView.findViewById(R.id.objectmois);
		TextView objectjour = (TextView) rowView.findViewById(R.id.objectjour);

		if (TOL == 0){
			Integer i = Integer.valueOf(values[position]);
			int Couleur = _DataPool.CompteColor(i);
			objectname.setText(_DataPool.Nom_Compte(i));
			objectname.setTextColor(Couleur);				
			objectmois.setText(String.valueOf(_DataPool.BrutCompte(i)));
			objectmois.setTextColor(Couleur);				
			objectjour.setText(String.valueOf(_DataPool.MtBudgetJour(String.valueOf(_DataPool.BrutCompte(i)))));
			objectjour.setTextColor(Couleur);	
		}
		if (TOL == 1){
			String IndexBudget = values[position];
			int Couleur = _DataPool.BudgetColor(IndexBudget);
			objectname.setText(_DataPool.BudgetNom(IndexBudget));
			objectname.setTextColor(Couleur);	
			objectmois.setText(String.valueOf(_DataPool.BudgetMois(IndexBudget)));
			objectmois.setTextColor(Couleur);
			objectjour.setText(String.valueOf(_DataPool.BudgetJour(IndexBudget)));
			objectjour.setTextColor(Couleur);
		}


		return rowView;
	}
}
