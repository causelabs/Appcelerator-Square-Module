package com.hidefweb.square;

import org.appcelerator.titanium.util.Log;

import com.squareup.android.Bill;
import com.squareup.android.Currency;
import com.squareup.android.LineItem;
import com.squareup.android.Square;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LaunchSquare extends Activity {
	
	public static final String PRICE = "PRICE";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String CODE = "CODE";
	
	/* The purpose of this class is to act as an Activity proxy that can receive the onResult response
	 * from the Square class. Once we get the resultCode from the Square class we can easily send that on
	 * to the Appcelerator calling module.
	 */
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        
        String description = extras.getString(DESCRIPTION);       
        int price = extras.getInt(PRICE);
        int code = extras.getInt(CODE);
        
        Square square = new Square(this);
        
		LineItem advice = new LineItem.Builder()
    	.price(price, Currency.USD)
    	.description(description)
    	.build();
	
		square.squareUp(Bill.containing(advice), code);
		
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("SquareModule", "onActivityResult Called");

        // Just pass on this result upstream
        setResult(resultCode);
        finish();
    }
}