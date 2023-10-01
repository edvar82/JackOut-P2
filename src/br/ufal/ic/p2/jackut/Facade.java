package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.*;

/**
 * <p> Facade class that implements the interface of the Jackut system. </p>
 */
public class Facade {
    private final System system = new System();

    /**
     * <p> Clears all data held in the system. </p>
     *
     * @see System
     */
    public void zerarSistema() {
        this.system.zerarSistema();
    }

    /**
     * <p> Creates a user with the provided account data. </p>
     *
     * @param login  User's login
     * @param password  User's password
     * @param name   User's name
     *
     * @throws LoginSenhaInvalidsException    Exception thrown if the login or password is invalid
     * @throws AccountAlreadyExistsException Exception thrown if the login is already registered
     *
     * @see User
     */
    public void criarUsuario(String login, String password, String name) throws LoginSenhaInvalidsException, AccountAlreadyExistsException {
        User usuario = new User(login, password, name);

        this.system.setUsuario(usuario);
    }

    /**
     * <p> Opens a session for a user with the provided login and password,
     * and returns an ID for this session. </p>
     *
     * @param login User's login
     * @param senha User's password
     * @return ID of the session
     *
     * @throws LoginSenhaInvalidsException   Exception thrown if the login or password is invalid
     * @throws UserIsNotRegisterException  Exception thrown if the user is not registered
     */
    public String abrirSessao(String login, String senha) throws LoginSenhaInvalidsException, UserIsNotRegisterException {
        return this.system.abrirSessao(login, senha);
    }

    /**
     * <p> Retrieves the value of a user's attribute stored in their profile. </p>
     *
     * @param login     User's login
     * @param attribute  Attribute to be returned
     * @return          Value of the attribute
     *
     * @throws UserIsNotRegisterException   Exception thrown if the user is not registered
     * @throws AttributeNotFilledException  Exception thrown if the attribute is not filled
     */
    public String getAtributoUsuario(String login, String attribute)
            throws UserIsNotRegisterException, AttributeNotFilledException {
        User usuario = this.system.getUsuario(login);

        return usuario.getAtributo(attribute);
    }

    /**
     * <p> Modifies the value of a user's profile attribute to the specified value. </p>
     * <p> A valid session <b>(identified by id)</b> must be open for the user
     * whose profile you want to edit. </p>
     *
     * @param id        Session ID
     * @param attribute  Attribute to be modified
     * @param valor     New value of the attribute
     *
     * @throws UserIsNotRegisterException Exception thrown if the user is not registered
     */
    public void editarPerfil(String id, String attribute, String valor)
            throws UserIsNotRegisterException {
        User usuario = this.system.getSessaoUsuario(id);

        usuario.getProfile().setAtributo(attribute, valor);
    }

    /**
     * <p> Adds a friend to the user currently logged in through the specified session id. </p>
     *
     * @param id     Session ID
     * @param amigo  Friend's login to be added
     *
     * @throws UserAlreadyHaveRelationException       Exception thrown if the user is already friends with the logged-in user
     * @throws UserIsNotRegisterException       Exception thrown if the user or friend is not registered
     * @throws UserAutoRelationException         Exception thrown if the user tries to add themselves as a friend
     * @throws UserAlreadySentConviteException  Exception thrown if the user has already requested friendship from the friend
     * @throws UserIsEnemyException             Exception thrown if the user is an enemy of the friend
     */
    public void adicionarAmigo(String id, String amigo) throws UserAlreadyHaveRelationException, UserIsNotRegisterException,
            UserAutoRelationException, UserAlreadySentConviteException, UserIsEnemyException {
        User usuario = this.system.getSessaoUsuario(id);
        User amigoUsuario = this.system.getUsuario(amigo);

        this.system.adicionarAmigo(usuario, amigoUsuario);
    }

    /**
     * <p> Returns true if the two users are friends. </p>
     *
     * @param login User's login
     * @param friend Friend's login
     * @return Boolean indicating if the users are friends
     *
     * @throws UserIsNotRegisterException Exception thrown if one of the users is not registered
     */
    public boolean ehAmigo(String login, String friend) throws UserIsNotRegisterException {
        User usuario = this.system.getUsuario(login);
        User amigoUsuario = this.system.getUsuario(friend);

        return usuario.getFriends().contains(amigoUsuario);
    }

    /**
     * <p> Returns the list of friends of the specified user. </p>
     * <p> The return is formatted as a String in the format: <b>{friend1,friend2,friend3,...}</b> </p>
     *
     * @param login User's login
     * @return List of friends of the user formatted as a String
     *
     * @throws UserIsNotRegisterException Exception thrown if the user is not registered
     */
    public String getAmigos(String login) throws UserIsNotRegisterException {
        User usuario = this.system.getUsuario(login);

        return usuario.getAmigosString();
    }

