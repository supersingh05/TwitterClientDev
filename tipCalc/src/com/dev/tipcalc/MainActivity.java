package com.dev.tipcalc;



import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.Printer;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView txtView;
	private EditText editText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView = (TextView) findViewById(R.id.tvText);
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
 
    public void findMyTip(View v) {
    	float val=0;
    	float finalVal = 0;
    	BigDecimal val1 = null;
    	BigDecimal per;
    	BigDecimal tot = null;
    	NumberFormat n;
    	String f = null;
    	double money;
    	NumberFormat nf = NumberFormat.getInstance();
    	nf.setMaximumFractionDigits(2);
    	nf.setGroupingUsed(false);
    	EditText etNew = (EditText) findViewById(R.id.etAmount);
    	if(etNew.getText().toString().isEmpty()){
    		txtView.setText("");
    	} else {
    		
    		float number = Float.valueOf(etNew.getText().toString());
    		BigDecimal number1 =  new BigDecimal(etNew.getText().toString());
    		n = NumberFormat.getCurrencyInstance(Locale.US);
    		//float val = number * (f/100);
    		//float finalVal= number +val;
    		//float myfloatvariable=(float) 2.33333;
    		//String mytext=Float.toString(finalVal);
    		switch(v.getId())  //get the id of the view clicked. (in this case button)
    		{
    		case R.id.btn10 : // if its button1
    		    //do something
    			 val = (float) (number * .1);
    			 per = new BigDecimal(".10");
    			 val1 = number1.multiply(per);
    			 finalVal= number +val;
    			 tot = number1.add(val1);
    			 //f = tot.toString(); 
    			 money = tot.doubleValue();
    			 f = n.format(money);
    			 
    		    break;
    		case R.id.btn15 : // if its button1
    			per = new BigDecimal(".15");
    			val1 = number1.multiply(per);
    			tot = number1.add(val1);
   			 	money = tot.doubleValue();
   			 	f = n.format(money);

    		    //do something
    		    break;
    		case R.id.btn20 : // if its button1
    		    //do something
    			per = new BigDecimal(".20");
    			val1 = number1.multiply(per);
    			tot = number1.add(val1);
   			 	money = tot.doubleValue();
   			 	f = n.format(money);   			 	

    		    break;
    		}
    		
    		
    		String subtotal=n.format(number1.doubleValue());
    		String tip=n.format(val1.doubleValue());
    		String finalTotal=n.format(tot.doubleValue());
    		//txtView.setText("Original: $"+number+"\nTip: $"+tip+"\nTotal: $"+mytext);
    		txtView.setText("Subtotal: "+subtotal+"\n\nTip: "+tip+"\n\nTotal: "+finalTotal);
    		
    		
    		
    	}
    }
    
}
