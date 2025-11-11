package it.unibo.deathnote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.deathnote.api.DeathNoteImpl;
import java.util.List;
import static it.unibo.deathnote.api.DeathNote.RULES;
import static org.junit.jupiter.api.Assertions.*;


import it.unibo.deathnote.api.DeathNote;

class TestDeathNote {

    private DeathNote deathnote;

    @BeforeEach
    void setUp() {
        deathnote = new DeathNoteImpl();
    }

    @Test
    void testIllegalRule() {
        for (final var index: List.of(-1, 0, RULES.size() + 1)) {
            assertThrows(
                new IllegalArgumentThrower() {
                    @Override
                    public void run() {
                        deathnote.getRule(index);
                    }
                }
            );
        }
    }

    static void assertThrows(final RuntimeExceptionThrower exceptionThrower) {
        try {
            exceptionThrower.run();
            fail("An exception was expected");
        } catch (IllegalStateException | IllegalArgumentException e) {
            assertTrue(
                exceptionThrower instanceof IllegalArgumentThrower && e instanceof IllegalArgumentException
                || 
        }

    }

    @FunctionalInterface
    private interface RuntimeExceptionThrower {
        void run();
    }

}