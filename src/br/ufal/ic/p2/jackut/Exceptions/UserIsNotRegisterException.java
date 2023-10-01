package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exception indicating that the user is not registered in the system. </p>

 */

public class UserIsNotRegisterException extends RuntimeException {
    public UserIsNotRegisterException() {
        super("Usuário não cadastrado.");
    }
}
