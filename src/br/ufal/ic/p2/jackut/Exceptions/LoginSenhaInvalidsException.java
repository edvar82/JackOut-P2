package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exception indicating that the provided login or password is invalid. </p>
*/

public class LoginSenhaInvalidsException extends RuntimeException {

    /**
     * <p> Returns an exception for invalid login or password, depending on the provided parameter. </p>
     * <p> If the parameter is <b>"login"</b>, the returned exception will be for invalid login. </p>
     * <p> If the parameter is <b>"senha"</b>, the returned exception will be for invalid password. </p>
     * <p> If the parameter is any other value, the returned exception will be for invalid login or password. </p>
     *
     * @param type Type of exception to be thrown.
     */


    public LoginSenhaInvalidsException(String type) {
        super(type.equals("login") ? "Login inválido." : type.equals("senha") ? "Senha inválida." : "Login ou senha inválidos.");
    }
    
}
