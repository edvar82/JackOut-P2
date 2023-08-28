package br.ufal.ic.p2.jackut.Exceptions;

public class UserSelfMessageException extends RuntimeException {
    public UserSelfMessageException() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
    
}