    /**
     * <p> Sends the specified message to the specified recipient. </p>
     * <p> A valid session <b>(identified by id)</b> must be open
     * for the user who wants to send the message. </p>
     *
     * @param id            Session ID
     * @param destinatario  Recipient's login
     * @param recado        Message to be sent
     *
     * @throws UserIsNotRegisterException    Exception thrown if the user or recipient is not registered
     * @throws SelfSentErrandException  Exception thrown if the user tries to send a message to themselves
     * @throws UserIsEnemyException    Exception thrown if the user is an enemy of the recipient
     */
    public void enviarRecado(String id, String destinatario, String recado) throws UserIsNotRegisterException, SelfSentErrandException, UserIsEnemyException {
        User usuario = this.system.getSessaoUsuario(id);
        User destinatarioUsuario = this.system.getUsuario(destinatario);

        this.system.enviarRecado(usuario, destinatarioUsuario, recado);
    }

    /**
     * <p> Returns the first message in the user's message queue with the open session identified by id. </p>
     *
     * @param id  Session ID
     * @return    First message in the user's message queue
     *
     * @throws UserIsNotRegisterException  Exception thrown if the user is not registered
     * @throws DontHaveErrandExcpetion          Exception thrown if the user has no messages in the queue
     */
    public String lerRecado(String id) throws UserIsNotRegisterException, DontHaveErrandExcpetion {
        User usuario = this.system.getSessaoUsuario(id);

        Errand recado = usuario.getRecado();
        return recado.getRecado();
    }

    /**
     * <p> Creates a community with the provided data. </p>
     *
     * @param id         Session ID
     * @param nome       Community name
     * @param descricao  Community description
     *
     * @throws UserIsNotRegisterException  Exception thrown if the user is not registered
     * @throws CommunityAlreadyExistisException    Exception thrown if the community already exists
     */
    public void criarComunidade(String id, String nome, String descricao)
            throws UserIsNotRegisterException, CommunityAlreadyExistisException {
        User usuario = this.system.getSessaoUsuario(id);

        system.criarComunidade(usuario, nome, descricao);
    }

    /**
     * Retrieves the description of a community by its name.
     *
     * @param nome The name of the community.
     * @return The description of the community.
     * @throws CommunityNotExistsException If the community does not exist.
     */
    public String getDescricaoComunidade(String nome) throws CommunityNotExistsException {
        return this.system.getDescricaoComunidade(nome);
    }

    /**
     * Retrieves the owner of a community by its name.
     *
     * @param nome The name of the community.
     * @return The owner of the community.
     * @throws CommunityNotExistsException If the community does not exist.
     */
    public String getDonoComunidade(String nome) throws CommunityNotExistsException {
        return this.system.getDonoComunidade(nome);
    }

    /**
     * Retrieves the members of a community by its name.
     *
     * @param nome The name of the community.
     * @return The members of the community.
     * @throws CommunityNotExistsException If the community does not exist.
     */
    public String getMembrosComunidade(String nome) throws CommunityNotExistsException {
        return this.system.getMembrosComunidade(nome);
    }

    /**
     * Retrieves the communities associated with a user by their login.
     *
     * @param login The login of the user.
     * @return A string containing the communities associated with the user.
     * @throws UserIsNotRegisterException If the user is not registered.
     */
    public String getComunidades(String login) throws UserIsNotRegisterException {
        User usuario = this.system.getUsuario(login);

        return this.system.getComunidades(usuario);
    }

    /**
     * Adds a user to a community.
     *
     * @param id   The ID of the user session.
     * @param nome The name of the community.
     * @throws UserIsNotRegisterException      If the user is not registered.
     * @throws CommunityNotExistsException    If the community does not exist.
     * @throws UserAlreadyInACommunityException If the user is already a member of another community.
     */
    public void adicionarComunidade(String id, String nome)
            throws UserIsNotRegisterException, CommunityNotExistsException, UserAlreadyInACommunityException {
        User usuario = this.system.getSessaoUsuario(id);

        this.system.adicionarComunidade(usuario, nome);
    }

    /**
     * Reads a message for a user.
     *
     * @param id The ID of the user session.
     * @return The message read by the user.
     * @throws UserIsNotRegisterException   If the user is not registered.
     * @throws DontHaveMessagesException   If the user has no messages.
     */
    public String lerMensagem(String id) throws UserIsNotRegisterException, DontHaveMessagesException {
        User usuario = this.system.getSessaoUsuario(id);

        return this.system.lerMensagem(usuario);
    }

    /**
     * Sends a message to a community.
     *
     * @param id         The ID of the user session.
     * @param comunidade The name of the community.
     * @param mensagem   The message to be sent.
     * @throws UserIsNotRegisterException   If the user is not registered.
     * @throws CommunityNotExistsException If the community does not exist.
     */
    public void enviarMensagem(String id, String comunidade, String mensagem)
            throws UserIsNotRegisterException, CommunityNotExistsException {
        this.system.getSessaoUsuario(id);
        Community comunidadeAlvo = this.system.getComunidade(comunidade);

        this.system.enviarMensagem(comunidadeAlvo, mensagem);
    }

