package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exceção que indica que o usuário não está cadastrado no sistema. </p>
 */

public class UserNotRegisteredException extends RuntimeException {
    public UserNotRegisteredException() {
        super("Usuário não cadastrado.");
    }
}
