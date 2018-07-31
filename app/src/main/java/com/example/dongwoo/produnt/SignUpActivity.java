package com.example.dongwoo.produnt;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length() > 0) buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }
    JSONObject jsonOb;
    public String POST(String pUrl) {
        String result = "";
        InputStream is = null;
        EditText eId = (EditText) findViewById(R.id.idEditText);
        String id = eId.getText().toString();
        EditText ePw = (EditText) findViewById(R.id.pwEditText);
        String pw = ePw.getText().toString();
        EditText eName = (EditText) findViewById(R.id.nameText);
        String name = eName.getText().toString();
        EditText ePhoneNumber = (EditText) findViewById(R.id.phoneNumberText);
        String phoneNumber = ePhoneNumber.getText().toString();
        EditText eCarrier = (EditText) findViewById(R.id.carrierText);
        String carrier = eCarrier.getText().toString();
        String macAddress = getMACAddress("wlan0");
        jsonOb = new JSONObject();
        try {
            jsonOb.put("Name", name);
            jsonOb.put("Cash", 0);
            jsonOb.put("MAC", macAddress);
            jsonOb.put("Good", 0);
            jsonOb.put("Bad", 0);
            jsonOb.put("Coord_x", 0.0);
            jsonOb.put("Coord_y", 0.0);
            jsonOb.put("Uid", id);
            jsonOb.put("Password", pw);
            jsonOb.put("PhoneNumber", phoneNumber);
            jsonOb.put("Provider", carrier);
            jsonOb.put("Status", 0);
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
        private   SignUpActivity signUPAct;
        HttpAsyncTask(SignUpActivity signUPAct) {
            this.signUPAct = signUPAct;
        }
        @Override
        protected String doInBackground(String... urls) {
            return POST(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            signUPAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(signUPAct, result, Toast.LENGTH_LONG).show();
                }
            });
        }
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
        HttpAsyncTask httpTask = new HttpAsyncTask(SignUpActivity.this);
        httpTask.execute("http://172.30.7.165:9998/postdata/new/");
    }
    public void onClickFinish(View v) {
        finish();
    }
}
