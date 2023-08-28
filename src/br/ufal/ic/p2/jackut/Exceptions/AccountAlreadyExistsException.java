package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exceção que indica que a conta já existe. </p>
 */

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException() {
        super("Conta com esse nome já existe.");
    }
}
