package com.android.demoapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.demoapp.db.MedicineData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sarfaraz on 12/30/2015.
 */
public class Product {
    public static int count = 0;
    long hkpDrugCode;
    String slug;
    double mrp;
    String packForm;
    int id;
    int uip;
    double oPrice ;
    int su;
    String productsForBrand;
    String generics;
    String name;
    String discountPerc;
    String manufacturer;
    String pForm;
    String imgUrl;
    int mfId;
    String form;
    String type;
    String packSize;
    double uPrice;
    boolean available;
    String label;


    public Product (JSONObject j) {
        try {
            id = j.getInt("id");
            name = j.getString("name");
            manufacturer = j.getString("manufacturer");
            label = j.getString("label");
            packForm = j.getString("packForm");
            mfId = j.getInt("mfId");
            available = j.getBoolean("available");
            form = j.getString("form");
            pForm = j.getString("pForm");
            discountPerc = j.getString("discountPerc");
            su = j.getInt("su");
            productsForBrand = j.getString("productsForBrand");
            oPrice = j.getDouble("oPrice");
            packSize = j.getString("packSize");
            mrp = j.getDouble("mrp");
            generics = j.getString("generics");
            imgUrl = j.getString("imgUrl");
            hkpDrugCode = j.getInt("hkpDrugCode");
            uip = j.getInt("uip");
            type = j.getString("type");
            uPrice = j.getDouble("uPrice");
            slug = j.getString("slug");
            count++;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("saz",e.toString());
        }
    }

    public void insert(Context ctx) {
        ContentValues values = new ContentValues();
        values.put(MedicineData._ID, id);
        values.put(MedicineData.MANUFACTURER, manufacturer);
        values.put(MedicineData.LABEL, label);
        values.put(MedicineData.PACKFORM, packForm);
        values.put(MedicineData.MFID, mfId);
        values.put(MedicineData.AVAILABLE, available);
        values.put(MedicineData.FORM, form);
        values.put(MedicineData.PFORM, pForm);
        values.put(MedicineData.DISCPRICE, discountPerc);
        values.put(MedicineData.SU, su);
        values.put(MedicineData.PRODUCTSFORBRAND, productsForBrand);
        values.put(MedicineData.OPRICE, oPrice);
        values.put(MedicineData.PACKSIZE, packSize);
        values.put(MedicineData.MRP, mrp);
        values.put(MedicineData.GENERICS, generics);
        values.put(MedicineData.IMGURL, imgUrl);
        values.put(MedicineData.HKPDRUGCODE, hkpDrugCode);
        values.put(MedicineData.NAME, name);
        values.put(MedicineData.UIP, uip);
        values.put(MedicineData.TYPE, type);
        values.put(MedicineData.UPRICE, uPrice);
        values.put(MedicineData.SLUG, slug);
        try {
            Uri uri = ctx.getContentResolver().insert(
                    MedicineData.CONTENT_URI, values);
        } catch (Exception e) {
            Log.i("saz",e.toString());
        }
    }
}
