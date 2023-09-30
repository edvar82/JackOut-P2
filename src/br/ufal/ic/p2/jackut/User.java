package br.ufal.ic.p2.jackut;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <p> Classe que representa um usuário. </p>
 */

public class User {
    private final ArrayList<User> friends;
    private final String login;
    private final String password;
    private final Queue<Messages> messages;
    private final String name;
    private final Profile profile;
    
    private final ArrayList<User> receivedSolicitations;
    private final ArrayList<User> sendSolicitations;

    private final ArrayList<Comunidade> comunidadesProprietarias = new ArrayList<>();
    private final ArrayList<Comunidade> comunidadesParticipantes = new ArrayList<>();
    /**
     * <p> Constrói um novo {@code Usuario} do Jackut. </p>
     * <p> Inicializa um {@code Perfil} para o usuário, as listas de amigos, solicitações enviadas e solicitações recebidas. </p>
     *
     * @param login Login do usuário.
     * @param password Senha do usuário.
     * @param name Nome do usuário.
     *
     * @see Profile
     */

    public User(String login, String password, String name) {
        this.friends = new ArrayList<>();
        this.login = login;
        this.password = password;
        this.messages = new LinkedList<>();
        this.name = name;
        this.profile = new Profile();
        this.receivedSolicitations = new ArrayList<>();
        this.sendSolicitations = new ArrayList<>();
    }

    /**
     * <p> Retorna o login do usuário. </p>
     *
     * @return Login do usuário.
     */

    public String getLogin() {
        return this.login;
    }

    /**
     * <p> Verifica se a senha passada como parâmetro é igual à senha do usuário. </p>
     *
     * @param senha  Senha a ser verificada.
     * @return       True se a senha for igual, false caso contrário.
     */

    public boolean verificarSenha(String senha) {
        return this.password.equals(senha);
    }

    /**
     * <p> Retorna a senha do usuário. </p>
     *
     * @return Senha do usuário.
     */

    public String getSenha() {
        return this.password;
    }

    /**
     * <p> Retorna o nome do usuário. </p>
     *
     * @return Nome do usuário.
     */

    public String getNome() {
        return this.name;
    }

    /**
     * <p> Retorna o perfil do usuário. </p>
     *
     * @return Perfil do usuário.
     *
     * @see Profile
     */

    public Profile getPerfil() {
        return this.profile;
    }

    /**
     * <p> Envia uma solicitação de amizade para o usuário passado como parâmetro. </p>
     *
     * @param usuario Usuário para o qual a solicitação será enviada.
     */

    public void enviarSolicitacao(User usuario) {
        this.sendSolicitations.add(usuario);
        usuario.receivedSolicitations.add(this);
    }

    /**
     * <p> Aceita uma solicitação de amizade. </p>
     *
     * @param user Usuário que enviou a solicitação.
     */

    public void aceitarSolicitacao(User user) {
        this.friends.add(user);
        this.receivedSolicitations.remove(user);
        user.friends.add(this);
        user.sendSolicitations.remove(this);
    }

    /**
     * <p> Retornar a lista de amigos do usuário. </p>
     *
     * @return Lista de amigos do usuário.
     */

    public ArrayList<User> getAmigos() {
        return this.friends;
    }

    /**
     * <p> Retorna a lista de solicitações de amizade enviadas pelo usuário. </p>
     *
     * @return Lista de solicitações de amizade enviadas pelo usuário
     */

    public ArrayList<User> getSolicitacoesEnviadas() {
        return this.sendSolicitations;
    }

    /**
     * <p> Retorna a lista de solicitações de amizade recebidas pelo usuário. </p>
     *
     * @return Lista de solicitações de amizade recebidas pelo usuário.
     */

    public ArrayList<User> getSolicitacoesRecebidas() {
        return this.receivedSolicitations;
    }

    /**
     * <p> Envia um recado para o usuário passado como parâmetro. </p>
     *
     * @param sender  Usuário para o qual o recado será enviado.
     * @param message        Recado a ser enviado.
     */

    public void enviarRecado(User sender, String message) {
        Messages r = new Messages(this, sender, message);
        sender.messages.add(r);
    }

    /**
     * <p> Retorna o recado mais antigo do usuário. </p>
     *
     * @return Recado mais antigo do usuário.
     */

    public Messages getRecado() {
        return this.messages.poll();
    }

    /**
     * <p> Adiciona um amigo ao usuário. </p>
     * <p> Método utilizado para carregar os dados do arquivo. Não deve ser utilizado para adicionar amigos. </p>
     *
     * @param friend Amigo a ser adicionado.
     */

    public void setAmigo(User friend) {
        if (!this.friends.contains(friend)) {
            this.friends.add(friend);
        }
    }

    /**
     * <p> Retorna a lista de recados não lidos do usuário. </p>
     *
     * @return Lista de recados não lidos do usuário.
     *
     * @see Messages
     */

    public Queue<Messages> getRecados() {
        return this.messages;
    }
    
    public void setCriadorComunidade(Comunidade comunidade) {
        this.comunidadesProprietarias.add(comunidade);
    }
    
    public void setParticipanteComunidade(Comunidade comunidade) {
        this.comunidadesParticipantes.add(comunidade);
    }

}