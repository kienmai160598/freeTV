package com.gathin.tvhome;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InputActionTest {
    @Test public void anActivityIsAComponent() {
        assertTrue(InputAction.isComponent("com.xiaomi.mitv.tvplayer/.ExternalSourceActivity"));
        assertTrue(InputAction.isComponent("a.b/a.b.Picker"));
    }

    @Test public void anActionOrAMalformedNameIsNot() {
        assertFalse(InputAction.isComponent("com.xgimi.action.hdmiPlayer"));
        assertFalse(InputAction.isComponent(""));
        assertFalse(InputAction.isComponent(null));
        assertFalse(InputAction.isComponent("/.Activity"));
        assertFalse(InputAction.isComponent("a.b/"));
        assertFalse(InputAction.isComponent("a.b/.C/.D"));
    }
}
