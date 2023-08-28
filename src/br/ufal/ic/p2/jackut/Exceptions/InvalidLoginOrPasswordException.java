package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exceção que indica que o login ou senha informados são inválidos. </p>
 */

public class InvalidLoginOrPasswordException extends RuntimeException {

    /**
     * <p> Retorna uma exceção de login ou senha inválidos, dependendo do parâmetro informado. </p>
     * <p> Caso o parâmetro seja <b>"login"</b>, a exceção retornada será de login inválido. </p>
     * <p> Caso o parâmetro seja <b>"senha"</b>, a exceção retornada será de senha inválida. </p>
     * <p> Caso o parâmetro seja qualquer outro valor, a exceção retornada será de login ou senha inválidos. </p>
     *
     * @param type Tipo de exceção a ser lançada.
     */

    public InvalidLoginOrPasswordException(String type) {
        super(type.equals("login") ? "Login inválido." : type.equals("senha") ? "Senha inválida." : "Login ou senha inválidos.");
    }
    
}
