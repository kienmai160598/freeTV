package com.gathin.tvhome;

import android.content.Context;
import android.text.format.Formatter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

final class SwitcherAdapter extends RecyclerView.Adapter<SwitcherAdapter.Holder> {

    interface Listener {
        void onOpen(SwitcherEntry entry);

        void onSwipeUp(SwitcherEntry entry, SwitcherCard card);
    }

    private final List<SwitcherEntry> entries = new ArrayList<>();
    private final Palette palette;
    private final Config config;
    private final Listener listener;

    SwitcherAdapter(Palette palette, Config config, Listener listener) {
        this.palette = palette;
        this.config = config;
        this.listener = listener;
    }

    void submit(List<SwitcherEntry> fresh) {
        entries.clear();
        entries.addAll(fresh);
        notifyDataSetChanged();
    }

    void remove(SwitcherEntry entry) {
        int index = entries.indexOf(entry);
        if (index < 0) return;

        entries.remove(index);
        notifyItemRemoved(index);
    }

    List<String> packages() {
        List<String> packages = new ArrayList<>();
        for (SwitcherEntry entry : entries) packages.add(entry.pkg);
        return packages;
    }

    long totalMemoryKb() {
        long total = 0;
        for (SwitcherEntry entry : entries) total += entry.memoryKb;
        return total;
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new Holder(inflater.inflate(R.layout.item_switcher_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(entries.get(position));
    }

    final class Holder extends RecyclerView.ViewHolder {
        private final ImageView banner, artIcon;
        private final TextView label, memory;
        private final SwitcherCard card;
        private SwitcherEntry entry;

        Holder(View view) {
            super(view);
            banner = view.findViewById(R.id.banner);
            artIcon = view.findViewById(R.id.artIcon);
            label = view.findViewById(R.id.label);
            memory = view.findViewById(R.id.memory);
            card = new SwitcherCard(view, view.findViewById(R.id.art), palette, config);
            RoundedCorners.clip(artIcon, Shapes.LARGE);
            label.setTextColor(palette.onSurface);
            memory.setTextColor(palette.onSurfaceVariant);
            view.setOnFocusChangeListener((v, focused) -> card.showFocus(focused));
            view.setOnClickListener(v -> listener.onOpen(entry));
            view.setOnKeyListener(this::onKey);
        }

        void bind(SwitcherEntry bound) {
            entry = bound;
            boolean hasBanner = bound.banner != null;
            banner.setImageDrawable(bound.banner);
            artIcon.setImageDrawable(hasBanner ? null : bound.icon);
            artIcon.setVisibility(hasBanner ? View.GONE : View.VISIBLE);
            label.setText(bound.label);
            memory.setText(memoryLine(bound));
            card.reset();
        }

        private String memoryLine(SwitcherEntry bound) {
            Context context = itemView.getContext();
            String size = Formatter.formatShortFileSize(context, bound.memoryKb * 1024);
            if (!bound.onScreen) return size;

            return context.getString(R.string.running_on_screen, size);
        }

        private boolean onKey(View view, int keyCode, KeyEvent event) {
            if (keyCode != KeyEvent.KEYCODE_DPAD_UP) return false;

            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                listener.onSwipeUp(entry, card);
            }
            return true;
        }
    }
}
