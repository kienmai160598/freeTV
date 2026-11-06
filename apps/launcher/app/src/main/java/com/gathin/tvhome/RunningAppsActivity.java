package com.gathin.tvhome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public final class RunningAppsActivity extends Activity {

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        startActivity(new Intent(this, AppSwitcherActivity.class));
        finish();
        overridePendingTransition(0, 0);
    }
}
