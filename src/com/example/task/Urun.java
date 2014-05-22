package com.example.task;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Urun  implements Parcelable,Serializable{
	
	  private int idurun;	
	  private String  codeurun;
	  private String isim;
	  private double fiyat;
	  private static String TAG = "** Urun**";
	 
	    public Urun(String isim,double fiyat,String codeurun,int idurun) {
	        super();
	        this.idurun = idurun;
	        
	        this.codeurun = codeurun;
	        
	        this.fiyat=fiyat;
	        
	        this.isim = isim;
	    }
	    
	 

	 
	    public Urun() {
			// TODO Auto-generated constructor stub
		}

		@Override
	    public String toString() {
	        return isim+" "+codeurun;
	    }
	 
	   
	    
	    public String getIsim() {
	        return isim;
	    }
	    
	    public String geturuncode() {
	        return codeurun;
	    }
	    
	    public double getfiyat() {
	        return fiyat;
	    }
	    
	    public int getidurun() {
	        return idurun;
	    }
	 
	    public void setIsim(String isim) {
	        this.isim = isim;
	    }
	    public void setcodeurun(String codeurun) {
	        this.codeurun = codeurun;
	    }
	    public void setfiyat(double fiyat) {
	        this.fiyat = fiyat;
	    }
	    
	    public void setidurun(int idurun) {
	        this.idurun = idurun;
	    }

	    public static final Parcelable.Creator<Urun> CREATOR = new 
	    		Parcelable.Creator<Urun>() { 

	    		                public Urun createFromParcel(Parcel in) { 

	    		                        Urun foo = new Urun(); 
	    		                        foo.idurun = in.readInt();
	    		                        foo.codeurun = in.readString(); 
	    		                        foo.fiyat=in.readDouble();
	    		                        foo.isim=in.readString();


	    		                    return foo; 
	    		                } 

	    		                    public Urun[] newArray(int size) { 
	    		                        return new Urun[size]; 
	    		                    } 
	    		                }; 
	    
		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		 public void writeToParcel(Parcel arg0, int arg1) { 
			  arg0.writeInt(this.idurun);
	          arg0.writeString(this.codeurun);
	          arg0.writeDouble(this.fiyat);
	          arg0.writeString(this.isim);
	          
	        } 
	

}
