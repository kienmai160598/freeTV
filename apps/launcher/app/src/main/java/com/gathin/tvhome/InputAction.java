package com.gathin.tvhome;

import android.content.ComponentName;
import android.content.Intent;

/**
 * The configured input_action: an intent action (com.xgimi.action.hdmiPlayer) or, written as
 * "package/.Activity", the vendor's own source picker, for firmware that answers the standard
 * channel URI with "unsupported" (Redmi X65, MiTV-NSOP0:
 * com.xiaomi.mitv.tvplayer/.ExternalSourceActivity). Actions never contain a slash, so the two
 * forms cannot be confused.
 */
final class InputAction {
    private InputAction() { }

    static boolean isComponent(String inputAction) {
        if (inputAction == null) return false;
        int slash = inputAction.indexOf('/');
        return slash > 0 && slash < inputAction.length() - 1
                && inputAction.indexOf('/', slash + 1) < 0;
    }

    static Intent intent(String inputAction) {
        ComponentName component = isComponent(inputAction)
                ? ComponentName.unflattenFromString(inputAction) : null;
        return component != null ? new Intent().setComponent(component) : new Intent(inputAction);
    }
}
