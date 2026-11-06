package com.gathin.tvhome;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Lets the user change HOME through the system UI, without shell or privileged permissions. Android
 * 14's picker (permissioncontroller) is translucent, so this stays behind it as a black backdrop
 * until the picker is left; over Home, its text sat on the tiles.
 */
public class HomeSettingsActivity extends Activity {
    private boolean pickerLeft;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        if (state != null) return;
        try {
            startActivity(new Intent(Settings.ACTION_HOME_SETTINGS));
            Toast.makeText(this, R.string.home_settings_help, Toast.LENGTH_LONG).show();
        } catch (ActivityNotFoundException | SecurityException unavailable) {
            Toast.makeText(this, R.string.not_available, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pickerLeft = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pickerLeft) finish();
    }
}
