package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exceção que indica que o usuário não pode adicionar a si mesmo como amigo. </p>
 */

public class UserSelfFriendshipException extends RuntimeException {
    public UserSelfFriendshipException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
    
}
