package com.android.demoapp.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.demoapp.R;
import com.android.demoapp.db.MedicineData;

/**
 * Created by Sarfaraz on 1/31/2016.
 */
public class Fragment_Main extends android.support.v4.app.Fragment {

    Fragment_Main() {
        setRetainInstance(true);
    }

    public static Fragment_Main getInstance(String nameFrag, String manufacturerFrag, Double priceFrag) {
        Fragment_Main frag = new Fragment_Main();

        Bundle b = new Bundle();
        b.putString(MedicineData.NAME, nameFrag);
        b.putString(MedicineData.MANUFACTURER, manufacturerFrag);
        b.putDouble(MedicineData.UPRICE, priceFrag);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.product_info, container, false);
        Log.i("saz", "fragment created");

        TextView name = (TextView) v.findViewById(R.id.name);
        TextView manufacturer = (TextView) v.findViewById(R.id.manufacturerName);
        TextView price = (TextView) v.findViewById(R.id.price);
        name.setText(getArguments().getString(MedicineData.NAME));
        manufacturer.setText(getArguments().getString(MedicineData.MANUFACTURER));
        Double p = getArguments().getDouble(MedicineData.UPRICE);
        price.setText(p.toString());
        return v;
    }
}
