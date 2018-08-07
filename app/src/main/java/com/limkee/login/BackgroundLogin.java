package com.limkee.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.google.gson.Gson;
import com.limkee.R;
import com.limkee.entity.Customer;
import com.limkee.entity.Driver;
import com.limkee.navigation.NavigationActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Miaozi on 6/8/18.
 */

public class BackgroundLogin extends AsyncTask<String,Void,String> {
    private Context context;
    private String password;
    private String driverID;
    private AlertDialog.Builder builder;
    private String isEnglish;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    BackgroundLogin(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://13.229.114.72:80/JavaBridge/login_driver.php";
        if(type.equals("login")){
            try {
                driverID = params[1];
                password = params[2];
                isEnglish = params[3];
                URL url = new URL(login_url);
                HttpURLConnection huc = (HttpURLConnection)url.openConnection();
                huc.setRequestMethod("POST");
                huc.setDoInput(true);
                huc.setDoOutput(true);
                OutputStream ops = huc.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String post_data = URLEncoder.encode("DriverID","UTF-8")+"="+URLEncoder.encode(driverID,"UTF-8")
                        +"&"+URLEncoder.encode("HashPassword","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bw.write(post_data);
                bw.flush();
                bw.close();
                ops.close();
                InputStream is = huc.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                String result = "";
                String line;
                while((line = br.readLine())!=null){
                    result += line;
                }
                br.close();
                is.close();
                huc.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        TextView pwdValidate = ((Activity)context).findViewById(R.id.pwdvalidation);
        if (!result.equals("login unsuccess")){
            String[] array = result.split(",");
            String driverID = array[0];
            String password = array[1];
            String driverName = array[2];
            String contact = array[3];
            String status = array[4];
            int routeNo = Integer.parseInt(array[5]);

            final Driver driver = new Driver(driverID, password, driverName, contact, status, routeNo);
            loginPreferences = context.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
            loginPrefsEditor = loginPreferences.edit();
            Intent it = new Intent(context, NavigationActivity.class);

            Gson gson = new Gson();
            String json = gson.toJson(driver);
            loginPrefsEditor.putString("driver", json);
            loginPrefsEditor.putString("driverID", driverID);
            loginPrefsEditor.putBoolean("isLogin", true);
            loginPrefsEditor.putBoolean("isAlertDialogue", true);
            loginPrefsEditor.putString("language", isEnglish);
            loginPrefsEditor.apply();

            context.startActivity(it);
        } else{
            if(isEnglish.equals("Yes")) {
                pwdValidate.setText("Invalid Username and/or Password");
            } else {
                pwdValidate.setText("用户名和/或密码错误");
            }

        }
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}