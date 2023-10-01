package br.ufal.ic.p2.jackut;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import br.ufal.ic.p2.jackut.Exceptions.DontHaveMessagesException;
import br.ufal.ic.p2.jackut.Exceptions.AttributeNotFilledException;
import br.ufal.ic.p2.jackut.Exceptions.DontHaveErrandExcpetion;
import br.ufal.ic.p2.jackut.Exceptions.LoginSenhaInvalidsException;

/**
 * <p> Classe que representa um usuário. </p>
 */

public class User {
    private final String login;
    private final String password;
    private final String name;

    private final Profile profile = new Profile();

    private final ArrayList<User> friends = new ArrayList<>();
    private final ArrayList<User> solicitationsSent = new ArrayList<>();
    private final ArrayList<User> solicitationsReceived = new ArrayList<>();

    private final Queue<Errand> errands = new LinkedList<>();

    private final ArrayList<Community> comunidadesProprietarias = new ArrayList<>();
    private final ArrayList<Community> comunidadesParticipantes = new ArrayList<>();
    private final Queue<Messages> messages = new LinkedList<>();

    private final ArrayList<User> idols = new ArrayList<>();
    private final ArrayList<User> fas = new ArrayList<>();

    private final ArrayList<User> crushes = new ArrayList<>();
    private final ArrayList<User> crushesReceived = new ArrayList<>();

    private final ArrayList<User> enemy = new ArrayList<>();

    /**
     * <p> Constructs a new {@code User} in the Jackut system. </p>
     *
     * <p> Initializes a {@code Profile} for the user, along with lists for friends, sent friend requests, and received friend requests. </p>
     *
     * @param login User's login.
     * @param password User's password.
     * @param name User's name.
     *
     * @throws LoginSenhaInvalidsException Exception thrown if the login or password is invalid.
     *
     * @see Profile
     */

    public User(String login, String password, String name) throws LoginSenhaInvalidsException {
        if (login == null) {
            throw new LoginSenhaInvalidsException("login");
        }

        if (password == null) {
            throw new LoginSenhaInvalidsException("password");
        }

        this.login = login;
        this.password = password;
        this.name = name;
    }

    /**
     * <p> Gets the user's login. </p>
     *
     * @return User's login.
     */

    public String getLogin() {
        return this.login;
    }

    /**
     * Returns a string representation of the user.
     *
     * @return The user's login.
     */
    public String toString() {
        return this.getLogin();
    }

    /**
     * <p> Checks if the provided password matches the user's password. </p>
     *
     * @param password Password to be verified.
     * @return True if the passwords match, false otherwise.
     */

    public boolean verificarSenha(String password) {
        return this.password.equals(password);
    }

    /**
     * <p> Gets the user's password. </p>
     *
     * @return User's password.
     */

    public String getPassword() {
        return this.password;
    }

    /**
     * <p> Gets the user's name. </p>
     *
     * @return User's name.
     */

    public String getName() {
        return this.name;
    }

    /**
     * <p> Gets the user's profile. </p>
     *
     * @return User's profile.
     *
     * @see Profile
     */

    public Profile getProfile() {
        return this.profile;
    }

    /**
     * <p> Gets the value of a user's attribute. </p>
     *
     * <p> If the attribute is "name," it returns the user's name. </p>
     *
     * @param attribute Attribute to be returned.
     * @return Value of the attribute.
     *
     * @throws AttributeNotFilledException Exception thrown if the attribute has not been filled.
     */

    public String getAtributo(String attribute) throws AttributeNotFilledException {
        if (attribute.equals("nome")) {
            return this.getName();
        } else {
            return this.getProfile().getAtributo(attribute);
        }
    }

    /**
     * <p> Sends a friend request to the specified user. </p>
     *
     * @param user User to whom the friend request will be sent.
     */

    public void enviarSolicitacao(User user) {
        this.solicitationsSent.add(user);
        user.solicitationsReceived.add(this);
    }

    /**
     * <p> Accepts a friend request. </p>
     *
     * @param user User who sent the friend request.
     */
    public void aceitarSolicitacao(User user) {
        this.friends.add(user);
        this.solicitationsReceived.remove(user);
        user.friends.add(this);
        user.solicitationsSent.remove(this);
    }

