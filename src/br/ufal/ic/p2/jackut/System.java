package br.ufal.ic.p2.jackut;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p> Classe que representa o sistema. </p>
 */

public class System {
    private Map<String, User> sessions;
    private Map<String, User> users;


    /**
     * <p> Constrói um novo {@code Sistema} responsável por
     * gerenciar os usuários e as sessões. </p>
     *
     * <p> Inicializa as listas de usuários e sessões. </p>
     *
     * @see User
     */

    public System() {
        this.sessions = new HashMap<>();
        this.users = new HashMap<>();
    }

    /**
     * <p> Adiciona um usuário ao sistema. </p>
     *
     * @param user Usuário a ser adicionado.
     *
     * @see User
     */

    public void setUsuario(User user) {
        this.users.put(user.getLogin(), user);
    }

    /**
     * <p> Cria uma sessão para um usuário logado. </p>
     *
     * @param user Usuário a ser adicionado.
     *
     * @see User
     */

    public void setSessaoUsuario(User user) {
        String id = UUID.randomUUID().toString();
        this.sessions.put(id, user);
    }

    /**
     * <p> Retorna um usuário do sistema pelo login. </p>
     *
     * @param login Login do usuário.
     *
     * @see User
     */

    public User getUsuario(String login) {
        return this.users.get(login);
    }

    /**
     * <p> Retorna um usuário logado do sistema pelo id da sua sessão. </p>
     *
     * @param id ID da sessão do usuário.
     *
     * @see User
     */

    public User getSessaoUsuario(String id) {
        return this.sessions.get(id);
    }

    /**
     * <p> Retorna todos os usuários do sistema. </p>
     *
     * @return Map com todos os usuários do sistema.
     *
     * @see User
     */

    public Map<String, User> getUsuarios() {
        return this.users;
    }

    /**
     * <p> Retorna o ID da sessão de um usuário. </p>
     *
     * @param usuario  Usuário a ser buscado.
     * @return         ID da sessão do usuário.
     */

    public String getSessao(User usuario) {
        for (Map.Entry<String, User> entry : this.sessions.entrySet()) {
            if (entry.getValue().equals(usuario)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * <p> Zera as informações do sistema. </p>
     */

    public void zerarSistema() {
        this.users = new HashMap<>();
        this.sessions = new HashMap<>();
    }
}
