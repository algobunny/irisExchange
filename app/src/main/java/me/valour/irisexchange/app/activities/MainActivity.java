package me.valour.irisexchange.app.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.logging.Logger;

import me.valour.irisexchange.app.R;
import me.valour.irisexchange.app.fragments.CaptureFragment;
import me.valour.irisexchange.app.fragments.RegisterFragment;
import me.valour.irisexchange.app.fragments.DashboardFragment;
import me.valour.irisexchange.app.fragments.LoginFragment;

public class MainActivity extends Activity implements DashboardFragment.DashboardEventListener {

    FragmentManager fm;
    DashboardFragment dashboard;

    static final int CAMERA_ACTIVITY = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = this.getFragmentManager();

        if(getToken()==null){
            toggleLoginRegister(false);
        } else {
           showDashboard();
        }

      //  dashboard = (DashboardFragment) this.getFragmentManager().findFragmentById(R.id.dashboardFragment);
      //  registerFragment = (RegisterFragment) fm.findFragmentById(R.id.mainFragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
       /* if (id == R.id.action_settings) {
            return true;
        } */
        return super.onOptionsItemSelected(item);
    }

    public void toggleLoginRegister(boolean registerActive){
        if(registerActive){
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.container, loginFragment);
            transaction.commit();
        } else {
          RegisterFragment registerFragment = new RegisterFragment();
          FragmentTransaction transaction = fm.beginTransaction();
          transaction.replace(R.id.container, registerFragment);
          transaction.commit();
        }
    }

    public void showDashboard(){
        dashboard = new DashboardFragment();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, dashboard);
        transaction.commit();
    }

    @Override
    public void launchCapture(){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, CAMERA_ACTIVITY);
    }

    @Override
    public void launchSettings() {

    }

    @Override
    public void launchInbox() {

    }

    /**
     * Set user token
     * @param token
     */
    public void setToken(String token){
        SharedPreferences sp = this.getPreferences(this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token",token);
        editor.apply();
    }

    /**
     * Get user token
     * @return
     */
    public String getToken(){
       SharedPreferences sp = this.getPreferences(this.MODE_PRIVATE);
       return sp.getString("token", null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }
        switch (requestCode){
            case CAMERA_ACTIVITY:
                if(data.getExtras().containsKey("image_path")) {
                    String image_path = data.getStringExtra("image_path");
                    Toast.makeText(this, image_path, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
