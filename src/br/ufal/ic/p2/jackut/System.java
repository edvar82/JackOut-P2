package br.ufal.ic.p2.jackut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.ufal.ic.p2.jackut.Exceptions.*;
import br.ufal.ic.p2.jackut.Exceptions.SelfSentErrandException;

/**
 * <p> Class that represents the system. </p>
 */

public class System {
    private Map<String, User> users = new HashMap<>();
    private Map<String, User> sessions = new HashMap<>();
    private Map<String, Community> communities = new HashMap<>();

    /**
     * <p> Constructs a new {@code System} responsible for managing users and sessions. </p>
     *
     * <p> Initializes the lists of users and sessions. </p>
     *
     * @see User
     */

    public System() {
        try {
            UtilsFileHandler.criarPasta();

            Map<String, String[]> communities = new HashMap<>();

            File arquivo = new File("./database/usuarios.txt");
            if (arquivo.exists()) {
                String[] dados;
                String linha;

                BufferedReader br = new BufferedReader(new FileReader(arquivo));

                while ((linha = br.readLine()) != null) {
                    dados = linha.split(";");

                    String login = dados[0];
                    String senha = dados[1];
                    String nome = "";
                    if (dados.length > 2) {
                        nome = dados[2];
                    }

                    User usuario = new User(login, senha, nome);
                    for (int i = 3; i < dados.length - 1; i++) {
                        String[] atributo = dados[i].split(":");
                        usuario.getProfile().setAtributo(atributo[0], atributo[1]);
                    }

                    String[] comunidadesUsuario = dados[dados.length - 1]
                            .substring(1, dados[dados.length - 1].length() - 1)
                            .split(",");

                    communities.put(login, comunidadesUsuario);

                    this.setUsuario(usuario);
                }
                br.close();
            }

            File arquivo2 = new File("./database/amigos.txt");
            if (arquivo2.exists()) {
                String[] dados;
                String linha;

                BufferedReader br = new BufferedReader(new FileReader(arquivo2));

                while ((linha = br.readLine()) != null) {
                    dados = linha.split(";");
                    User usuario = this.getUsuario(dados[0]);

                    if (dados[1].length() <= 2) {
                        continue;
                    }

                    String[] amigos = dados[1].substring(1, dados[1].length() - 1).split(",");

                    for (String amigo : amigos) {
                        usuario.setAmigo(this.getUsuario(amigo));
                    }
                }
                br.close();
            }

            File arquivo3 = new File("./database/recados.txt");
            if (arquivo3.exists()) {
                String[] dados;
                String linha;

                BufferedReader br = new BufferedReader(new FileReader(arquivo3));

                while ((linha = br.readLine()) != null) {
                    dados = linha.split(";");
                    User usuario = this.getUsuario(dados[0]);
                    User amigo = this.getUsuario(dados[1]);
                    String recado = dados[2];
                    try {
                        this.enviarRecado(amigo, usuario, recado);
                    } catch (UserIsEnemyException e) {
                    }
                }
                br.close();
            }

            File arquivo4 = new File("./database/communities.txt");
            if (arquivo4.exists()) {
                String[] dados;
                String linha;

                BufferedReader br = new BufferedReader(new FileReader(arquivo4));

                while ((linha = br.readLine()) != null) {
                    dados = linha.split(";");
                    User dono = this.getUsuario(dados[0]);
                    String nome = dados[1];
                    String descricao = dados[2];

                    Community comunidade = new Community(dono, nome, descricao);

                    dono.setCriadorComunidade(comunidade);

                    String[] membros = dados[3].substring(1, dados[3].length() - 1).split(",");

                    for (String membro : membros) {
                        if (membro.equals(dono.getLogin())) {
                            continue;
                        }
                        comunidade.adicionarMembro(this.getUsuario(membro));
                    }

                    this.communities.put(nome, comunidade);
                }

                try {
                    for (String login : communities.keySet()) {
                        User usuario = this.getUsuario(login);
                        for (String comunidade : communities.get(login)) {
                            usuario.setParticipanteComunidade(this.getComunidade(comunidade));
                        }
                    }
                } catch (CommunityNotExistsException e) {
                }

                br.close();
            }

            File arquivo5 = new File("./database/mensagens.txt");
            if (arquivo5.exists()) {
                String[] dados;
                String linha;

                BufferedReader br = new BufferedReader(new FileReader(arquivo5));

                while ((linha = br.readLine()) != null) {
                    dados = linha.split(";");
                    User usuario = this.getUsuario(dados[0]);
                    String mensagem = dados[1];
                    Messages msg = new Messages(mensagem);
                    usuario.receberMensagem(msg);
                }
                br.close();
            }

            File arquivo6 = new File("./database/relacoes.txt");
            if (arquivo6.exists()) {
                String[] dados;
                String linha;

                BufferedReader br = new BufferedReader(new FileReader(arquivo6));

                while ((linha = br.readLine()) != null) {
                    dados = linha.split(";");
                    User usuario = this.getUsuario(dados[0]);
                    User amigo = this.getUsuario(dados[1]);
                    String tipo = dados[2];

                    switch (tipo) {
                        case "idolo":
                            usuario.setIdolo(amigo);
                            break;
                        case "fa":
                            usuario.setFa(amigo);
                            break;
                        case "paquera":
                            usuario.setPaquera(amigo);
                            break;
                        case "paqueraRecebida":
                            usuario.setCrushesReceived(amigo);
                            break;
                        case "inimigo":
                            usuario.setInimigo(amigo);
                            amigo.setInimigo(usuario);
                            break;
                    }
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p> Adds a user to the system. </p>
     *
     * @param usuario User to be added.
     *
     * @throws AccountAlreadyExistsException Exception thrown if the user is already registered.
     *
     * @see User
     */

    public void setUsuario(User usuario) throws AccountAlreadyExistsException {
        String login = usuario.getLogin();

        if (this.users.containsKey(login)) {
            throw new AccountAlreadyExistsException();
        }

        this.users.put(login, usuario);
    }

    /**
     * <p> Logs a user into the system, creating their session ID and returning it. </p>
     *
     * @param login User's login.
     * @param senha User's password.
     * @return ID of the user's session.
     *
     * @throws LoginSenhaInvalidsException Exception thrown if the login or password is invalid.
     *
     * @see User
     */

    public String abrirSessao(String login, String senha) throws LoginSenhaInvalidsException {
        try{
            User usuario = this.getUsuario(login);

            if (!usuario.verificarSenha(senha)) {
                throw new LoginSenhaInvalidsException("any");
            }

            String id = UUID.randomUUID().toString();

            this.sessions.put(id, usuario);

            return id;
        } catch (UserIsNotRegisterException e) {
            throw new LoginSenhaInvalidsException("any");
        }
    }

    /**
     * <p> Retrieves a user from the system by their login. </p>
     *
     * @param login User's login.
     *
     * @throws UserIsNotRegisterException Exception thrown if the user is not registered.
     *
     * @see User
     */

    public User getUsuario(String login) throws UserIsNotRegisterException {
        if (!this.users.containsKey(login)) {
            throw new UserIsNotRegisterException();
        }

        return this.users.get(login);
    }

    /**
     * <p> Retrieves a logged-in user from the system by their session ID. </p>
     *
     * @param id Session ID of the user.
     *
     * @throws UserIsNotRegisterException Exception thrown if the user is not registered.
     *
     * @see User
     */

    public User getSessaoUsuario(String id) throws UserIsNotRegisterException {
        if (!this.sessions.containsKey(id)) {
            throw new UserIsNotRegisterException();
        }

        return this.sessions.get(id);
    }

    /**
     * <p> Retrieves all users from the system. </p>
     *
     * @return Map with all users in the system.
     *
     * @see User
     */

    public Map<String, User> getUsers() {
        return this.users;
    }

    /**
     * <p> Adds a friend to the user if they are not already friends, have already requested friendship, or have received a friend request from the user. </p>
     *
     * @param amigo Friend's login to be added.
     *
     * @throws UserAlreadyHaveRelationException       Exception thrown if the user is already friends with the logged-in user.
     * @throws UserAutoRelationException              Exception thrown if the user tries to add themselves as a friend.
     * @throws UserAlreadySentConviteException        Exception thrown if the user has already requested friendship from the friend.
     * @throws UserIsEnemyException                   Exception thrown if the user is an enemy of the friend.
     */

    public void adicionarAmigo(User usuario, User amigo)
            throws UserAlreadyHaveRelationException, UserAlreadySentConviteException, UserAutoRelationException,
            UserIsEnemyException {
        if (usuario.equals(amigo)) {
            throw new UserAutoRelationException("amizade");
        }

        if (usuario.getFriends().contains(amigo) || amigo.getFriends().contains(usuario)) {
            throw new UserAlreadyHaveRelationException("amigo");
        }

        this.verificarInimigo(usuario, amigo);

        if (usuario.getSolicitationsSent().contains(amigo)) {
            throw new UserAlreadySentConviteException();
        } else if (usuario.getSolicitationsReceived().contains(amigo)) {
            usuario.aceitarSolicitacao(amigo);
        } else {
            usuario.enviarSolicitacao(amigo);
        }
    }

    /**
     * <p> Sends a message to the specified user. </p>
     *
     * @param destinatario Recipient's login.
     * @param recado       Message to be sent.
     *
     * @throws SelfSentErrandException Exception thrown if the user tries to send a message to themselves.
     * @throws UserIsEnemyException    Exception thrown if the user is an enemy of the recipient.
     */

    public void enviarRecado(User remetente, User destinatario, String recado) throws SelfSentErrandException, UserIsEnemyException {
        if (remetente.getLogin().equals(destinatario.getLogin())) {
            throw new SelfSentErrandException();
        }

        this.verificarInimigo(remetente, destinatario);

        Errand r = new Errand(remetente, destinatario, recado);
        destinatario.receberRecado(r);
    }

    /**
     * <p> Retrieves a community from the system by its name. </p>
     *
     * @param nome Name of the community.
     * @return Community with the specified name.
     *
     * @throws CommunityNotExistsException Exception thrown if the community does not exist.
     */

    public Community getComunidade(String nome) throws CommunityNotExistsException {
        if (!this.communities.containsKey(nome)) {
            throw new CommunityNotExistsException();
        }

        return this.communities.get(nome);
    }

    /**
     * <p> Creates a community in the system. </p>
     *
     * @param dono       User who owns the community.
     * @param nome       Community name.
     * @param descricao  Community description.
     *
     * @throws CommunityAlreadyExistisException Exception thrown if the community already exists with the given name.
     *
     * @see Community
     */

    public void criarComunidade(User dono, String nome, String descricao) throws CommunityAlreadyExistisException {
        if (this.communities.containsKey(nome)) {
            throw new CommunityAlreadyExistisException();
        }

        Community comunidade = new Community(dono, nome, descricao);
        this.communities.put(nome, comunidade);

        dono.setCriadorComunidade(comunidade);
        dono.setParticipanteComunidade(comunidade);
    }

    /**
     * <p> Retrieves the description of a community from the system by its name. </p>
     *
     * @param nome Name of the community.
     * @return Description of the community with the specified name.
     *
     * @throws CommunityNotExistsException Exception thrown if the community does not exist.
     */

    public String getDescricaoComunidade(String nome) throws CommunityNotExistsException {
        Community comunidade = this.getComunidade(nome);

        return comunidade.getDescricao();
    }

    /**
     * <p> Retrieves the owner of a community from the system by its name. </p>
     *
     * @param nome Name of the community.
     * @return Owner of the community with the specified name.
     *
     * @throws CommunityNotExistsException Exception thrown if the community does not exist.
     */

    public String getDonoComunidade(String nome) throws CommunityNotExistsException {
        Community comunidade = this.getComunidade(nome);

        return comunidade.getCriador().getLogin();
    }

    /**
     * <p> Retrieves the members of a community in a formatted string. </p>
     *
     * @param nome Name of the community.
     * @return Members of the community with the specified name in a formatted string.
     *
     * @throws CommunityNotExistsException Exception thrown if the community does not exist.
     */

    public String getMembrosComunidade(String nome) throws CommunityNotExistsException {
        Community comunidade = this.getComunidade(nome);

        return comunidade.getMembrosString();
    }

    /**
     * <p> Retrieves the communities of which the user is a member in a formatted string. </p>
     *
     * @param usuario User.
     * @return Communities of which the user is a member.
     */

    public String getComunidades(User usuario) {
        return UtilsString.formatArrayList(usuario.getComunidadesParticipantes());
    }

    /**
     * <p> Adds a user to a community. </p>
     *
     * @param usuario User.
     * @param nome    Name of the community.
     *
     * @throws CommunityNotExistsException        Exception thrown if the community does not exist.
     * @throws UserAlreadyInACommunityException  Exception thrown if the user is already in the community.
     */

    public void adicionarComunidade(User usuario, String nome)
            throws CommunityNotExistsException, UserAlreadyInACommunityException {
        Community comunidade = this.getComunidade(nome);

        if (usuario.getComunidadesParticipantes().contains(comunidade)) {
            throw new UserAlreadyInACommunityException();
        }

        comunidade.adicionarMembro(usuario);
        usuario.setParticipanteComunidade(comunidade);
    }

    /**
     * <p> Reads a message for the user. </p>
     *
     * @param usuario User.
     * @return The message read by the user.
     *
     * @throws DontHaveMessagesException Exception thrown if the user has no messages.
     */
    public String lerMensagem(User usuario) throws DontHaveMessagesException {
        return usuario.lerMensagem();
    }

    /**
     * <p> Sends a message to a community. </p>
     *
     * @param comunidade Community.
     * @param msg       Message to be sent.
     */
    public void enviarMensagem(Community comunidade, String msg) {
        Messages mensagem = new Messages(msg);

        comunidade.enviarMensagem(mensagem);
    }

    /**
     * <p> Adds a user as an idol. </p>
     *
     * @param usuario User.
     * @param idolo   User to be added as an idol.
     *
     * @throws UserAutoRelationException       Exception thrown if the user tries to add themselves as an idol.
     * @throws UserAlreadyHaveRelationException Exception thrown if the user already has a relation with the specified user.
     * @throws UserIsEnemyException            Exception thrown if the user is an enemy of the specified user.
     */
    public void adicionarIdolo(User usuario, User idolo)
            throws UserAutoRelationException, UserAlreadyHaveRelationException, UserIsEnemyException {
        if (usuario.equals(idolo)) {
            throw new UserAutoRelationException("fã");
        }

        if (usuario.getIdols().contains(idolo)) {
            throw new UserAlreadyHaveRelationException("Ídolo");
        }

        this.verificarInimigo(usuario, idolo);

        usuario.setIdolo(idolo);
        idolo.setFa(usuario);
    }

    /**
     * <p> Retrieves the fans (idols) of a user in a formatted string. </p>
     *
     * @param usuario User.
     * @return Fans (idols) of the user in a formatted string.
     */
    public String getFas(User usuario) {
        return UtilsString.formatArrayList(usuario.getFas());
    }

    /**
     * <p> Adds a user as a crush (romantic interest). </p>
     *
     * @param usuario User.
     * @param paquera User to be added as a crush.
     *
     * @throws UserAutoRelationException       Exception thrown if the user tries to add themselves as a crush.
     * @throws UserAlreadyHaveRelationException Exception thrown if the user already has a relation with the specified user.
     * @throws UserIsEnemyException            Exception thrown if the user is an enemy of the specified user.
     */
    public void adicionarPaquera(User usuario, User paquera)
            throws UserAutoRelationException, UserAlreadyHaveRelationException, UserIsEnemyException {
        if (usuario.equals(paquera)) {
            throw new UserAutoRelationException("paquera");
        }

        if (usuario.getCrushes().contains(paquera)) {
            throw new UserAlreadyHaveRelationException("paquera");
        }

        this.verificarInimigo(usuario, paquera);

        if (usuario.getCrushesReceived().contains(paquera) || paquera.getCrushesReceived().contains(usuario)) {
            this.enviarRecado(paquera, usuario, paquera.getName() + " é seu paquera - Recado do Jackut.");
            this.enviarRecado(usuario, paquera, usuario.getName() + " é seu paquera - Recado do Jackut.");
        }

        usuario.setPaquera(paquera);
        paquera.setCrushesReceived(usuario);
    }

    /**
     * <p> Retrieves the crushes (romantic interests) of a user in a formatted string. </p>
     *
     * @param usuario User.
     * @return Crushes (romantic interests) of the user in a formatted string.
     */
    public String getPaqueras(User usuario) {
        return UtilsString.formatArrayList(usuario.getCrushes());
    }

    /**
     * <p> Adds a user as an enemy. </p>
     *
     * @param usuario User.
     * @param inimigo User to be added as an enemy.
     *
     * @throws UserAutoRelationException       Exception thrown if the user tries to add themselves as an enemy.
     * @throws UserAlreadyHaveRelationException Exception thrown if the user already has a relation with the specified user.
     */
    public void adicionarInimigo(User usuario, User inimigo)
            throws UserAutoRelationException, UserAlreadyHaveRelationException {
        if (usuario.equals(inimigo)) {
            throw new UserAutoRelationException("inimigo");
        }

        if (usuario.getEnemy().contains(inimigo)) {
            throw new UserAlreadyHaveRelationException("inimigo");
        }

        usuario.setInimigo(inimigo);
        inimigo.setInimigo(usuario);
    }

    /**
     * <p> Checks if a user is an enemy of another user. </p>
     *
     * @param usuario User.
     * @param inimigo User to be checked as an enemy.
     *
     * @throws UserIsEnemyException Exception thrown if the user is an enemy of the specified user.
     */
    public void verificarInimigo(User usuario, User inimigo) throws UserIsEnemyException {
        if (usuario.getEnemy().contains(inimigo)) {
            throw new UserIsEnemyException(inimigo.getName());
        }
    }

    /**
     * <p> Removes a user from the system, along with all associated information, including relationships, sent messages, and profile. </p>
     *
     * @param usuario User to be removed from the system.
     * @param id      ID of the user's session.
     *
     * @throws UserIsNotRegisterException Exception thrown if the user is not registered in the system.
     */
    public void removerUsuario(User usuario, String id) throws UserIsNotRegisterException {
        for (User amigo : usuario.getFriends()) {
            amigo.removerAmigo(usuario);
        }

        for (User fa : usuario.getIdols()) {
            fa.removerFa(usuario);
        }

        for (User paquera : usuario.getCrushes()) {
            paquera.removerPaquera(usuario);
        }

        for (User paqueraRecebida : usuario.getCrushesReceived()) {
            paqueraRecebida.removerPaqueraRecebida(usuario);
        }

        for (User inimigo : usuario.getEnemy()) {
            inimigo.removerInimigo(usuario);
        }

        for (User solicitacaoEnviada : usuario.getSolicitationsSent()) {
            solicitacaoEnviada.removerSolicitacaoEnviada(usuario);
        }

        for (User solicitacaoRecebida : usuario.getSolicitationsReceived()) {
            solicitacaoRecebida.removerSolicitacaoRecebida(usuario);
        }

        for (Community comunidade : this.communities.values()) {
            if (comunidade.getCriador().equals(usuario)) {
                for (User membro : comunidade.getMembros()) {
                    membro.removerComunidade(comunidade);
                }
                this.communities.remove(comunidade.getNome());
            }
        }

        for (User destinatario : this.users.values()) {
            for (Errand recado : destinatario.getErrands()) { // O(n^2)
                if (recado.getRemetente().equals(usuario)) {
                    destinatario.removerRecado(recado);
                }
            }
        }

        this.users.remove(usuario.getLogin());
        this.sessions.remove(id);
    }

    /**
     * <p> Resets the system's information. </p>
     */

    public void zerarSistema() {
        this.users = new HashMap<>();
        this.sessions = new HashMap<>();
        this.communities = new HashMap<>();
        try {
            UtilsFileHandler.limparArquivos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p> Saves the registration to a file and terminates the program.</p>
     * <p> Reaching the end of a script (end of a file) is equivalent to finding this command. </p>
     * <p> In this case, the command has no parameters and will save at this time only the registered users. </p>
     *
     * @throws AttributeNotFilledException Exception thrown if any attribute is not filled.
     */

    public void encerrarSistema() throws AttributeNotFilledException {
        try {
            UtilsFileHandler.criarPasta();

            UtilsFileHandler.persistirDados(this.users, this.communities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
