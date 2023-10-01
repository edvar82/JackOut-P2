package br.ufal.ic.p2.jackut.Exceptions;

/**
 * Exception for when a community does not exist.
 */

public class CommunityNotExistsException extends RuntimeException {
    public CommunityNotExistsException() {
        super("Comunidade não existe.");
    }
}
