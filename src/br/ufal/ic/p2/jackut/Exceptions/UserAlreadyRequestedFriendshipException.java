package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exceção que indica que o usuário já solicitou amizade para o outro usuário. </p>
 */

public class UserAlreadyRequestedFriendshipException extends RuntimeException {
    public UserAlreadyRequestedFriendshipException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
    
}
