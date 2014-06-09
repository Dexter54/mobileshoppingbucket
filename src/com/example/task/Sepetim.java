package com.example.task;


import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Sepetim extends Ara {

	
	private String[] isimler=new String[3];
	//ArrayList<Urun>  kisiler;//=new ArrayList<Urun>();
	MyAdapter adaptorumuz;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sepetim);
	
			 Intent i = getIntent(); 

			 //kisiler= (ArrayList<Urun>) i.getSerializableExtra("MyList");
			   //Log.d("",kisiler.get(0).geturuncode());
			 
			   int xyz=kisiler.size();
               
               String abc = Integer.toString(xyz);
               
               Log.d("ürün sayýsý",abc);
               
               /* xyz=(kisiler).size();
               
                abc = Integer.toString(xyz);
               
               Log.d("ürün sayýsý",abc);*/
			   
			 ListView listemiz=(ListView) findViewById(R.id.liste);
			 adaptorumuz=new MyAdapter(this, kisiler);
			 listemiz.setAdapter(adaptorumuz);
			 
			 listemiz.setOnItemLongClickListener(new OnItemLongClickListener(){
				 @Override
				 public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						 int position, long arg3) {
					// TODO Auto-generated method stub
					removeItemFromList(position);   
					return true;
				}
				 
			 });
				 
						 
		
	}
	protected void removeItemFromList(int position) {
        final int deletePosition = position;
        Log.d("pozisyon",Integer.toString(position));
        AlertDialog.Builder alert = new AlertDialog.Builder(
                Sepetim.this);
    
        alert.setTitle("Silme");
        alert.setMessage("Bu ürünü silmek istediðinize emin misiniz?");
        alert.setPositiveButton("EVET", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				kisiler.remove(deletePosition);
		
			    //customer.
				adaptorumuz.notifyDataSetChanged();
				/*Intent intent = new Intent(); 
            	intent.setClass(Sepetim.this, Ara.class); 
            	intent.putExtra("MyList", kisiler ); 
            	 
            	
            	startActivity(intent);*/
          
				adaptorumuz.notifyDataSetInvalidated();
				
			}
        });
        alert.setNegativeButton("HAYIR", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
      
        alert.show();
      
    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sepetim, menu);
		return true;
	}

}
