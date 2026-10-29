package com.gathin.tvhome;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

final class ServiceRepair {
    private static final String TAG = "TvHome.ServiceRepair";
    private static final String RUNNING_HELPERS = "accessibility_helpers";
    private static final String SEPARATOR = ":";

    private ServiceRepair() { }

    static void noteRunning(Context context, Class<?> service) {
        runningHelpers(context).edit().putBoolean(service.getName(), true).apply();
    }

    static void forget(Context context, Class<?> service) {
        runningHelpers(context).edit().remove(service.getName()).apply();
    }

    static void putBackMissing(Context context) {
        if (context.checkSelfPermission(Manifest.permission.WRITE_SECURE_SETTINGS)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        try {
            ContentResolver resolver = context.getContentResolver();
            String enabledList = Settings.Secure.getString(resolver,
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            String repairedList = withMissingHelpers(context, enabledList);
            if (repairedList == null) return;

            Settings.Secure.putString(resolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
                    repairedList);
            Settings.Secure.putInt(resolver, Settings.Secure.ACCESSIBILITY_ENABLED, 1);
        } catch (RuntimeException refused) {
            Log.w(TAG, "could not put the key services back: " + refused.getMessage());
        }
    }

    private static String withMissingHelpers(Context context, String enabledList) {
        List<ComponentName> enabled = parse(enabledList);
        boolean hasEntries = enabledList != null && !enabledList.isEmpty()
                && !enabledList.equals("null");
        StringBuilder repaired = new StringBuilder(hasEntries ? enabledList : "");
        boolean anyMissing = false;
        for (String helper : runningHelpers(context).getAll().keySet()) {
            ComponentName name = new ComponentName(context.getPackageName(), helper);
            if (enabled.contains(name)) continue;

            if (repaired.length() > 0) repaired.append(SEPARATOR);
            repaired.append(name.flattenToShortString());
            anyMissing = true;
        }

        return anyMissing ? repaired.toString() : null;
    }

    private static List<ComponentName> parse(String enabledList) {
        List<ComponentName> enabled = new ArrayList<>();
        if (enabledList == null) return enabled;

        for (String entry : enabledList.split(SEPARATOR)) {
            ComponentName name = ComponentName.unflattenFromString(entry);
            if (name != null) enabled.add(name);
        }
        return enabled;
    }

    private static SharedPreferences runningHelpers(Context context) {
        return context.getSharedPreferences(RUNNING_HELPERS, Context.MODE_PRIVATE);
    }
}
