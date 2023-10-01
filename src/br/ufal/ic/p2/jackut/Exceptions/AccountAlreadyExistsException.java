package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exception that indicates that the account already exists. </p>
 */


public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException() {
        super("Conta com esse nome já existe.");
    }
}
