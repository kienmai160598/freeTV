package com.gathin.tvhome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public final class AppSwitcherActivity extends Activity
        implements SwitcherAdapter.Listener, SwitcherLoader.Listener {

    private static final long CONNECTING_DELAY_MS = 400;

    private Config config;
    private Palette palette;
    private SwitcherLoader loader;
    private SwitcherAdapter adapter;
    private FrameLayout screen;
    private View content;
    private RecyclerView cards;
    private SwitcherLayoutManager layoutManager;
    private TextView summary, hint, action;
    private HomeBackground background;
    private SwitcherMotion motion;
    private final Runnable showConnecting = this::showConnecting;
    private boolean closing;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        overridePendingTransition(0, 0);
        Palette.loadDarkMode(this);
        setContentView(R.layout.activity_app_switcher);
        config = Config.load(this);
        palette = Palette.currentOrBaseline();
        loader = new SwitcherLoader(this, this);
        findViews();
        paint();
        adapter = new SwitcherAdapter(palette, config, this);
        layoutManager = new SwitcherLayoutManager(this, config);
        cards.setLayoutManager(layoutManager);
        cards.post(() -> SwitcherLayoutManager.padToCentre(cards));
        cards.setItemAnimator(new SpringItemAnimator(config.reducedMotion));
        cards.setAdapter(adapter);
        SwitcherHighlight.attach(cards, palette, config);
        motion = new SwitcherMotion(screen, content, cards, config);
        motion.enter();
        loader.load();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        close();
    }

    @Override
    public void onBackPressed() {
        close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!closing) background.startMotion();
    }

    @Override
    protected void onPause() {
        background.stopMotion();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!loader.busy() && !isFinishing()) finish();
    }

    @Override
    protected void onDestroy() {
        screen.removeCallbacks(showConnecting);
        loader.shutdown();
        background.close();
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onLoading() {
        screen.postDelayed(showConnecting, CONNECTING_DELAY_MS);
    }

    @Override
    public void onLoaded(List<SwitcherEntry> entries) {
        screen.removeCallbacks(showConnecting);
        if (closing) return;
        if (entries.isEmpty()) {
            showMessage(getString(R.string.running_none), null, false);
            return;
        }

        adapter.submit(entries);
        showSummary();
        hint.setText(R.string.switcher_hint);
        showAction(R.string.running_stop_all, v -> stopAll());
        int focus = SwitcherEntry.initialFocus(entries);
        cards.post(() -> focusCard(focus));
        motion.revealCards();
    }

    @Override
    public void onFailed(int message) {
        screen.removeCallbacks(showConnecting);
        if (closing) return;

        adapter.submit(Collections.emptyList());
        showMessage(getString(message), null, true);
    }

    @Override
    public void onOpen(SwitcherEntry entry) {
        RemoteKeys.launch(this, entry.pkg);
        finish();
    }

    @Override
    public void onSwipeUp(SwitcherEntry entry, SwitcherCard card) {
        loader.stop(Collections.singletonList(entry.pkg));
        card.dismiss(() -> {
            adapter.remove(entry);
            if (adapter.getItemCount() == 0) close();
            else showSummary();
        });
    }

    private void stopAll() {
        loader.stop(adapter.packages());
        close();
    }

    private void focusCard(int position) {
        SwitcherLayoutManager.padToCentre(cards);
        layoutManager.scrollToPositionWithOffset(position, 0);
        cards.post(() -> {
            RecyclerView.ViewHolder target = cards.findViewHolderForAdapterPosition(position);
            if (target != null) target.itemView.requestFocus();
        });
    }

    private void showConnecting() {
        String connecting = getString(R.string.running_connecting);
        showMessage(connecting, getString(R.string.running_allow), false);
    }

    private void showSummary() {
        int count = adapter.getItemCount();
        String apps = getResources().getQuantityString(R.plurals.running_count, count, count);
        String memory = Formatter.formatShortFileSize(this, adapter.totalMemoryKb() * 1024);
        summary.setText(getString(R.string.running_summary, apps, memory));
    }

    private void showMessage(String text, String detail, boolean retry) {
        summary.setText(text);
        hint.setText(detail);
        if (retry) showAction(R.string.try_again, v -> loader.load());
        else action.setVisibility(View.GONE);
    }

    private void showAction(int text, View.OnClickListener click) {
        action.setText(text);
        action.setOnClickListener(click);
        action.setVisibility(View.VISIBLE);
        if (adapter.getItemCount() == 0) action.requestFocus();
    }

    private void findViews() {
        screen = findViewById(R.id.screen);
        content = findViewById(R.id.content);
        cards = findViewById(R.id.cards);
        summary = findViewById(R.id.summary);
        hint = findViewById(R.id.hint);
        action = findViewById(R.id.stopAll);
    }

    private void paint() {
        background = HomeBackground.fill(screen, content, config, palette);
        ((TextView) findViewById(R.id.title)).setTextColor(palette.onSurface);
        summary.setTextColor(palette.onSurfaceVariant);
        hint.setTextColor(palette.onSurfaceVariant);
        M3Skin.apply(content, palette, config.animations, config.reducedMotion);
    }

    private void close() {
        if (closing) return;

        closing = true;
        background.stopMotion();
        motion.exit(this::finish);
    }
}
