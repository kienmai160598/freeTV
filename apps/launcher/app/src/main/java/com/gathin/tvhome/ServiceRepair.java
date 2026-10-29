package com.gathin.tvhome;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

final class ServiceRepair {
    private static final String TAG = "TvHome.ServiceRepair";
    private static final String RUNNING_HELPERS = "accessibility_helpers";
    private static final String SEPARATOR = ":";
    private static final long STEP_PAUSE_MS = 1000;

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
            ContentResolver resolver = context.getApplicationContext().getContentResolver();
            String enabledList = readList(resolver);
            List<ComponentName> recorded = recordedHelpers(context);
            List<ComponentName> enabled = parse(enabledList);
            if (enabled.containsAll(recorded)) return;

            String withoutHelpers = join(enabled, recorded, false);
            String withHelpers = join(enabled, recorded, true);
            String[] listSteps = {enabledList, withHelpers, withoutHelpers, withHelpers};
            new RepairCycle(resolver, listSteps).run();
        } catch (RuntimeException refused) {
            logRefusal(refused);
        }
    }

    private static List<ComponentName> recordedHelpers(Context context) {
        List<ComponentName> recorded = new ArrayList<>();
        for (String helper : runningHelpers(context).getAll().keySet()) {
            recorded.add(new ComponentName(context.getPackageName(), helper));
        }
        return recorded;
    }

    private static String join(List<ComponentName> enabled, List<ComponentName> recorded,
            boolean withHelpers) {
        StringBuilder list = new StringBuilder();
        for (ComponentName name : enabled) {
            if (!recorded.contains(name)) append(list, name);
        }
        if (!withHelpers) return list.toString();

        for (ComponentName name : recorded) append(list, name);
        return list.toString();
    }

    private static void append(StringBuilder list, ComponentName name) {
        if (list.length() > 0) list.append(SEPARATOR);
        list.append(name.flattenToShortString());
    }

    private static List<ComponentName> parse(String enabledList) {
        List<ComponentName> enabled = new ArrayList<>();
        if (enabledList == null) return enabled;

        for (String entry : enabledList.split(SEPARATOR)) {
            ComponentName name = ComponentName.unflattenFromString(entry);
            if (name != null && !enabled.contains(name)) enabled.add(name);
        }
        return enabled;
    }

    private static String readList(ContentResolver resolver) {
        return Settings.Secure.getString(resolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
    }

    private static boolean replaceList(ContentResolver resolver, String expected, String updated) {
        String current = readList(resolver);
        return Objects.equals(orEmpty(current), orEmpty(expected))
                && Settings.Secure.putString(resolver,
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, updated);
    }

    private static String orEmpty(String list) {
        return list == null ? "" : list;
    }

    private static void logRefusal(RuntimeException refused) {
        Log.w(TAG, "could not put the key services back: " + refused.getMessage());
    }

    private static SharedPreferences runningHelpers(Context context) {
        return context.getSharedPreferences(RUNNING_HELPERS, Context.MODE_PRIVATE);
    }

    private static final class RepairCycle implements Runnable {
        private final ContentResolver resolver;
        private final String[] listSteps;
        private final Handler pause = new Handler(Looper.getMainLooper());
        private int step;

        RepairCycle(ContentResolver resolver, String[] listSteps) {
            this.resolver = resolver;
            this.listSteps = listSteps;
        }

        @Override
        public void run() {
            try {
                if (!replaceList(resolver, listSteps[step], listSteps[step + 1])) return;

                step++;
                if (step + 1 < listSteps.length) {
                    pause.postDelayed(this, STEP_PAUSE_MS);
                    return;
                }
                Settings.Secure.putInt(resolver, Settings.Secure.ACCESSIBILITY_ENABLED, 1);
            } catch (RuntimeException refused) {
                logRefusal(refused);
            }
        }
    }
}
