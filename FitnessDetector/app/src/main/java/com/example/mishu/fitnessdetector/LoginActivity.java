package com.example.mishu.fitnessdetector;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    private EditText Name,Password,email;
    private Button Login,SignUp;

    private TextView NameErr,PassErr,Attempts;

    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        Login = (Button) findViewById(R.id.btnLogin);
        NameErr = (TextView) findViewById(R.id.tvNameEr);
        PassErr = (TextView) findViewById(R.id.tvPassEr);
        Attempts = (TextView) findViewById(R.id.tvAttempts);
        SignUp = (Button) findViewById(R.id.btn_signup);

        NameErr.setVisibility(View.INVISIBLE);
        PassErr.setVisibility(View.INVISIBLE);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = Name.getText().toString();
                String pass = Password.getText().toString();
                if(!(name.isEmpty() && pass.isEmpty())) {
                    new LoginBackground().execute(name, pass);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Fill The Fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ResisterActivity.class);
                startActivity(intent);
            }
        });




    }




    public class LoginBackground extends AsyncTask<String,Void,String> {

        AlertDialog alertDialog;
        String resu = "";
        String username, pass, loginurl;

        @Override
        protected String doInBackground(String... params) {
            try {
                username = params[0];
                pass = params[1];

                URL url = new URL(loginurl);
                HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
                httpurlconnection.setRequestMethod("POST");
                httpurlconnection.setDoOutput(true);
                httpurlconnection.setDoInput(true);
                OutputStream outputstream = httpurlconnection.getOutputStream();
                BufferedWriter bufferedwritter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));
                String postdata = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
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
                return resu;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //
        @Override
        protected void onPreExecute() {
            loginurl = "http://192.168.1.7/myapp/login.php";
            alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
            alertDialog.setTitle("Login Information");
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
            if(result.equals("Login Successful")){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
            else{
                NameErr.setVisibility(View.VISIBLE);
                PassErr.setVisibility(View.VISIBLE);
            }


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
