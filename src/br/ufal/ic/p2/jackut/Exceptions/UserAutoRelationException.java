package br.ufal.ic.p2.jackut.Exceptions;

public class UserAutoRelationException extends RuntimeException {
    /**
     * <p> Exception indicating self-adding a relationship depending on the type of relationship. </p>
     * <p> The <code>relacao</code> parameter must be one of the following relationships:
     * <ul>
     * <li> Friendship </li>
     * <li> Fan </li>
     * <li> Crush </li>
     * <li> Enemy </li>
     * </ul>
     * </p>
     *
     * @param relacao The relationship being added.
     */

    public UserAutoRelationException(String relacao) {
        super(
                relacao.toLowerCase().equals("amizade")
                        ? 
                "Usuário não pode adicionar a si mesmo como amigo."
                        : 
                "Usuário não pode ser " + relacao.toLowerCase() + " de si mesmo."
        );
    }
}
