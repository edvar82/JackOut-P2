package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exceção que indica que não há recados para serem lidos. </p>
 */

public class NoMessagesException extends RuntimeException {
    public NoMessagesException() {
        super("Não há recados.");
    }
}
