package com.example.task;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Ara extends MainActivity {

	private String[] ulkeler = new String[3];
	private DrawerLayout mDrawerLayout;  
    private ListView mDrawerList;
    Button scanner;
    //public ArrayList<Urun> kisiler;
    Urun x;
    private ProgressDialog pDialog;
    String Uruncode;
	static JSONObject jObj = null;
    static String json = "";
    InputStream is;
	private int i=0;
	String tc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.drawer_layout);
		
		Intent i = getIntent();
		//kisiler= (ArrayList<Urun>) i.getSerializableExtra("MyList");
		tc=i.getStringExtra("tc");
		
		
		Log.d("","girdi");
		
		ulkeler = getResources().getStringArray(R.array.contents);		
	    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.text, ulkeler));
       
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
       
            scanner = (Button)findViewById(R.id.imageButtonSelector3);
           
            scanner.setOnClickListener(new View.OnClickListener() {
                
                public void onClick(View v) {               	
                	
                	/*intent.putExtra("MyList", kisiler ); */               	
                	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE,CODABAR,PRODUCT_MODE");
                    startActivityForResult(intent, 0);
                }
 
            });
		
	}
		
	
	 public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	        if (requestCode == 0) {
	            if (resultCode == RESULT_OK) {
           	
	            	Uruncode = intent.getStringExtra("SCAN_RESULT");
	                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");           
	                
	                new GetProductDetails().execute();
	                
	               
	            } else if (resultCode == RESULT_CANCELED) {
	                // Handle cancel
	                Toast toast = Toast.makeText(this, "Scan was Cancelled!", Toast.LENGTH_LONG);
	                toast.setGravity(Gravity.TOP, 25, 400);
	                toast.show();
	                
	            }
	        }
	    }
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener  {
	    
		
		
		@Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	       
	      
	    }

		
		
		private void selectItem(int position) {

			   switch(position)
			     {
			          case 0:
			        	  
			        	    
			        	  	Intent intent = new Intent(); 
		                	intent.setClass(Ara.this, Sepetim.class); 
		                	//intent.putExtra("MyList",kisiler );
		                	startActivity(intent);
		              break; 	
			          case 1:
			        	  	
			        	  pDialog = new ProgressDialog(Ara.this);
			        	  double x=0;
			        	  for(int i=0;i<kisiler.size();++i)
			        		  x+=kisiler.get(i).getfiyat();
			        	  String abc=Double.toString(x);			        	  
			              pDialog.setMessage("Toplam borcunuz"+x+"  TL dir");
			              //pDialog.setIndeterminate(false);
			              //pDialog.setCancelable(true);
			              pDialog.show();
			          break;   
			          case 2:
			        	  
			        	  new SaveProduct().execute();
			        	  
			        	  finish();			        	  
			     } 						
		}		
	}
	
	class GetProductDetails extends AsyncTask<String, String, String> {
		 
		String result;
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            /*super.onPreExecute();
            pDialog = new ProgressDialog(Ara.this);
            pDialog.setMessage("Loading product details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();*/
        }
 
        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {
 
            // updating UI from Background Thread
          
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                        params1.add(new BasicNameValuePair("Uruncode", Uruncode));
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://192.168.43.149/first/getproduct.php");
                        httppost.setEntity(new UrlEncodedFormEntity(params1));
                        HttpResponse response = httpclient.execute(httppost); 
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();

                        Log.e("log_tag", "connection success ");
                        
                    }
                catch(Exception e)
                    {
                        Log.e("log_tag", "Error in http connection "+e.toString());
                  

                    }
                //convert response to string
                    try{
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) 
                        {
                                sb.append(line + "\n");
                                
                        }
                        is.close();

                        result=sb.toString();
                    }
                    catch(Exception e)
                    {
                       Log.e("log_tag", "Error converting result "+e.toString());
                   
                    }

                //parse json data
                try{
                              
                        JSONObject object = new JSONObject(result);
                        String ch=object.getString("re");
                        if(ch.equals("success"))
                        {
                           
                           JSONObject no = object.getJSONObject("0");
                            
                         //long q=object.getLong("f1");
                        String id=no.getString("idUrun");   
                        String kod=no.getString("Uruncode");
                        String isim=no.getString("Name");
                        String w= no.getString("Fiyat");
                        
                        Log.d("", id);
                        
                        double x=Double.parseDouble(w);
                        int y = Integer.parseInt(id);
                        
                        Urun yeni = new Urun(isim,x,kod,y);
                        kisiler.add(yeni);                        

                          }
                        
                        else
                        {                           
                            Log.d("","kayýt yok");

                        }                   
                
                }
                catch(JSONException e)
                {
                        Log.e("log_tag", "Error parsing data "+e.toString());
                       
                }

            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
        	//pDialog.cancel();
            //pDialog.dismiss();
        }
    }
	
	class SaveProduct extends AsyncTask<String, String, String> {
		 
		String result;
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            /*super.onPreExecute();
            pDialog = new ProgressDialog(Ara.this);
            pDialog.setMessage("Loading product details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();*/
        }
 
        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {
 
            // updating UI from Background Thread
          
                    // Check for success tag
                  
        			int success;
        			int temp = 0;
                    try {
                        // Building Parameters
                        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                        params1.add(new BasicNameValuePair("Tc", tc));
                        double x=0;
			        	  for(int i=0;i<kisiler.size();++i)
			        		  x+=kisiler.get(i).getfiyat();
			        	String abc=Double.toString(x);
                        params1.add(new BasicNameValuePair("Total",abc));
                        params1.add(new BasicNameValuePair("Tamam","0"));
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://192.168.43.149/first/create_product.php");
                        httppost.setEntity(new UrlEncodedFormEntity(params1));
                        HttpResponse response = httpclient.execute(httppost); 
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();

                        Log.e("log_tag", "connection success ");
                        
                        params1.add(new BasicNameValuePair("kimlikno", tc));
                        httpclient = new DefaultHttpClient();
                        httppost = new HttpPost("http://192.168.43.149/first/getidcustomer.php");
                        httppost.setEntity(new UrlEncodedFormEntity(params1));
                        response = httpclient.execute(httppost); 
                        entity = response.getEntity();
                        is = entity.getContent();
                        try{
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                            StringBuilder sb = new StringBuilder();
                            String line = null;
                            while ((line = reader.readLine()) != null) 
                            {
                                    sb.append(line + "\n");
                                    
                            }
                            is.close();

                            result=sb.toString();
                        }
                        catch(Exception e)
                        {
                           Log.e("log_tag", "Error converting result "+e.toString());
                       
                        }

                    //parse json data
                    try{
                                  
                            JSONObject object = new JSONObject(result);
                            String ch=object.getString("re");
                            if(ch.equals("success"))
                            {
                               
                               JSONObject no = object.getJSONObject("0");
                                
                             //long q=object.getLong("f1");
                            String araci=no.getString("idcustomer");                     
                            temp = Integer.parseInt(araci);
                            Log.d("",araci);
                              }
                            
                            else
                            {                           
                                Log.d("","kayýt yok");

                            }                   
                    
                    }
                    catch(JSONException e)
                    {
                            Log.e("log_tag", "agagError parsing data "+e.toString());
                           
                    }
                    }
                catch(Exception e)
                    {
                        Log.e("log_tag", "Error in http connection "+e.toString());
                  

                    }
                    
                    
                    int abc;
                    
                    for(int i=0;i<kisiler.size();++i){
                    	HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://192.168.43.149/first/saveall.php");
                    	List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    	String x = Integer.toString(temp);
                    	params1.add(new BasicNameValuePair("idcustomer", x));
                    	Log.d("",x);
                    	abc=kisiler.get(i).getidurun();
                    	x = Integer.toString(abc);
                    	params1.add(new BasicNameValuePair("idUrun", x));
                    	Log.d("",x);
                    	try {
							httppost.setEntity(new UrlEncodedFormEntity(params1));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        HttpResponse response = null;
						try {
							response = httpclient.execute(httppost);
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
                        HttpEntity entity = response.getEntity();
                        try {
							is = entity.getContent();
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                         try{
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) 
                        {
                                sb.append(line + "\n");
                                
                        }
                        is.close();

                        result=sb.toString();
                    }
                    catch(Exception e)
                    {
                       Log.e("log_tag", "Error converting result "+e.toString());
                   
                    }

                //parse json data
                try{
                              
                        JSONObject object = new JSONObject(result);
                        String ch=object.getString("success");
                        String xy=object.getString("message");
                        Log.d("success:  ",ch);
                        Log.d("message:  ",xy);
                       /* if(ch.equals("0"))
                        {
                           
                        	Log.d("","baþarýlý");
                          }
                        
                        else
                        {                           
                            Log.d("","kayýt yok");

                        }     */              
                
                }
                catch(JSONException e)
                {
                        Log.e("log_tag", "Error parsing data "+e.toString());
                       
                }

                        Log.e("log_tag", "connection success ");
                    }
              
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
        	//pDialog.cancel();
        }
    }

	//public void onDestroy(){
		
		//s/uper.onDestroy();
		//finish();
		
	//}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ara, menu);
		return true;
	}

}
