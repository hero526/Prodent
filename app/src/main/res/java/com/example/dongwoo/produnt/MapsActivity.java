package com.example.dongwoo.produnt;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemClickListener{

    private double my_x=0;
    private double my_y=0;

    private ArrayList<String> seller_names = new ArrayList<String>();
    private ArrayList<String> price = new ArrayList<String>();
    private ArrayList<String> good_rates = new ArrayList<String>();
    private ArrayList<String> bad_rates = new ArrayList<String>();
    private ArrayList<String> data_volumes = new ArrayList<String>();
    private Random rnd = new Random();

    private HttpAsyncTask web;
    private ProgressDialog progressDialog;
    private final MyHandler mHandler = new MyHandler(this);

    private GoogleMap mMap;
    LocationManager locationManager;

    List<sellerData> rowItems;
    ListView mylistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //LocationManager
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        //check available gps and permission
        check_gps();
        //prepare seller list
        setListViewData();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        progressDialog = new ProgressDialog( MapsActivity.this );
        web = new HttpAsyncTask(MapsActivity.this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // 중심 화면 설정
        LatLng eng_9 = new LatLng(35.887025, 128.609459);                // 테스트 장소 공대 9호관

        //현재 위치로 가는 버튼 표시
        if (ContextCompat.checkSelfPermission (this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission (this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( eng_9, 15));//초기 위치...수정필요

        myLocation.LocationResult locationResult = new myLocation.LocationResult() {
            @Override
            public void gotLocation(Location location) {
                //String msg = "lon: "+location.getLongitude()+" -- lat: "+location.getLatitude();
                //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                drawMarker(location);
            }
        };

        myLocation myLocation = new myLocation();
        myLocation.getLocation(getApplicationContext(), locationResult);
    }
    public void drawMarker(Location location) {

        //기존 마커 지우기
        mMap.clear();
        my_x=location.getLatitude();
        my_y=location.getLongitude();

        LatLng currentPosition = new LatLng(my_x, my_y);

        web.execute("http://192.168.43.234:9998/databylocale/");

        //currentPosition 위치로 카메라 중심을 옮기고 화면 줌을 조정한다. 줌범위는 2~21, 숫자클수록 확대
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( currentPosition, 17));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);

        //마커 추가
        mMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet("Lat:" + location.getLatitude() + "Lng:" + location.getLongitude())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("현재위치"));
    }
    public void add_Marker(GoogleMap map, String x, String  y){

        Log.d("@@Marker@@",""+x+"/"+y+"/"+map.toString());
         double sell_x = Double.parseDouble(x);
         double sell_y = Double.parseDouble(y);

        // circle settings
        LatLng latLng = new LatLng(sell_x, sell_y);

        // draw circle
        int d = 250; // diameter
        Bitmap bm = Bitmap.createBitmap(d, d, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint();
        p.setColor(Color.RED);
        c.drawCircle(d/2, d/2, d/2, p);

        // generate BitmapDescriptor from circle Bitmap
        BitmapDescriptor bmD = BitmapDescriptorFactory.fromBitmap(bm);

        // mapView is the GoogleMap
        map.addGroundOverlay(new GroundOverlayOptions().
                image(bmD).
                position(latLng,20,20).
                transparency(0.4f));
    }
    private void check_gps()
    {
        //GPS가 켜져있는지 체크
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //GPS 설정화면으로 이동
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
            finish();
        }

        //마시멜로 이상이면 권한 요청하기
        if(Build.VERSION.SDK_INT >= 23){
            //권한이 없는 경우
            if(ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION , Manifest.permission.ACCESS_FINE_LOCATION} , 1);
            }
        }
    }

    private void setListViewData() {
        rowItems = new ArrayList<sellerData>();

        for (int i = 0; i < seller_names.size(); i++) {
            sellerData item = new sellerData(seller_names.get(i), good_rates.get(i), bad_rates.get(i), data_volumes.get(i), price.get(i));
            rowItems.add(item);
       }
        mylistview = (ListView) findViewById(R.id.list);
        sellerList_Adapter adapter = new sellerList_Adapter(this, rowItems);
        mylistview.setAdapter(adapter);
        //profile_pics.recycle();
        mylistview.setOnItemClickListener(this);
    }

    public void add_sellerdata(String name, String good, String bad,  String money, String seller_x, String seller_y)
    {
        seller_names.add(name);
        price.add(money);
        good_rates.add(good);
        bad_rates.add(bad);
        data_volumes.add(Integer.toString(rnd.nextInt(500)));
        add_Marker(mMap, seller_x,seller_y);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String seller_name = rowItems.get(position).getSeller_name();
        //WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //wifiManager.setWifiEnabled(true);
        Toast.makeText(getApplicationContext(), "connected with " +seller_name, Toast.LENGTH_SHORT).show();
    }

    public String POST(String pUrl) {
        String result = "";
        InputStream is = null;

        JSONObject jsonOb = new JSONObject();
        Log.d("@@POST@@",""+my_x+"/"+my_y);
        try {
            jsonOb.put("Name", "test");
            jsonOb.put("Cash", 0);
            jsonOb.put("MAC", "test");
            jsonOb.put("Rate", 0.0);
            jsonOb.put("Coord_x", my_x);
            jsonOb.put("Coord_y", my_y);
            jsonOb.put("Uid", "Test");
            jsonOb.put("Password", "pw");
            jsonOb.put("PhoneNumber", "phonenumber");
            jsonOb.put("Provider", "provider");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(pUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-type", "application/json");
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            os.write(jsonOb.toString().getBytes("UTF-8"));
            os.flush();
            try {
                is = con.getInputStream();
                if (is != null)
                    result = convertInputStreamToString(is);
                else
                    result = "Do not work!";
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private   MapsActivity mapsAct;
        HttpAsyncTask(MapsActivity signUPAct) {
            this.mapsAct = signUPAct;
        }
        @Override
        protected String doInBackground(String... urls) {
            return POST(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);

            mapsAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getJSON();
                }
            });
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    private static class MyHandler extends Handler {
        private final WeakReference<MapsActivity> weakReference;

        public MyHandler(MapsActivity mapact) {
            weakReference = new WeakReference<MapsActivity>(mapact);
        }
        @Override
        public void handleMessage(Message msg) {
            MapsActivity mapsactivity = weakReference.get();

            if (mapsactivity != null) {
                switch (msg.what) {
                    case 101:
                        //this.progressDialog.dismiss();
                         String msg_str= "["+(String)msg.obj+"]";
                        Log.d("@@handler@11@",""+msg_str);
                        try {
                            JSONArray jary = new JSONArray(msg_str);
                            for (int i=0; i<jary.length(); i++)
                            {
                                String name=jary.getJSONObject(i).get("Name").toString();
                                String bad=jary.getJSONObject(i).get("Bad").toString();
                                String good=jary.getJSONObject(i).get("Good").toString();
                                String price=jary.getJSONObject(i).get("Cash").toString();
                                String seller_x=jary.getJSONObject(i).get("Coord_x").toString();
                                String seller_y=jary.getJSONObject(i).get("Coord_y").toString();
                                mapsactivity.add_sellerdata(name, good, bad, price, seller_x, seller_y);
                            }
                        } catch (JSONException e) {
                            Log.d("@@handler@@","err");
                            e.printStackTrace();
                        }
                        mapsactivity.setListViewData();
                        break;
                }
            }
        }
    }

    public void  getJSON() {
        Thread thread = new Thread(new Runnable() {

            public void run() {

                String result;

                try {
                    Log.d("url", "http://192.168.43.234:9998/databylocale/");
                    URL url = new URL("http://192.168.43.234:9998/databylocale/");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setReadTimeout(3000);
                    httpURLConnection.setConnectTimeout(3000);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.connect();

                    int responseStatusCode = httpURLConnection.getResponseCode();

                    InputStream inputStream;
                    if (responseStatusCode == HttpURLConnection.HTTP_OK) {

                        inputStream = httpURLConnection.getInputStream();
                    } else {
                        inputStream = httpURLConnection.getErrorStream();

                    }


                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }

                    bufferedReader.close();
                    httpURLConnection.disconnect();

                    result = sb.toString().trim();


                } catch (Exception e) {
                    result = e.toString();
                }

                Message message = mHandler.obtainMessage(101, result);
                mHandler.sendMessage(message);
            }

        });
        thread.start();
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    public void onClickSU(View v) {
        HttpAsyncTask httpTask = new HttpAsyncTask(MapsActivity.this);
        //httpTask.execute("http://192.168.43.234:9998/postdata/new/");
        httpTask.execute("http://192.168.43.234:9998/databylocale/");
    }
}
