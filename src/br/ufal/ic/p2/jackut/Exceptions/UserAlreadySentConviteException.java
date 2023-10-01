package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exception indicating that the user has already sent a friend request to the other user. </p>

 */

public class UserAlreadySentConviteException extends RuntimeException {
    public UserAlreadySentConviteException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
    
}
