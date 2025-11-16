package it.unibo.deathnote.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static it.unibo.deathnote.api.DeathNote.RULES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestDeathNote {

  private static final String JIM_MORIARTY = "Jim Moriarty";
  private static final String MURDERED = "murdered";
  private static final String MYCROFT_HOLMES = "Mycroft Holmes";
  private static final String SHERLOCK_HOLMES = "Sherlock Holmes";
  private static final String DEFAULT_CAUSE = "heart attack";
  private static final String KARTING_ACCIDENT = "karting accident";
  private static final long INVALID_CAUSE_TIME = 100;
  private static final long INVALID_DETAILS_TIME = 6000 + INVALID_CAUSE_TIME;

  private DeathNote deathnote;

  @BeforeEach
  void setUp() {
    deathnote = new DeathNoteImpl();
  }

  /*
  1. Rule number 0 and negative rules do not exist in the DeathNote rules.
    *check that the exceptions are thrown correctly, that their type is the expected one,
      and that the message is not null, empty, or blank.
  */
  @Test
  void testIllegalRule() {
    for (final int index : List.of(-1, 0, RULES.size() + 1)) {
      final Exception e = assertThrows(IllegalArgumentException.class, () -> {
        deathnote.getRule(index);
      }
      );
      assertNotNull(e.getMessage());
      assertFalse(e.getMessage().isEmpty());
      assertFalse(e.getMessage().isBlank());
    }
  }

  /*
  2. No rule is empty or null in the DeathNote rules.
      * for all the valid rules, check that none is null or blank
  */
  @Test
  void testValidRules() {
    for (int i = 1; i < RULES.size(); i++) {
      assertNotNull(RULES.get(i));
      assertFalse(RULES.get(i).isBlank());
    }
  }

  /*
  3. The human whose name is written in the DeathNote will eventually die.
      * verify that the human has not been written in the notebook yet
      * write the human in the notebook
      * verify that the human has been written in the notebook
      * verify that another human has not been written in the notebook
      * verify that the empty string has not been written in the notebook
  */
  @Test
  void testDeath() {
    assertFalse(deathnote.isNameWritten(JIM_MORIARTY));
    deathnote.writeName(JIM_MORIARTY);
    assertTrue(deathnote.isNameWritten(JIM_MORIARTY));
    assertFalse(deathnote.isNameWritten(MYCROFT_HOLMES));
    assertFalse(deathnote.isNameWritten(""));
  }

  /*
  4. If the cause of death is written within the next 40 milliseconds of writing the person's name,
    it will happen. 
    If the cause of death is not specified, the person will simply die of a heart attack.
      * check that writing a cause of death before writing a name throws the correct exception
      * write the name of a human in the notebook
      * verify that the cause of death is a heart attack
      * write the name of another human in the notebook
      * set the cause of death to "karting accident"
      * verify that the cause of death has been set correctly (returned true, and the cause is indeed "karting accident")
      * sleep for 100ms
      * try to change the cause of death 
      * verify that the cause of death has not been changed
  */
  @Test
  void testDeathInTime() throws InterruptedException {
    final Exception e = assertThrows(IllegalStateException.class, () -> {
      deathnote.writeDeathCause(MURDERED);
    });
    assertNotNull(e.getMessage());
    assertFalse(e.getMessage().isEmpty());
    assertFalse(e.getMessage().isBlank());
    deathnote.writeName(MYCROFT_HOLMES);
    assertEquals(DEFAULT_CAUSE, deathnote.getDeathCause(MYCROFT_HOLMES));
    deathnote.writeName(JIM_MORIARTY);
    assertTrue(deathnote.writeDeathCause(KARTING_ACCIDENT));
    assertEquals(KARTING_ACCIDENT, deathnote.getDeathCause(JIM_MORIARTY));
    Thread.sleep(INVALID_CAUSE_TIME);
    assertFalse(deathnote.writeDeathCause("decapitation"));
    assertEquals(KARTING_ACCIDENT, deathnote.getDeathCause(JIM_MORIARTY));
  }

  /*
  5. After writing the cause of death, details of the death should be written
     in the next 6 seconds and 40 milliseconds of writing the death's cause.
      * check that writing the death details before writing a name throws the correct exception
      * write the name of a human in the notebook
      * verify that the details of the death are currently empty
      * set the details of the death to "ran for too long"
      * verify that death details have been set correctly 
        (returned true, and the details are indeed "ran for too long")
      * write the name of another human in the notebook
      * sleep for 6100ms
      * try to change the details
      * verify that the details have not been changed
  */
  @Test
  void testDetailsInTime() throws InterruptedException {
    final Exception e = assertThrows(IllegalStateException.class, () -> {
      deathnote.writeDetails("decapitation by a flying boomerang");
    }
    );
    assertNotNull(e.getMessage());
    assertFalse(e.getMessage().isEmpty());
    assertFalse(e.getMessage().isBlank());
    deathnote.writeName(MYCROFT_HOLMES);
    assertTrue(deathnote.getDeathDetails(MYCROFT_HOLMES).isEmpty());
    assertTrue(deathnote.writeDetails("ran for too long"));
    assertEquals("ran for too long", deathnote.getDeathDetails(MYCROFT_HOLMES));
    deathnote.writeName(SHERLOCK_HOLMES);
    Thread.sleep(INVALID_DETAILS_TIME);
    deathnote.writeDetails("decapitation by a flying boomerang");
    assertEquals("", deathnote.getDeathDetails(SHERLOCK_HOLMES));
  }
}
