package it.unibo.deathnote.api;

public class DeathNoteImpl implements DeathNote{



    public String getRule(int ruleNumber) {
        throw new IllegalArgumentException("given rule number is smaller than 1 or larger than the number of rules");
    }

    public void writeName(String name) {
        throw new NullPointerException("given name is null");
    }

    public boolean writeDeathCause(String cause) {
        throw new IllegalStateException("there is no name written in this Death or the cause is null");
    }

    public boolean writeDetails(String details) {
        throw new IllegalStateException("there is no name written in this DeathNote or the details are null");
    }

    public String getDeathCause(String name) {
        throw new IllegalArgumentException("the provider name is not written in this DeathNote");
    }

    public String getDeathDetails(String name) {
        throw new IllegalArgumentException("the provider name is not written in this DeathNote");
    }

    public boolean isNameWritten(String name) {
        throw new NullPointerException("given name is null");
        //Not sure about this one
        //throw new UnsupportedOperationException("Not implemented yet"); ??
        
    }

}
