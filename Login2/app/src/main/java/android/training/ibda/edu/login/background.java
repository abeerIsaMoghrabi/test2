package android.training.ibda.edu.login;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.training.ibda.edu.login.activity.DisplayUserInfo;
import android.training.ibda.edu.login.activity.MainActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by isa moghrabi on 09/22/2017.
 */

public class background extends AsyncTask< String , String, String> {
private Context context;
    private String Method , FN_b,LN_b,email_b,age_b,gender_b,pass_b;
    private String result="";
   public  background(Context cont){
       this.context=cont;

   }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        if(Method.equals(constant.SIGN_UP)){
            checkValidation(result);
        }
        else if(Method.equals(constant.SIGN_IN)) {


            parseJson(result);//????????????????


        }
        else {
            displayToast(result);
            redirect();
        }




    }

    @Override
    protected String doInBackground(String... params) {
        Method=params[0];

        if(Method.equals(constant.SIGN_UP)){

            FN_b=params[1];
            LN_b=params[2];
            email_b=params[3];
            age_b=params[4];
            gender_b=params[5];
            pass_b=params[6];
          result= signUp();
        }
        else if(Method.equals(constant.SIGN_IN)){

            email_b=params[1];
            pass_b=params[2];


           result= signIn();
        }
else if(Method.equals(constant.EDIT)){
            FN_b=params[1];
            LN_b=params[2];
            email_b=params[3];
            age_b=params[4];
            gender_b=params[5];
            pass_b=params[6];
           result= edit_a();
        }
        return result;
    }

    private String signIn() {
        String json_result = null;
        try {
            String data =   URLEncoder.encode("action","UTF-8")+"="+URLEncoder.encode(Method,"UTF-8")+"&"+
                    URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email_b,"UTF-8")+"&"+
                    URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass_b,"UTF-8");
           json_result= openConnectionAndSendData(data);



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return json_result ;//?????????????
    }

    private String signUp() {

        String notUse = null;
        try {
            String data =   URLEncoder.encode("action","UTF-8")+"="+URLEncoder.encode(Method,"UTF-8")+"&"+
                    URLEncoder.encode("FN","UTF-8")+"="+URLEncoder.encode(FN_b,"UTF-8")+"&"+
                    URLEncoder.encode("LN","UTF-8")+"="+URLEncoder.encode(LN_b,"UTF-8")+"&"+
                    URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email_b,"UTF-8")+"&"+
                    URLEncoder.encode("age","UTF-8")+"="+URLEncoder.encode(age_b,"UTF-8")+"&"+
                    URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(gender_b,"UTF-8")+"&"+
                    URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass_b,"UTF-8");
            notUse= openConnectionAndSendData(data);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return  notUse;
    }
    private String edit_a(){
        String ignore=signUp();
      return   "edit done successfully";


    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

 private String openConnectionAndSendData(String data){
     String response="";
     String signUp_url="http://192.168.58.2/Android/controller.php";
     StringBuilder stringBuilder=new StringBuilder();
     try {
     URL url=new URL(signUp_url);
     HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
     httpURLConnection.setRequestMethod("POST");// post method need to encrypt data before send it
     httpURLConnection.setDoOutput(true);
     OutputStream OS=httpURLConnection.getOutputStream();
     BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
         bufferedWriter.write(data);

         bufferedWriter.flush();
         bufferedWriter.close();
         OS.close();

         InputStream IS=httpURLConnection.getInputStream();

         if(Method.equals(constant.SIGN_IN)||Method.equals(constant.SIGN_UP)){
             InputStream inputStream=httpURLConnection.getInputStream();
             BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));


             if ((response=bufferedReader.readLine())!=null){
                    stringBuilder.append(response+"\n");
             }
             inputStream.close();
             bufferedReader.close();
         }

         IS.close();
         httpURLConnection.disconnect();



     } catch (MalformedURLException e) {
         e.printStackTrace();
         Log.e("(MalformedURLException","hi1");
     } catch (IOException e) {
         Log.e("IOException","hi2");
         e.printStackTrace();
     }
     return stringBuilder.toString().trim() ;
 }

private void displayToast(String txt){
    Toast.makeText(context,txt,Toast.LENGTH_LONG).show();
}
private void parseJson(String json_r){


       if(json_r.equals("null")){
        displayToast("cant find this account plz check you information");

    }
    else{
        Intent intent = new Intent(this.context, DisplayUserInfo.class);


        intent.putExtra("json_data", json_r);

        this.context.startActivity(intent);
    }

}
private void redirect()  {



    try {
        Intent intent = new Intent(context, DisplayUserInfo.class);
        JSONObject item = new JSONObject();
        item.put("FN",FN_b);
        item.put("LN",LN_b);
        item.put("emali",email_b);
        item.put("age",age_b);
        item.put("pass",pass_b);
        item.put("gender",gender_b);
        intent.putExtra("json_data",item.toString());

        context.startActivity(intent);
    } catch (JSONException e) {
        e.printStackTrace();
    }

}

public void checkValidation(String result){
    if(result.equals("no")){
        displayToast("this email is already exist");

    }
    else {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);

    }

}
}
