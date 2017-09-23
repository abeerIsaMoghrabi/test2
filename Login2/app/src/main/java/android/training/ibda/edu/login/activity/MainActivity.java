package android.training.ibda.edu.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.training.ibda.edu.login.DB.SharedPreferencesManager;
import android.training.ibda.edu.login.R;
import android.training.ibda.edu.login.background;
import android.training.ibda.edu.login.constant;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private EditText email,pass;
    private Button signIn,signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.activity_main);

    }
    private void init(int obj){
        setContentView(obj);
        loadView();
        checkLogin();
        register();

    }

       private void register() {
        signUp.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    private void loadView() {
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pass);
        signIn=(Button) findViewById(R.id.signin);
        signUp=(Button) findViewById(R.id.signup);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signup){
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);

        }
        else{


       goToBackGround();
        }
    }

    private void goToBackGround() {

        String email_s=email.getText().toString();

        String pass_s=pass.getText().toString();
        background backObj=new background(this);
        String method= constant.SIGN_IN;
        backObj.execute(method,email_s,pass_s);
    }
    private void checkLogin(){
        SharedPreferencesManager singlton=SharedPreferencesManager.getSharedPreferencesManager(this);
        JSONObject obj=singlton.getUserHistory();
        if(obj!=null){

            Intent intent = new Intent(this, android.training.ibda.edu.login.activity.DisplayUserInfo.class);
            intent.putExtra("json_data",obj.toString());
            this.startActivity(intent);
            this.finish();
        }


    }
}
