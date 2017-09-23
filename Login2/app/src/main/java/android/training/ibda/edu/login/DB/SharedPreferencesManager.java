package android.training.ibda.edu.login.DB;

import android.content.Context;
import android.content.SharedPreferences;
import android.training.ibda.edu.login.constant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by isa moghrabi on 09/23/2017.
 */

public class SharedPreferencesManager {
    private static SharedPreferencesManager inst;
     private Context context;
    SharedPreferences shareObj;
    private SharedPreferencesManager(Context context){
       this.context=context;
         shareObj= this.context.getSharedPreferences(constant.SP_N,Context.MODE_PRIVATE);
    }
   public static SharedPreferencesManager getSharedPreferencesManager(Context con){
        inst=new SharedPreferencesManager(con);
        return inst;

    }
    public JSONObject getUserHistory(){
        //SharedPreferences.Editor editor=shareObj.edit();
        JSONObject json_obj=null;
        String history=shareObj.getString("history",null);
        try {
            if(history!=null){
                json_obj=new JSONObject(history);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json_obj;

    }
    public void setUserHistory(JSONObject obj){
        SharedPreferences.Editor editor=shareObj.edit();
        editor.putString("history", obj.toString());
        editor.commit();
    }
    public void clearUserHistory(){
        shareObj.edit().clear().commit();
    }
}
