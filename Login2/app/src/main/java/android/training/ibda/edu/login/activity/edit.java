package android.training.ibda.edu.login.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.training.ibda.edu.login.R;
import android.training.ibda.edu.login.background;
import android.training.ibda.edu.login.constant;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class edit extends AppCompatActivity implements View.OnClickListener {

    private String jsonData;
    private JSONObject jsonObject;
    private EditText FN,LN,age,pass;
    private TextView email;
    private RadioGroup radioGroup;
    private RadioButton female,male;
    private Button done,cancel;
    private String gender="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.activity_edit);
        jsonData=getIntent().getExtras().getString("json_data");

        try {
            jsonObject=new JSONObject(jsonData);


            FN.setText(jsonObject.getString("FN"));
            LN.setText(jsonObject.getString("LN"));
            age.setText(jsonObject.getString("age"));
            email.setText(jsonObject.getString("emali"));
            gender=jsonObject.getString("gender");
            pass.setText(jsonObject.getString("pass"));

            if(gender.equals("male")){
                male.setChecked(true);
            }
            else {
                female.setChecked(true);
            }

        } catch (JSONException e) {
            Log.e("json",e.getMessage());
            e.printStackTrace();
        }
    }


    private void init(int obj){
        setContentView(obj);
        loadView();
        register();

    }

    private void loadView() {
        FN= (EditText) findViewById(R.id.FN_e);
        LN= (EditText) findViewById(R.id.LN_e);
        age= (EditText) findViewById(R.id.age_e);
        email= (TextView) findViewById(R.id.email_e);
        radioGroup=(RadioGroup) findViewById(R.id.RBTN_group_e);
        female=(RadioButton) findViewById(R.id.female_e);
        male=(RadioButton) findViewById(R.id.male_e);
        done=(Button) findViewById(R.id.done);
        cancel=(Button) findViewById(R.id.cancel);
        pass= (EditText) findViewById(R.id.pass_e);


    }

    private void register() {
       done.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.done){


            goToBackGround();
        }
        else if(v.getId()==R.id.cancel){
            this.finish();

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
            String method = constant.EDIT;
            backObj.execute(method, FN_s, LN_s, email_s, age_s, gender, pass_s);
        }


    }
    private void displayToast(String txt){
        Toast.makeText(this,txt,Toast.LENGTH_LONG).show();
    }

}
