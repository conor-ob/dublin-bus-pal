package ie.dublinbuspal.android.view.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.view.home.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFirstLaunch()) {
            //startTutorialActivity();
        }
        startHomeActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PreferenceManager.getDefaultSharedPreferences(this)
                        .edit()
                        .putBoolean(getString(R.string.preference_key_first_launch), false)
                        .apply();
                startHomeActivity();
            } else {
                finish();
            }
        }
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void startTutorialActivity() {
        //Intent intent = new Intent(this, TutorialActivity.class);
        //startActivityForResult(intent, REQUEST_CODE);
    }

    private boolean isFirstLaunch() {
        return PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(getString(R.string.preference_key_first_launch), true);
    }

}
