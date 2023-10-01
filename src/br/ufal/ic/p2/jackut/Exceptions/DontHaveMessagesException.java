package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exception to be thrown when the user doesn't have any messages. </p>
 */

public class DontHaveMessagesException extends Exception {
    public DontHaveMessagesException() {
        super("Não há mensagens.");
    }
}
