package com.example.mishu.fitnessdetector;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class ResisterActivity extends AppCompatActivity {


    private EditText Name,Password,Email,rPassword;
    private Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resister);

        Name = (EditText) findViewById(R.id.etNameR);
        Password = (EditText) findViewById(R.id.etPasswordR);
        Email =(EditText)findViewById(R.id.etEmailR);
        rPassword =(EditText)findViewById(R.id.etRePassR);
        Register = (Button) findViewById(R.id.btnregister);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String password = Password.getText().toString();
                String email = Email.getText().toString();
                String rpassword = rPassword.getText().toString();
                if (!(name.isEmpty() && password.isEmpty() && email.isEmpty() && rpassword.isEmpty())) {
                    if (password.equals(rpassword)) {
                        Toast.makeText(ResisterActivity.this, name + " " + password + " " + email, Toast.LENGTH_SHORT).show();
                        new ResistorTask().execute(name, password, email);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Passwords donot match", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Fill all The Fields",Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    public class ResistorTask extends AsyncTask<String,Void,String>{

        String username,pass,email,loginurl;
        AlertDialog.Builder alert;
        String resu="";

        @Override
        protected void onPreExecute() {
            loginurl = "http://192.168.1.7/myapp/register.php";
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                username = params[0];
                pass = params[1];
                email = params[2];
                URL url = new URL(loginurl);
                HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
                httpurlconnection.setRequestMethod("POST");
                httpurlconnection.setDoOutput(true);
                httpurlconnection.setDoInput(true);
                OutputStream outputstream = httpurlconnection.getOutputStream();
                BufferedWriter bufferedwritter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));
                String postdata = URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&" + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&" + URLEncoder.encode("Pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
                bufferedwritter.write(postdata);
                bufferedwritter.flush();
                bufferedwritter.close();
                outputstream.close();
                InputStream inputstream = httpurlconnection.getInputStream();
                BufferedReader bufferdreader = new BufferedReader(new InputStreamReader(inputstream, "iso-8859-1"));
                String line = "";
                while ((line = bufferdreader.readLine()) != null) {
                    resu += line;
                }
                bufferdreader.close();
                inputstream.close();
                httpurlconnection.disconnect();

                return "Registration Succesfull...";
            } catch(MalformedURLException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ResisterActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
