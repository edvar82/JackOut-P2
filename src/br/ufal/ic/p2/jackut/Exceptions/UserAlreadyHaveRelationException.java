package br.ufal.ic.p2.jackut.Exceptions;

public class UserAlreadyHaveRelationException extends Exception {
/**
 * <p> Exception thrown when the user already has a relationship with another user. </p>
 * <p> The <code>relacao</code> parameter must be one of the following relationships:
 * <ul>
 * <li> Friend </li>
 * <li> Idol </li>
 * <li> Crush </li>
 * <li> Enemy </li>
 * </ul>
 * </p>
 *
 * @param relacao The relationship being added.
 */
    public UserAlreadyHaveRelationException(String relacao) {
        super("Usuário já está adicionado como " + relacao.toLowerCase() + ".");
    }
}
