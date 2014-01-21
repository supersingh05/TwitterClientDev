package com.dev.googlesearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class SearchFilter extends Activity {
	Spinner imageSize;
	Spinner colorFilter;
	Spinner imageType;
	EditText domainFilter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_filter);
		 imageSize = (Spinner) findViewById(R.id.spSize);
		 colorFilter = (Spinner) findViewById(R.id.spFilter);
		 imageType = (Spinner) findViewById(R.id.spType);
		 domainFilter = (EditText) findViewById(R.id.etSite);
		 	//Log.d("DEBUG","imagesize "+HomeScreen.imageSize.toString());
		 	
			setSpinnerToValue(imageSize, HomeScreen.imageSize.toString());
			setSpinnerToValue(imageType, HomeScreen.imageType.toString());
			setSpinnerToValue(colorFilter, HomeScreen.colorFilter.toString());
			domainFilter.setText(HomeScreen.domainFilter.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_filter, menu);
		return true;
	}
	
	public void  setFilter(View v) {
		//Spinner imageSize = (Spinner) findViewById(R.id.spSize);
		//Spinner colorFilter = (Spinner) findViewById(R.id.spFilter);
		//Spinner imageType = (Spinner) findViewById(R.id.spType);
		

		
		Filter filter = new Filter();
		
		if(imageSize.getSelectedItem().toString().isEmpty()) {
			filter.setImageSize("");
		} else {
			//Toast.makeText(this, imageSize.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
			filter.setImageSize(imageSize.getSelectedItem().toString());
		}
		if(colorFilter.getSelectedItem().toString().isEmpty()) {
			filter.setColorFilter("");
		} else {
			filter.setColorFilter(colorFilter.getSelectedItem().toString());
		}
		if(imageType.getSelectedItem().toString().isEmpty()) {
			filter.setImageType("");
		} else {
			filter.setImageType(imageType.getSelectedItem().toString());
		}
		
		if(domainFilter.getText().toString().isEmpty()) {
			filter.setDomainFilter("");
		} else {
			filter.setDomainFilter(domainFilter.getText().toString());
		}
		
		//Log.d("DEBUG",filter.getImageSize().toString());
		Intent i = new Intent();
		i.putExtra("filter",filter);
		
		setResult(100, i); 
		finish();
		
	}
	
	public void setSpinnerToValue(Spinner spinner, String value) {
		int index = 0;
		SpinnerAdapter adapter = spinner.getAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).equals(value)) {
				index = i;
			}
		}
		spinner.setSelection(index);
	}

}
