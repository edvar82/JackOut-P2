package br.ufal.ic.p2.jackut;

/**
 * <p> Class representing a message. </p>
 */
public class Errand {
    private final User sender;
    private final User recipient;
    private final String message;

    /**
     * <p> Constructs a new {@code Errand} sent from one {@code User} to another. </p>
     *
     * @param sender     Sender of the message.
     * @param recipient  Recipient of the message.
     * @param message    Message content.
     *
     * @see User
     */
    public Errand(User sender, User recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    /**
     * <p> Returns the sender of the message. </p>
     *
     * @return Sender of the message.
     *
     * @see User
     */
    public User getRemetente() {
        return this.sender;
    }

    /**
     * <p> Returns the recipient of the message. </p>
     *
     * @return Recipient of the message.
     *
     * @see User
     */
    public User getDestinatario() {
        return this.recipient;
    }

    /**
     * <p> Returns the message content. </p>
     *
     * @return Message content.
     */
    public String getRecado() {
        return this.message;
    }
}

