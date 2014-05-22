package com.example.task;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
 
    private LayoutInflater mInflater;
    private ArrayList<Urun>     UrunListesi;//=new ArrayList<Urun>()  ;
 
    public MyAdapter(Activity activity, ArrayList<Urun> Urunler) {
        //XML'i alýp View'a çevirecek inflater'ý örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalým
        UrunListesi = Urunler;
    }
 
    @Override
    public int getCount() {
        return UrunListesi.size();
    }
 
    @Override
    public Urun getItem(int position) {
        //þöyle de olabilir: public Object getItem(int position)
        return UrunListesi.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
    	View satirView;
 
        satirView = mInflater.inflate(R.layout.satir_layout, null);
        TextView textView = 
                (TextView) satirView.findViewById(R.id.codeurun); 
        
        TextView textView1 = 
                (TextView) satirView.findViewById(R.id.isim); 
        TextView textView2 = 
                (TextView) satirView.findViewById(R.id.fiyat); 
        
 
        Urun kisi = UrunListesi.get(position);
 
        textView.setText(kisi.geturuncode());
        textView1.setText(kisi.getIsim());
        textView2.setText(Double.toString(kisi.getfiyat()));
 
        
        return satirView;
    }

}
	
	


