package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exceção que indica que um atributo não foi preenchido. </p>
 */

public class UnfilledAttributeException extends Exception {
    public UnfilledAttributeException() {
        super("Atributo não preenchido.");
    }
    
}