    /**
     * <p> Returns the list of friends of the user. </p>
     *
     * @return List of user's friends.
     */

    public ArrayList<User> getFriends() {
        return this.friends;
    }

    /**
     * <p> Returns the list of friends of the user as a formatted string. </p>
     *
     * <p> The return format is: <b>{friend1, friend2, friend3, ...}</b> </p>
     *
     * @return Formatted string of user's friends.
     */

    public String getAmigosString() {
        return UtilsString.formatArrayList(this.friends);
    }

    /**
     * <p> Returns the list of friend requests sent by the user. </p>
     *
     * @return List of friend requests sent by the user.
     */

    public ArrayList<User> getSolicitationsSent() {
        return this.solicitationsSent;
    }

    /**
     * <p> Returns the list of friend requests received by the user. </p>
     *
     * @return List of friend requests received by the user.
     */

    public ArrayList<User> getSolicitationsReceived() {
        return this.solicitationsReceived;
    }

    /**
     * <p> Gets the oldest message from the user's queue of messages. </p>
     *
     * @return Oldest message from the user's queue of messages.
     *
     * @throws DontHaveErrandExcpetion Exception thrown if the user has no messages in the queue.
     */

    public Errand getRecado() throws DontHaveErrandExcpetion {
        if (this.errands.isEmpty()) {
            throw new DontHaveErrandExcpetion();
        }

        return this.errands.poll();
    }

    /**
     * <p> Receives a message from a community. </p>
     *
     * @param message Message to be received.
     */
    public void receberRecado(Errand message) {
        this.errands.add(message);
    }

    /**
     * <p> Adds a friend to the user. </p>
     * <p> Method used to load data from a file. Should not be used to add friends. </p>
     *
     * @param friend Friend to be added.
     */

    public void setAmigo(User friend) {
        if (!this.friends.contains(friend)) {
            this.friends.add(friend);
        }
    }

    /**
     * <p> Returns the list of unread messages for the user. </p>
     *
     * @return List of unread messages for the user.
     *
     * @see Errand
     */

    public Queue<Errand> getErrands() {
        return this.errands;
    }

    /**
     * <p> Sets the user as the creator of a community. </p>
     *
     * @param community Community to be set as a creator.
     */

    public void setCriadorComunidade(Community community) {
        this.comunidadesProprietarias.add(community);
    }

    /**
     * <p> Sets the user as a participant of a community. </p>
     *
     * @param comunidade Community to be set as a participant.
     */
    public void setParticipanteComunidade(Community comunidade) {
        this.comunidadesParticipantes.add(comunidade);
    }

    /**
     * <p> Returns the list of communities owned by the user. </p>
     *
     * @return List of communities owned by the user.
     *
     * @see Community
     */
    public ArrayList<Community> getComunidadesProprietarias() {
        return this.comunidadesProprietarias;
    }

    /**
     * <p> Returns the list of communities in which the user participates. </p>
     *
     * @return List of communities in which the user participates.
     *
     * @see Community
     */
    public ArrayList<Community> getComunidadesParticipantes() {
        return this.comunidadesParticipantes;
    }

    /**
     * <p> Receives a message from a community. </p>
     *
     * @param message Message to be received.
     */

    public void receberMensagem(Messages message) {
        this.messages.add(message);
    }

    /**
     * <p> Reads a message from the user's message queue. </p>
     *
     * @return Read message.
     *
     * @throws DontHaveMessagesException Exception thrown if the user has no messages in the queue.
     */

    public String lerMensagem() throws DontHaveMessagesException {
        if (this.messages.isEmpty()) {
            throw new DontHaveMessagesException();
        }

        return this.messages.poll().getMensagem();
    }

    /**
     * <p> Returns the list of messages received by the user. </p>
     *
     * @return List of messages received by the user.
     *
     * @see Messages
     */
    public Queue<Messages> getMessages() {
        return this.messages;
    }

    /**
     * <p> Adds a user as an idol. </p>
     *
     * @param user User to be added as an idol.
     */
    public void setIdolo(User user) {
        this.idols.add(user);
    }

    /**
     * <p> Adds a user as a fan. </p>
     *
     * @param user User to be added as a fan.
     */
    public void setFa(User user) {
        this.fas.add(user);
    }

