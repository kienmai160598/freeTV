package com.gathin.tvhome;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

final class HomeBackground {

    private final WallpaperPlayer wallpaper;
    private final ExpressiveBackdrop shapes;

    private HomeBackground(WallpaperPlayer wallpaper, ExpressiveBackdrop shapes) {
        this.wallpaper = wallpaper;
        this.shapes = shapes;
    }

    static HomeBackground fill(FrameLayout root, View content, Config config, Palette palette) {
        Context context = root.getContext();
        root.setBackgroundColor(palette.surface);
        WallpaperPlayer wallpaper = new WallpaperPlayer(context, root, config);
        String look = HomeLook.backdrop(
                context.getSharedPreferences(Config.SETTINGS_NAME, Context.MODE_PRIVATE));
        ExpressiveBackdrop shapes = HomeLook.SHAPES.equals(look) && !wallpaper.showing()
                ? addShapes(root, config, palette)
                : null;
        addSky(root, content, look, config);
        return new HomeBackground(wallpaper, shapes);
    }

    void startMotion() {
        if (shapes != null) shapes.start();
    }

    void stopMotion() {
        if (shapes != null) shapes.stop();
    }

    void close() {
        wallpaper.close();
    }

    private static ExpressiveBackdrop addShapes(FrameLayout root, Config config,
            Palette palette) {
        ExpressiveBackdrop shapes = new ExpressiveBackdrop(root.getContext(), config.animations,
                config.reducedMotion, config.advancedEffects);
        shapes.setPalette(palette);
        shapes.matchHomeParallax();
        root.addView(shapes, 0, fullScreen());
        return shapes;
    }

    private static void addSky(FrameLayout root, View content, String look, Config config) {
        int intensity = HomeLook.skyIntensity(look, config.skyIntensity, Palette.darkMode());
        if (intensity == 0) return;

        SkyView sky = new SkyView(root.getContext(), null);
        sky.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        root.addView(sky, root.indexOfChild(content), fullScreen());
        sky.setIntensity(intensity);
    }

    private static FrameLayout.LayoutParams fullScreen() {
        return new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