    /**
     * Checks if a user is a fan of another user.
     *
     * @param login The login of the user.
     * @param idolo The login of the idol.
     * @return True if the user is a fan of the idol, false otherwise.
     */
    public boolean ehFa(String login, String idolo) {
        User usuario = this.system.getUsuario(login);
        User idoloUsuario = this.system.getUsuario(idolo);

        return idoloUsuario.getFas().contains(usuario);
    }

    /**
     * Adds a user as an idol (fan) of another user.
     *
     * @param id    The ID of the user session.
     * @param Idolo The login of the idol.
     * @throws UserIsNotRegisterException       If the user is not registered.
     * @throws UserAlreadyHaveRelationException If the user already has a relation with the idol.
     * @throws UserAutoRelationException         If the user is trying to establish a relation with themselves.
     * @throws UserIsEnemyException             If the user is an enemy of the idol.
     */
    public void adicionarIdolo(String id, String Idolo)
            throws UserIsNotRegisterException, UserAlreadyHaveRelationException, UserAutoRelationException, UserIsEnemyException {
        User usuario = this.system.getSessaoUsuario(id);
        User idoloUsuario = this.system.getUsuario(Idolo);

        this.system.adicionarIdolo(usuario, idoloUsuario);
    }

    /**
     * Retrieves the fans (idols) of a user by their login.
     *
     * @param login The login of the user.
     * @return A string containing the fans of the user.
     * @throws UserIsNotRegisterException If the user is not registered.
     */
    public String getFas(String login) throws UserIsNotRegisterException {
        User usuario = this.system.getUsuario(login);

        return this.system.getFas(usuario);
    }

    /**
     * Checks if a user is in a romantic relationship (paquera) with another user.
     *
     * @param id       The ID of the user session.
     * @param paquera The login of the person they are in a romantic relationship with.
     * @return True if the user is in a romantic relationship with the other person, false otherwise.
     * @throws UserIsNotRegisterException If the user is not registered.
     */
    public boolean ehPaquera(String id, String paquera) throws UserIsNotRegisterException {
        User usuario = this.system.getSessaoUsuario(id);
        User paqueraUsuario = this.system.getUsuario(paquera);

        return usuario.getCrushes().contains(paqueraUsuario);
    }

    /**
     * Adds a user as a romantic partner (paquera) of another user.
     *
     * @param id      The ID of the user session.
     * @param paquera The login of the romantic partner.
     * @throws UserIsNotRegisterException       If the user is not registered.
     * @throws UserAlreadyHaveRelationException If the user already has a relation with the romantic partner.
     * @throws UserAutoRelationException         If the user is trying to establish a relation with themselves.
     * @throws UserIsEnemyException             If the user is an enemy of the romantic partner.
     */
    public void adicionarPaquera(String id, String paquera)
            throws UserIsNotRegisterException, UserAlreadyHaveRelationException, UserAutoRelationException, UserIsEnemyException {
        User usuario = this.system.getSessaoUsuario(id);
        User paqueraUsuario = this.system.getUsuario(paquera);

        this.system.adicionarPaquera(usuario, paqueraUsuario);
    }

    /**
     * Retrieves the romantic partners (paqueras) of a user by their login.
     *
     * @param id The ID of the user session.
     * @return A string containing the romantic partners of the user.
     * @throws UserIsNotRegisterException If the user is not registered.
     */
    public String getPaqueras(String id) throws UserIsNotRegisterException {
        User usuario = this.system.getSessaoUsuario(id);

        return this.system.getPaqueras(usuario);
    }

    /**
     * Adds a user as an enemy of another user.
     *
     * @param id     The ID of the user session.
     * @param inimigo The login of the enemy.
     * @throws UserIsNotRegisterException       If the user is not registered.
     * @throws UserAlreadyHaveRelationException If the user already has a relation with the enemy.
     * @throws UserAutoRelationException         If the user is trying to establish a relation with themselves.
     */
    public void adicionarInimigo(String id, String inimigo)
            throws UserIsNotRegisterException, UserAlreadyHaveRelationException, UserAutoRelationException {
        User usuario = this.system.getSessaoUsuario(id);
        User inimigoUsuario = this.system.getUsuario(inimigo);

        this.system.adicionarInimigo(usuario, inimigoUsuario);
    }

    /**
     * Removes a user from the system.
     *
     * @param id The ID of the user session.
     * @throws UserIsNotRegisterException If the user is not registered.
     */
    public void removerUsuario(String id) throws UserIsNotRegisterException {
        User usuario = this.system.getSessaoUsuario(id);

        this.system.removerUsuario(usuario, id);
    }


    /**
     * <p> Writes the user registrations to a file and terminates the program.</p>
     * <p> Reaching the end of a script (end of file) is equivalent to encountering this command. </p>
     * <p> In this case, the command has no parameters, and will save only the registered users at this moment. </p>
     *
     * @throws AttributeNotFilledException Exception thrown if any attribute is not filled
     */

    public void encerrarSistema() throws AttributeNotFilledException {
        this.system.encerrarSistema();
    }
}