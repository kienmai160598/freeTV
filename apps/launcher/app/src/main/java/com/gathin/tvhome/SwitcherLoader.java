package com.gathin.tvhome;

import android.app.Activity;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

final class SwitcherLoader {

    interface Listener {
        void onLoading();

        void onLoaded(List<SwitcherEntry> entries);

        void onFailed(int message);
    }

    private final ExecutorService worker = Executors.newSingleThreadExecutor();
    private final Activity activity;
    private final Listener listener;
    private final LocalAdb adb;
    private volatile Set<String> launchable;
    private volatile boolean shutDown;
    private boolean busy;

    SwitcherLoader(Activity activity, Listener listener) {
        this.activity = activity;
        this.listener = listener;
        this.adb = new LocalAdb(activity);
    }

    boolean busy() {
        return busy;
    }

    void load() {
        busy = true;
        listener.onLoading();
        worker.execute(this::read);
    }

    void stop(List<String> packages) {
        worker.execute(() -> {
            try {
                BackgroundApps.stop(adb, packages);
            } catch (IOException e) {
                deliver(() -> listener.onFailed(R.string.switcher_stop_failed));
            }
        });
    }

    void shutdown() {
        shutDown = true;
        worker.shutdown();
    }

    private void read() {
        try {
            if (launchable == null) launchable = BackgroundApps.launchable(activity);
            List<BackgroundApps.App> apps = BackgroundApps.list(adb, launchable::contains);
            List<SwitcherEntry> entries = SwitcherEntry.resolve(activity, apps);
            deliver(() -> listener.onLoaded(entries));
        } catch (LocalAdb.OffException e) {
            deliver(() -> listener.onFailed(R.string.running_off));
        } catch (LocalAdb.NotAllowedException e) {
            deliver(() -> listener.onFailed(R.string.running_denied));
        } catch (IOException e) {
            deliver(() -> listener.onFailed(R.string.running_failed));
        }
    }

    private void deliver(Runnable result) {
        activity.runOnUiThread(() -> {
            busy = false;
            if (!shutDown) result.run();
        });
    }
}
