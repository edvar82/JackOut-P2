package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exception indicating that the user cannot send a message to themselves. </p>
    */

public class SelfSentErrandException extends RuntimeException {
    public SelfSentErrandException() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
    
}
