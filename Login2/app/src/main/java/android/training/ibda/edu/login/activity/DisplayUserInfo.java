package android.training.ibda.edu.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.training.ibda.edu.login.DB.SharedPreferencesManager;
import android.training.ibda.edu.login.R;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DisplayUserInfo extends AppCompatActivity implements View.OnClickListener {
   private String jsonData;
    private JSONObject jsonObject;
    private TextView FN,LN,age,email ,pass;
    private RadioGroup radioGroup;
    private RadioButton female,male;
    private Button signOut,edit;
    private String gender="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.activity_display_user_info);
        jsonData=getIntent().getExtras().getString("json_data");
        SharedPreferencesManager singlton=SharedPreferencesManager.getSharedPreferencesManager(this);


        try {
            jsonObject=new JSONObject(jsonData);

            singlton.setUserHistory(jsonObject);
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
        FN=(TextView) findViewById(R.id.FN_dis);
        LN=(TextView) findViewById(R.id.LN_dis);
        age=(TextView) findViewById(R.id.age_dis);
        email=(TextView) findViewById(R.id.email_dis);
        radioGroup=(RadioGroup) findViewById(R.id.RBTN_group_dis);
        female=(RadioButton) findViewById(R.id.female_dis);
        male=(RadioButton) findViewById(R.id.male_dis);
        edit=(Button) findViewById(R.id.edit);
        signOut=(Button) findViewById(R.id.signout);
        pass=(TextView) findViewById(R.id.pass_dis);


    }

    private void register() {
        signOut.setOnClickListener(this);
        edit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.edit){
           Intent intent = new Intent(this, android.training.ibda.edu.login.activity.edit.class);


           intent.putExtra("json_data", jsonData);

           this.startActivity(intent);

       }
       else if(v.getId()==R.id.signout){
           SharedPreferencesManager singlton=SharedPreferencesManager.getSharedPreferencesManager(this);
           singlton.clearUserHistory();
           Intent intent = new Intent(this, android.training.ibda.edu.login.activity.MainActivity.class);
           this.startActivity(intent);
           this.finish();
       }
    }
}
