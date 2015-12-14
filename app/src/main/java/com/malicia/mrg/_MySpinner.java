package com.malicia.mrg;

import java.util.Comparator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class _MySpinner extends Spinner {

    // constructors (each calls initialise)
    public _MySpinner(Context context) {
        super(context);
        this.initialise();
    }
    public _MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialise();
    }
    public _MySpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initialise();
    }

    // declare object to hold data values
    private ArrayAdapter<String> arrayAdapter;

    // add the selected item to the end of the list
    public void addItem(String item) {
        this.addItem(item, true);
    }
    public void addItem(String item, boolean select) {
        arrayAdapter.add(item);
        this.setEnabled(true);
        if (select) this.selectItem(item);
        arrayAdapter.sort(new Comparator<String>() {
            public int compare(String object1, String object2) {
                return object1.compareTo(object2);
            };
        });
    }

    // remove all items from the list and disable it
    public void clearItems() {
        arrayAdapter.clear();
        this.setEnabled(false);
    }

    // make the specified item selected (returns false if item not in the list)
    public boolean selectItem(String item) {
        boolean found = false;
        this.setSelection(0);
        for (int i = 0; i < this.getCount(); i++) {
            if (arrayAdapter.getItem(i).compareTo(item)==0) {
                this.setSelection(i);
                found = true;
                break;
            }
        }
        return found;
    }

    // return the current selected item
    public String getSelected() {
        if (this.getCount() > 0) {
            return arrayAdapter.getItem(super.getSelectedItemPosition());
        } else {
            return "";
        }
    }

    // allow the caller to use a different DropDownView, defaults to android.R.layout.simple_dropdown_item_1line
    public void setDropDownViewResource(int resource) {
        arrayAdapter.setDropDownViewResource(resource);
    }
    // internal routine to set up the array adapter, bind it to the spinner and disable it as it is empty
    private void initialise() {
        arrayAdapter = new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        this.setAdapter(arrayAdapter);
        this.setEnabled(false);
    }
}
