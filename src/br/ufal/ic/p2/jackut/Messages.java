package br.ufal.ic.p2.jackut;

public class Messages {
    private final String message;

    /**
     * <p> Constructs a new {@code Messages} object with the specified message. </p>
     *
     * @param message The message to be stored in the Messages object.
     */
    public Messages(String message) {
        this.message = message;
    }

    /**
     * <p> Returns the stored message. </p>
     *
     * @return The message stored in the Messages object.
     */
    public String getMensagem() {
        return this.message;
    }
}
