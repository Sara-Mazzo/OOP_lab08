package it.unibo.deathnote.api;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of the DeathNote interface.
 */
public final class DeathNoteImpl implements DeathNote {

    private static final long VALID_CAUSE_TIME = 40;
    private static final long VALID_DETAILS_TIME = 6000 + VALID_CAUSE_TIME;
    private final Map<String, Death> deathnote;
    private String lastName;
    private long lastNameTime;
    private Death lastDeath;

    /**
     * a new empty deathnote.
     */
    public DeathNoteImpl() {
        deathnote = new LinkedHashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber <= 0 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("given rule number is smaller than 1 or larger than the number of rules");
        }
        return RULES.get(ruleNumber - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeName(final String name) {
        Objects.requireNonNull(name, "name cannot be null");
        lastDeath = new Death();
        deathnote.put(name, lastDeath);
        lastName = name;
        lastNameTime = System.currentTimeMillis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDeathCause(final String cause) {
        if (cause == null) {
            throw new IllegalStateException("the cause is null");
        }
        if (lastName == null) {
            throw new IllegalStateException("there is no name written in this DeathNote");
        }
        if (System.currentTimeMillis() - lastNameTime > VALID_CAUSE_TIME) { //NOPMD
            return false;
        }
        return deathnote.replace(lastName, lastDeath, new Death(cause, ""));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDetails(final String details) {
        if (details == null) {
            throw new IllegalStateException("the details are null");
        }
        if (lastName == null) {
            throw new IllegalStateException("there is no name written in this DeathNote");
        }
        if (System.currentTimeMillis() - lastNameTime > VALID_DETAILS_TIME) { //NOPMD
            return false;
        }
        return deathnote.replace(lastName, lastDeath, new Death("", details)); 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathCause(final String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException("the provider name is not written in this DeathNote");
        }
        return deathnote.get(name).cause;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathDetails(final String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException("the provider name is not written in this DeathNote");
        }
        return deathnote.get(name).details;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNameWritten(final String name) {
        Objects.requireNonNull(name, "name cannot be null");
        return deathnote.containsKey(name);
    }

    private static final class Death {

        private static final String DEFAULT_CAUSE = "heart attack";
        private String cause;
        private String details;

        private Death(final String cause, final String details) {
            this.cause = cause;
            this.details = details;
        }

        private Death() {
            this(DEFAULT_CAUSE, "");
        }
    }

}
