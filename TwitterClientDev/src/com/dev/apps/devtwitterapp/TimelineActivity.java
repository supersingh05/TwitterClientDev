package com.dev.apps.devtwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dev.apps.devtwitterapp.models.Tweet;
import com.dev.apps.devtwitterapp.models.User;



import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class TimelineActivity extends Activity {
	ListView lvTweets;
	ArrayList<Tweet> tweets;
	TweetsAdapter adapter;
	User me;
	ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		actionBar = getActionBar();
		//get users info for user id and profile pic
		MyTwitterApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, JSONObject myInfo) {
				// TODO Auto-generated method stub
				//super.onSuccess(arg0, arg1);
				 me = User.fromJson(myInfo);
				Log.d("DEBUG",me.getScreenName());
				actionBar.setTitle(Html.fromHtml("<small>@"+me.getScreenName()+"</small>"));
		
				//Log.d("DEBUG",""+statusCode);
				//Log.d("DEBUG",""+myInfo.toString());
			}
			
			
			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				//add persistance to load from db
				Log.d("DEBUG","FAIL TO GET MYINFO");
			}
		});
		
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				// tweets defined above
				Log.d("DEBUG",jsonTweets.toString());
				 tweets = Tweet.fromJson(jsonTweets);
				 // lvTweets defined above
				 lvTweets = (ListView) findViewById(R.id.lvTweets);
				 
				adapter =  new TweetsAdapter(getBaseContext(),tweets);
				lvTweets.setAdapter(adapter);
				//Log.d("debug",jsonTweets.toString());
				lvTweets.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapter, View parent, int position,
							long rowId) {
						Tweet tweet = tweets.get(position);
						
						//Log.d("DEBUG", "pos"+position);
						//Log.d("DEBUG", ""+tweet.getId());
						// TODO Auto-generated method stub
						//Toast.makeText(this,"moo", Toast.LENGTH_SHORT).show();
						//Toast.makeText(getBaseContext(),tweet.getId().toString(), Toast.LENGTH_SHORT).show();
						
					}
				});
				
				
		        //endless scrolling
		        lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    	    @Override
		    	    public void onLoadMore(int page, int totalItemsCount) {
		                    // Triggered only when new data needs to be appended to the list
		                    // Add whatever code is needed to append new items to your AdapterView
		    	        //customLoadMoreDataFromApi(page); 
		                    // or customLoadMoreDataFromApi(totalItemsCount); 
		    	    	//Log.d("DEBUG", "scrolling, page "+page);
		    	    	//Log.d("DEBUG", "scrolling, totalItemsCount "+totalItemsCount);
		    	    	//System.out.println(totalItemsCount);
		    	    	//Log.d("DEBUG",""+totalItemsCount);
		    	    	long maxid = tweets.get((totalItemsCount-1)).getId();
		    	    	//Log.d("DEBUG",""+tweets.get(aa).getId());
		    	    	MyTwitterApp.getRestClient().getOlderTimeline(new JsonHttpResponseHandler() {
		    	    		@Override
		    	    		public void onSuccess(JSONArray jsonTweets) {
		    	    				adapter.addAll(Tweet.fromJson(jsonTweets));
		    	    		}
		    				@Override
		    				public void onFailure(Throwable arg0) {
		    					// TODO Auto-generated method stub
		    					
		    					Toast.makeText(getBaseContext(),"No Internet Connection", Toast.LENGTH_SHORT).show();
		    					Log.d("DEBUG","FAIL TO LOAD MORE TWEETS");
		    				}
		    	    		
		    	    	}, maxid);
		    	    	
		    	    	
		    	    	
		    	    }
		            });
			}
			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				//add persistance to load from db
				Log.d("DEBUG","FAIL TO GET LOAD TWEETS");
			}
		});
		

		

		
		
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void clickPostTweet(MenuItem mi) {
    	//Toast.makeText(this, mi.toString(), Toast.LENGTH_SHORT).show();
		Intent i = new Intent(getApplicationContext(),ComposeTweet.class);
		i.putExtra("userid", me.getScreenName());
		i.putExtra("profilepic",me.getProfileImageUrl());
		startActivityForResult(i, 5);
		
    	//Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
		//startActivityForResult(i, 5);
		
	}
	
	public void refreshTweets(MenuItem mi) {
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				// TODO Auto-generated method stub
				adapter.clear();
				adapter.addAll(Tweet.fromJson(jsonTweets));
				Toast.makeText(getBaseContext(),"Refreshed", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  	  // REQUEST_CODE is defined above
  	//Log.d("DEBUG","requestCode "+requestCode);
  	//Log.d("DEBUG","resultCode "+resultCode);
  	  if (resultCode == 100 && requestCode == 5) {
  	     // Extract name value from result extras
  	     Tweet mytweet = (Tweet) data.getSerializableExtra("mytweet");
  	     Log.d("DEBUG",mytweet.toString());
  	     adapter.insert(mytweet,0);
  	     // Toast the name to display temporarily on screen
  	     //Toast.makeText(this, fil.getImageSize().toString(), Toast.LENGTH_SHORT).show();
  	  }
  	  if (resultCode == 50 && requestCode == 5) {

   	  }
  	}
	

	

}
