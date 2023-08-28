package br.ufal.ic.p2.jackut;

import java.util.HashMap;
import java.util.Map;
import br.ufal.ic.p2.jackut.Exceptions.UnfilledAttributeException;

/**
 * <p> Classe que representa um perfil de usuário. </p>
 */
public class Profile {
    private final Map<String, String> attributes = new HashMap<>();

    /**
     * <p> Adiciona um atributo ao perfil. </p>
     *
     * @param chave Nome do atributo.
     * @param valor Valor do atributo.
     */

    public void setAtributo(String chave, String valor) {
        this.attributes.put(chave, valor);
    }

    /**
     * <p> Retorna o valor de um atributo do perfil. </p>
     *
     * @param chave  Chave do atributo.
     * @return       Valor do atributo.
     *
     * @throws UnfilledAttributeException Exceção que indica que o atributo não foi preenchido.
     */

    public String getAtributo(String chave) throws UnfilledAttributeException {
        if (this.attributes.containsKey(chave)) {
            return this.attributes.get(chave);
        } else {
            throw new UnfilledAttributeException();
        }
    }

    /**
     * <p> Retorna todos os atributos do perfil. </p>
     *
     * @return Map com todos os atributos do perfil.
     */

    public Map<String, String> getAtributos() {
        return this.attributes;
    }
}
