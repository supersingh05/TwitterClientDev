package com.dev.googlesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeScreen extends Activity {
	
	EditText etQuery;
	GridView gvResults;
	Button btnSearch; 
	ArrayList <ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	String query;
	static String imageSize="";
	static String colorFilter="";
	static String imageType="";
	static String domainFilter="";
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setUpViews();
     //   if(getIntent().getSerializableExtra("filter").toString().isEmpty()) {
        	
       // } else {
        //	Filter fil = (Filter) getIntent().getSerializableExtra("filter");
        	//setFilterParams(fil);
        	
        //}
        imageAdapter= new ImageResultArrayAdapter(this, imageResults);
        
        //endless scrolling
        gvResults.setOnScrollListener(new EndlessScrollListener() {
    	    @Override
    	    public void onLoadMore(int page, int totalItemsCount) {
                    // Triggered only when new data needs to be appended to the list
                    // Add whatever code is needed to append new items to your AdapterView
    	        //customLoadMoreDataFromApi(page); 
                    // or customLoadMoreDataFromApi(totalItemsCount); 
    	    	//Log.d("DEBUG", "scrolling, page "+page);
    	    	//Log.d("DEBUG", "scrolling, totalItemsCount "+totalItemsCount);
    	    	System.out.println(totalItemsCount);
    	    	queryGoogle(totalItemsCount);
    	    	
    	    	
    	    }
            });
        
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new OnItemClickListener () {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,long rowId) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),ImageFullScreen.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result",imageResult);
				startActivity(i);
				
			}
        	
        	
        });
        
     
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }
    
    public void setUpViews() {
    	etQuery = (EditText) findViewById(R.id.etSearch);
    	gvResults = (GridView) findViewById(R.id.gvResult);
    	btnSearch = (Button) findViewById(R.id.btSearch);
    	
    	
    	
    }
    
    public void filterClick(MenuItem mi) {
    	//Toast.makeText(this, mi.toString(), Toast.LENGTH_SHORT).show();
		Intent i = new Intent(getApplicationContext(),SearchFilter.class);
		
    	//Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
		startActivityForResult(i, 5);
    }
    
    public void onSearch(View V) {
    	query = etQuery.getText().toString(); 
    	AsyncHttpClient client = new AsyncHttpClient();
    	String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&start=0&q="+Uri.encode(query);
       	if(!imageSize.isEmpty()) {
       		url=url+"&imgsz="+imageSize;
       	}
       	if(!imageType.isEmpty()) {
       		url=url+"&as_filetype="+imageType;
       		
       	}
       	if(!colorFilter.isEmpty()) {
       		url=url+"&imgcolor="+colorFilter;
       	}
       	if(!domainFilter.isEmpty()) {
       		url=url+"&as_sitesearch="+Uri.encode(domainFilter);
       	}
       	//Log.d("DEBUG","url "+url);
    	client.get(url, 
    			new JsonHttpResponseHandler() {
    				public void  onSuccess(JSONObject response) {
    					JSONArray imageJsonResults = null;
    					try {
    						imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
    						
    						imageAdapter.clear();
    						imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
    						//Log.d("DEBUG",imageResults.toString());
    						
    					} catch(JSONException e ) {
    						e.printStackTrace();
    					}
    					
    					
    				}
    		
    	});
    	
    	//Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
    		
    	
    }
    
    public void queryGoogle(int offset) {
       	AsyncHttpClient client = new AsyncHttpClient();
       	String url= "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&start="+offset+"&q="+Uri.encode(query);
       	if(!imageSize.isEmpty()) {
       		url=url+"&imgsz="+imageSize;
       	}
       	if(!imageType.isEmpty()) {
       		url=url+"&as_filetype="+imageType;
       		
       	}
       	if(!colorFilter.isEmpty()) {
       		url=url+"&imgcolor="+colorFilter;
       	}
       	if(!domainFilter.isEmpty()) {
       		url=url+"&as_sitesearch="+domainFilter;
       	}
       //	Log.d("DEBUG","url "+url);
       	client.get(url, 
    			new JsonHttpResponseHandler() {
    				public void  onSuccess(JSONObject response) {
    					JSONArray imageJsonResults = null;
    					try {
    						imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
    						
    						//imageAdapter.clear();
    						imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
    						//Log.d("DEBUG",imageResults.toString());
    						
    					} catch(JSONException e ) {
    						e.printStackTrace();
    					}
    					
    					
    				}
    		
    	});
    }
    
    public void setFilterParams(Filter filter) {
    	if(filter.getImageSize().toString().isEmpty()) {
    		imageSize ="";
    	} else {
    		imageSize = filter.getImageSize().toString(); 
    	}
    	
    	if(filter.getImageType().isEmpty()) {
    		imageType="";
    	} else {
    		imageType = filter.getImageType().toString();
    	}
    	
    	if(filter.getColorFilter().isEmpty()) {
    		colorFilter = "";
    	} else {
    		colorFilter =filter.getColorFilter().toString();
    	}
    	
    	if(filter.getDomainFilter().isEmpty()) {
    		domainFilter = "";
    	} else {
    		domainFilter =filter.getDomainFilter().toString();
    	}
    	//Log.d("DEBUG","image size "+imageSize);
    	//Log.d("DEBUG","image type "+imageType);
    	//Log.d("DEBUG","colorFilter "+colorFilter);
    }
 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	  // REQUEST_CODE is defined above
    	//Log.d("DEBUG","requestCode "+requestCode);
    	//Log.d("DEBUG","resultCode "+resultCode);
    	  if (resultCode == 100 && requestCode == 5) {
    	     // Extract name value from result extras
    	     Filter fil = (Filter) data.getSerializableExtra("filter");
    	     setFilterParams(fil);
    	     // Toast the name to display temporarily on screen
    	     //Toast.makeText(this, fil.getImageSize().toString(), Toast.LENGTH_SHORT).show();
    	  }
    	} 
    
}
