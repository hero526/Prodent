package com.example.dongwoo.produnt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    private static final String TAG = "imagesearchexample";
    public static final int CONNECT_SUCCESS = 100;
    public static final int LOAD_SUCCESS = 101;
    public static final int PW_WRONG = 102;
    private static String id;
    private String REQUEST_URL ="http://192.168.43.234:9998/getdata/";
    private ProgressDialog progressDialog;
    private EditText login_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginB = (Button)findViewById(R.id.loginButton);
        Button signupB = (Button)findViewById(R.id.signupButton);
        signupB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        login_id = (EditText)findViewById(R.id.idText);
        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = login_id.getText().toString();
                progressDialog = new ProgressDialog( MainActivity.this );
                progressDialog.setMessage("Please wait.....");
                progressDialog.show();
                getJSON();
            }
        });
    }
    private final MyHandler mHandler = new MyHandler(this);
    private class MyHandler extends Handler {
        private final WeakReference<MainActivity> weakReference;
        public MyHandler(MainActivity mainactivity) {
            weakReference = new WeakReference<MainActivity>(mainactivity);
        }
        @Override
        public void handleMessage(Message msg) {
            MainActivity mainactivity = weakReference.get();
            if (mainactivity != null) {
                switch (msg.what) {
                    case LOAD_SUCCESS:
                        mainactivity.progressDialog.dismiss();
                }
            }
            String result = (String)msg.obj;
            if (result.equals("No Correct Data"))
                Toast.makeText(mainactivity, "Wrong ID", Toast.LENGTH_SHORT).show();
            else {
                try {
                    JSONObject jOb = new JSONObject(result);
                    String serverName = jOb.get("Name").toString();
                    int serverCash = (int)jOb.get("Cash");
                    String serverMac = jOb.get("MAC").toString();
                    int serverGood = (int)jOb.get("Good");
                    int serverBad = (int)jOb.get("Bad");
                    double serverCoord_x = (double)jOb.get("Coord_x");
                    double serverCoord_y = (double)jOb.get("Coord_y");
                    String serverUid = jOb.get("Uid").toString();
                    String serverPw = jOb.get("Password").toString();
                    String serverPn = jOb.get("PhoneNumber").toString();
                    String serverPr = jOb.get("Provider").toString();
                    int serverStatus = (int)jOb.get("Status");
                    EditText eUid = (EditText)findViewById(R.id.idText);
                    EditText ePw = (EditText)findViewById(R.id.pwText);
                    String myUid = eUid.getText().toString();
                    String myPw = ePw.getText().toString();
                    if (serverPw.equals(myPw)) {
                        try {
                            BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "user.txt", true));
                            bw.write(serverName + " " + serverCash + " " + serverMac + " " +
                                    serverGood + " " + serverBad + " " + serverCoord_x + " " + serverCoord_y + " " +
                                    serverUid + " " + serverPw + " " + serverPn + " " + serverPr + " " + serverStatus);
                            bw.close();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(mainactivity, roleActivity.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(mainactivity, "Wrong Password", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void getJSON() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                String result;
                try {
                    Log.d(TAG, REQUEST_URL);
                    URL url = new URL(REQUEST_URL + id);
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
                    result = sb.toString().trim( );
                } catch (Exception e) {
                    result = e.toString();
                }
                Message message = mHandler.obtainMessage(LOAD_SUCCESS, result);
                mHandler.sendMessage(message);
            }
        });
        thread.start();
    }
}