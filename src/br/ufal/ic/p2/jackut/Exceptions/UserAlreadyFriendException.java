package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exceção que indica que o usuário já é amigo do outro usuário. </p>
 */

public class UserAlreadyFriendException extends RuntimeException {
    public UserAlreadyFriendException() {
        super("Usuário já está adicionado como amigo.");
    }
    
}
