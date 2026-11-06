package com.gathin.tvhome;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.hardware.input.InputManager;
import android.view.InputDevice;

final class GamepadPassthrough implements InputManager.InputDeviceListener {
    private static final int FILTER_KEYS = AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS;

    private final AccessibilityService service;
    private final InputManager inputs;

    GamepadPassthrough(AccessibilityService service) {
        this.service = service;
        this.inputs = (InputManager) service.getSystemService(Context.INPUT_SERVICE);
    }

    static GamepadPassthrough startIfWanted(AccessibilityService service, Config config) {
        if (!config.gamepadPassthrough || !config.keyFilter) return null;

        GamepadPassthrough passthrough = new GamepadPassthrough(service);
        passthrough.start();
        return passthrough;
    }

    static boolean isGamepad(int sources) {
        return hasAll(sources, InputDevice.SOURCE_GAMEPAD)
                && hasAll(sources, InputDevice.SOURCE_JOYSTICK);
    }

    private static boolean hasAll(int sources, int source) {
        return (sources & source) == source;
    }

    void start() {
        inputs.registerInputDeviceListener(this, null);
        filterUnlessGamepad();
    }

    void stop() {
        inputs.unregisterInputDeviceListener(this);
    }

    private boolean gamepadConnected() {
        for (int id : inputs.getInputDeviceIds()) {
            InputDevice device = inputs.getInputDevice(id);
            if (device == null || device.isVirtual()) continue;
            if (isGamepad(device.getSources())) return true;
        }
        return false;
    }

    private void filterUnlessGamepad() {
        AccessibilityServiceInfo info = service.getServiceInfo();
        if (info == null) return;

        boolean wanted = !gamepadConnected();
        boolean filtering = (info.flags & FILTER_KEYS) != 0;
        if (wanted == filtering) return;

        info.flags ^= FILTER_KEYS;
        service.setServiceInfo(info);
    }

    @Override
    public void onInputDeviceAdded(int deviceId) {
        filterUnlessGamepad();
    }

    @Override
    public void onInputDeviceRemoved(int deviceId) {
        filterUnlessGamepad();
    }

    @Override
    public void onInputDeviceChanged(int deviceId) {
        filterUnlessGamepad();
    }
}
