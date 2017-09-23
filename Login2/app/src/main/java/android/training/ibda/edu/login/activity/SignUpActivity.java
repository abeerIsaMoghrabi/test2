package android.training.ibda.edu.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.training.ibda.edu.login.R;
import android.training.ibda.edu.login.background;
import android.training.ibda.edu.login.constant;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity  implements View.OnClickListener , RadioGroup.OnCheckedChangeListener{
   private EditText FN,LN,age,email ,pass;
    private  RadioGroup radioGroup;
    private RadioButton female,male;
    private Button signIn,signUp;
    private String gender="male";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.activity_sign_up);

    }
    private void init(int obj){
        setContentView(obj);
        loadView();
        register();

    }

    private void loadView() {
        FN=(EditText)findViewById(R.id.FN);
        LN=(EditText)findViewById(R.id.LN);
        age=(EditText)findViewById(R.id.age);
        email=(EditText)findViewById(R.id.email_2);
        radioGroup=(RadioGroup) findViewById(R.id.RBTN_group);
        female=(RadioButton) findViewById(R.id.female);
        male=(RadioButton) findViewById(R.id.male);
        signIn=(Button) findViewById(R.id.signin2);
        signUp=(Button) findViewById(R.id.signup2);
        pass=(EditText) findViewById(R.id.pass_2);


    }

    private void register() {
        signUp.setOnClickListener(this);
        signIn.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signin2){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        else{

         goToBackGround();

        }
    }

    private void goToBackGround() {
        String FN_s=FN.getText().toString();
        String LN_s=LN.getText().toString();
        String email_s=email.getText().toString();
        String age_s=age.getText().toString();
        String pass_s=pass.getText().toString();
        if(FN_s.equals("")||LN_s.equals("")||email_s.equals("")||age_s.equals("")||pass_s.equals("")){
            displayToast("plz fil all info");
        }
else {
            background backObj = new background(this);
            String method = constant.SIGN_UP;
            backObj.execute(method, FN_s, LN_s, email_s, age_s, gender, pass_s);

        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if(checkedId==R.id.male){
            gender="male";
        }
        else {
            gender="female";
        }
    }
    private void displayToast(String txt){
        Toast.makeText(this,txt,Toast.LENGTH_LONG).show();
    }
}



