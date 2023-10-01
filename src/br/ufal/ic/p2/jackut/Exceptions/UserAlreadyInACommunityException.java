package br.ufal.ic.p2.jackut.Exceptions;


/**
 * <p> Exception for when a user is already in a community. </p>
 */

public class UserAlreadyInACommunityException extends RuntimeException {
    public UserAlreadyInACommunityException() {
        super("Usuario já faz parte dessa comunidade.");
    }
    
}
