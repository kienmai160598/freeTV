package com.gathin.tvhome;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

final class SwitcherEntry {

    final String pkg;
    final CharSequence label;
    final Drawable icon;
    final Drawable banner;
    final long memoryKb;
    final boolean onScreen;

    private SwitcherEntry(
            BackgroundApps.App app, CharSequence label, Drawable icon, Drawable banner) {
        this.pkg = app.pkg;
        this.label = label;
        this.icon = icon;
        this.banner = banner;
        this.memoryKb = app.memoryKb;
        this.onScreen = app.recency == 0;
    }

    static List<SwitcherEntry> resolve(Context context, List<BackgroundApps.App> apps) {
        PackageManager pm = context.getPackageManager();
        List<SwitcherEntry> entries = new ArrayList<>();
        for (BackgroundApps.App app : apps) {
            ActivityInfo launcher = launcherActivity(pm, app.pkg);
            if (launcher == null) continue;

            CharSequence label = pm.getApplicationLabel(launcher.applicationInfo);
            Drawable banner = AppBanner.load(context, pm, launcher);
            entries.add(new SwitcherEntry(app, label, launcher.loadIcon(pm), banner));
        }
        return entries;
    }

    static int initialFocus(List<SwitcherEntry> entries) {
        boolean behindIsListed = !entries.isEmpty() && entries.get(0).onScreen;
        return behindIsListed && entries.size() > 1 ? 1 : 0;
    }

    private static ActivityInfo launcherActivity(PackageManager pm, String pkg) {
        Intent launch = pm.getLeanbackLaunchIntentForPackage(pkg);
        if (launch == null) launch = pm.getLaunchIntentForPackage(pkg);
        if (launch == null) return null;

        ResolveInfo resolved = pm.resolveActivity(launch, 0);
        return resolved == null ? null : resolved.activityInfo;
    }
}
