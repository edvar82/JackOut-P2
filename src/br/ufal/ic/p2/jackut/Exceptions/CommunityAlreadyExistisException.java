package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exception for when a community already exists. </p>
**/

public class CommunityAlreadyExistisException extends RuntimeException {
    public CommunityAlreadyExistisException() {
        super("Comunidade com esse nome já existe.");
    }
}
