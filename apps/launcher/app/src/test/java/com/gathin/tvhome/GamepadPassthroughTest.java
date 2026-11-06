package com.gathin.tvhome;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GamepadPassthroughTest {
    private static final int XBOX_CONTROLLER = 0x01000511;
    private static final int TCL_VOICE_REMOTE = 0x01000311;
    private static final int TCL_VIRTUAL_SEARCH_KEYBOARD = 0x00000701;
    private static final int KEYPAD = 0x00000101;

    @Test public void xboxControllerIsAGamepad() {
        assertTrue(GamepadPassthrough.isGamepad(XBOX_CONTROLLER));
    }

    @Test public void voiceRemoteWithJoystickIsNot() {
        assertFalse(GamepadPassthrough.isGamepad(TCL_VOICE_REMOTE));
    }

    @Test public void virtualSearchKeyboardIsNot() {
        assertFalse(GamepadPassthrough.isGamepad(TCL_VIRTUAL_SEARCH_KEYBOARD));
    }

    @Test public void keypadIsNot() {
        assertFalse(GamepadPassthrough.isGamepad(KEYPAD));
    }
}