    /**
     * <p> Returns the list of idols for the user. </p>
     *
     * @return List of idols for the user.
     */
    public ArrayList<User> getIdols() {
        return this.idols;
    }

    /**
     * <p> Returns the list of idols for the user. </p>
     *
     * @return List of idols for the user.
     */
    public ArrayList<User> getFas() {
        return this.fas;
    }

    /**
     * <p> Returns the string representation of the user's fans. </p>
     *
     * @return String representation of the user's fans.
     */
    public String getFasString() {
        return UtilsString.formatArrayList(this.fas);
    }

    /**
     * <p> Adds a user as a crush. </p>
     *
     * @param user User to be added as a crush.
     */
    public void setPaquera(User user) {
        this.crushes.add(user);
    }

    /**
     * <p> Adds a user as a received crush. </p>
     *
     * @param user User to be added as a received crush.
     */
    public void setCrushesReceived(User user) {
        this.crushesReceived.add(user);
    }

    /**
     * <p> Returns the list of crushes for the user. </p>
     *
     * @return List of crushes for the user.
     */
    public ArrayList<User> getCrushes() {
        return this.crushes;
    }

    /**
     * <p> Returns the list of crushes received by the user. </p>
     *
     * @return List of crushes received by the user.
     */
    public ArrayList<User> getCrushesReceived() {
        return this.crushesReceived;
    }

    /**
     * <p> Returns the string representation of the user's crushes. </p>
     *
     * @return String representation of the user's crushes.
     */

    public String getPaquerasString() {
        return UtilsString.formatArrayList(this.crushes);
    }

    /**
     * <p> Returns the string representation of the user's received crushes. </p>
     *
     * @return String representation of the user's received crushes.
     */
    public void setInimigo(User usuario) {
        this.enemy.add(usuario);
    }

    /**
     * <p> Returns the list of enemies for the user. </p>
     *
     * @return List of enemies for the user.
     */

    public ArrayList<User> getEnemy() {
        return this.enemy;
    }

    /**
     * <p> Removes a friend from the user's list of friends. </p>
     *
     * @param friend Friend to be removed.
     */

    public void removerAmigo(User friend) {
        this.friends.remove(friend);
    }

    /**
     * <p> Removes a sent friend request from the user's list of sent requests. </p>
     *
     * @param user User to whose request will be removed.
     */

    public void removerSolicitacaoEnviada(User user) {
        this.solicitationsSent.remove(user);
    }

    /**
     * <p> Removes a received friend request from the user's list of received requests. </p>
     *
     * @param user User from whose request will be removed.
     */
    public void removerSolicitacaoRecebida(User user) {
        this.solicitationsReceived.remove(user);
    }

    /**
     * <p> Removes a fan from the user's list of fans. </p>
     *
     * @param fan Fan to be removed.
     */
    public void removerFa(User fan) {
        this.fas.remove(fan);
    }

    /**
     * <p> Removes a crush from the user's list of crushes. </p>
     *
     * @param crush Crush to be removed.
     */

    public void removerPaquera(User crush) {
        this.crushes.remove(crush);
    }


    /**
     * <p> Removes a received crush from the user's list of received crushes. </p>
     *
     * @param receivedCrush Received crush to be removed.
     */

    public void removerPaqueraRecebida(User receivedCrush) {
        this.crushesReceived.remove(receivedCrush);
    }

    /**
     * <p> Removes an enemy from the user's list of enemies. </p>
     *
     * @param enemy Enemy to be removed.
     */

    public void removerInimigo(User enemy) {
        this.enemy.remove(enemy);
    }

    /**
     * <p> Removes an idol from the user's list of idols. </p>
     *
     * @param idol Idol to be removed.
     */

    public void removerIdolo(User idol) {
        this.idols.remove(idol);
    }

    /**
     * <p> Removes the user from a community they own. </p>
     *
     * @param community Community to be removed from.
     */

    public void removerComunidade(Community community) {
        this.comunidadesParticipantes.remove(community);
    }

    /**
     * <p> Removes an errand from the user's list of errands. </p>
     *
     * @param errand Errand to be removed.
     */

    public void removerRecado(Errand errand) {
        this.errands.remove(errand);
    }
}