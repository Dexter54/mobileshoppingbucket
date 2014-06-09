package com.example.task;


import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	String EXTRA_MESSAGE = " ";
	//Burasý private olacak
	public static  ArrayList<Urun> kisiler=new ArrayList<Urun>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		try {
            Button scanner = (Button)findViewById(R.id.imageButtonSelector);
            
            
       
            scanner.setOnClickListener(new View.OnClickListener() {
                        
                public void onClick(View v) {
                	
                	kisiler.clear();
                	EditText xyz = (EditText) findViewById(R.id.editText1);
                    String tc=xyz.getText().toString();
                	Intent intent = new Intent(); 
                	intent.setClass(MainActivity.this, Ara.class); 
                	//intent.putExtra("MyList", kisiler ); 
                	intent.putExtra("tc", tc ); 
                	
                	startActivity(intent);
                	/*Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE,CODABAR,PRODUCT_MODE");
                    startActivityForResult(intent, 0);*/
                }
 
            });
            
            Button scanner2 = (Button)findViewById(R.id.imageButtonSelector2);
            scanner2.setOnClickListener(new View.OnClickListener()  {
                
                public void onClick(View v) {
                
                	finish();
                	
                }
 
            });

	
            
        } catch (ActivityNotFoundException anfe) {
            Log.e("onCreate", "Scanner Not Found", anfe);
        }
		
		 
	}
	 
	 @Override
	 protected void onResume() {
	     // TODO Auto-generated method stub
	     super.onResume();
	 }


	 @Override
	 protected void onPause() {
	     // TODO Auto-generated method stub
	     super.onPause();
	 }
	 
	 @Override
	 protected void onDestroy() {
	     // TODO Auto-generated method stub
	     super.onDestroy();
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
