package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exception for when an attribute is not filled. </p>
 */


public class AttributeNotFilledException extends RuntimeException {
    public AttributeNotFilledException() {
        super("Atributo não preenchido.");
    }
}
