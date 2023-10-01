package br.ufal.ic.p2.jackut.Exceptions;

public class UserIsEnemyException extends Exception {

    /**
     * <p> Exception for when the user is an enemy of the logged-in user. </p>
     *
     * @param usuario User who is an enemy of the logged-in user.
     */


    public UserIsEnemyException(String usuario) {
        super("Função inválida: " + usuario + " é seu inimigo.");
    }
}
