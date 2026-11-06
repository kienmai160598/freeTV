package com.gathin.tvhome;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

final class AppBanner {

    private static final float MIN_BANNER_RATIO = 1.4f;

    private AppBanner() {
    }

    static Drawable load(Context context, PackageManager pm, ActivityInfo activity) {
        if (SettingsBanner.PACKAGE.equals(activity.packageName)) return new SettingsBanner(context);

        Drawable banner = loadAtTileDensity(context, pm, activity);
        if (banner == null) banner = activity.loadBanner(pm);
        if (banner == null) return null;

        boolean shaped = isBannerShaped(banner.getIntrinsicWidth(), banner.getIntrinsicHeight());
        return shaped ? banner : null;
    }

    static boolean isBannerShaped(int width, int height) {
        return width <= 0 || height <= 0 || width >= height * MIN_BANNER_RATIO;
    }

    private static Drawable loadAtTileDensity(
            Context context, PackageManager pm, ActivityInfo activity) {
        int bannerId = activity.banner != 0 ? activity.banner : activity.applicationInfo.banner;
        if (bannerId == 0) return null;

        int screenDensity = context.getResources().getDisplayMetrics().densityDpi;
        int density = Math.max(DisplayMetrics.DENSITY_XHIGH, screenDensity);
        try {
            Resources resources = pm.getResourcesForApplication(activity.applicationInfo);
            return resources.getDrawableForDensity(bannerId, density, null);
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException missing) {
            return null;
        }
    }
}
