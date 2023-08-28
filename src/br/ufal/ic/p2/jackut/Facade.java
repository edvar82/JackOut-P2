package br.ufal.ic.p2.jackut;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import br.ufal.ic.p2.jackut.Exceptions.*;

/**
 * <p> Classe fachada que implementa a interface do sistema Jackut. </p>
 */

public class Facade {
    private final System system;

    /**
     * <p> Constrói um novo {@code Facade} e inicializa uma instância do {@link System}. </p>
     * 
     * <p> Os dados do usuário são carregados de um arquivo caso o arquivo exista. </p>
     * <p> Os dados carregados incluem informações do usuário, como: atributos, amigos e recados. </p>
     *
     * @see System
     */

    public Facade() {
        this.system = new System();

        try{
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
                    for (int i = 3; i < dados.length; i++) {
                        String[] atributo = dados[i].split(":");
                        usuario.getPerfil().setAtributo(atributo[0], atributo[1]);
                    }
                    this.system.setUsuario(usuario);
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
                    User usuario = this.system.getUsuario(dados[0]);

                    if (dados[1].length() <= 2) {
                        continue;
                    }

                    String[] amigos = dados[1].substring(1, dados[1].length() - 1).split(",");

                    for (String amigo : amigos) {
                        usuario.setAmigo(this.system.getUsuario(amigo));
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
                    User usuario = this.system.getUsuario(dados[0]);
                    User amigo = this.system.getUsuario(dados[1]);
                    String recado = dados[2];
                    amigo.enviarRecado(usuario, recado);
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p> Apaga todos os dados mantidos no sistema. </p>
     *
     * @see System
     */

    public void zerarSistema() {
        try {
            File arquivo = new File("./database/usuarios.txt");
            arquivo.delete();
            File arquivo2 = new File("./database/amigos.txt");
            arquivo2.delete();
            File arquivo3 = new File("./database/recados.txt");
            arquivo3.delete();
            this.system.zerarSistema();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p> Cria um usuário com os dados da conta fornecidos. </p>
     *
     * @param login  Login do usuário
     * @param senha  Senha do usuário
     * @param nome   Nome do usuário
     *
     * @throws InvalidLoginOrPasswordException  Exceção lançada caso o login ou a senha sejam inválidos
     * @throws AccountAlreadyExistsException        Exceção lançada caso o login já esteja cadastrado
     * 
     * @see User
     */

    public void criarUsuario(String login, String senha, String nome) throws InvalidLoginOrPasswordException, AccountAlreadyExistsException {
        if (login == null) {
            throw new InvalidLoginOrPasswordException("login");
        }

        if (senha == null) {
            throw new InvalidLoginOrPasswordException("senha");
        }

        if (this.system.getUsuario(login) != null) {
            throw new AccountAlreadyExistsException();
        }

        User usuario = new User(login, senha, nome);
        this.system.setUsuario(usuario);
    }

    /**
     * <p> Abre uma sessão para um usuário com o login e a senha fornecidos,
     * e retorna uma id para esta sessão. </p>
     *
     * @param login  Login do usuário
     * @param senha  Senha do usuário
     * @return       ID da sessão
     *
     * @throws InvalidLoginOrPasswordException Exceção lançada caso o login ou a senha sejam inválidos
     */

    public String abrirSessao(String login, String senha) throws InvalidLoginOrPasswordException {
        User usuario = this.system.getUsuario(login);

        if (usuario == null) {
            throw new InvalidLoginOrPasswordException("any");
        }

        if (!usuario.verificarSenha(senha)) {
            throw new InvalidLoginOrPasswordException("any");
        }

        this.system.setSessaoUsuario(usuario);

        return this.system.getSessao(usuario);
    }

    /**
     * <p> Retorna o valor do atributo de um usuário, armazenado em seu perfil. </p>
     *
     * @param login     Login do usuário
     * @param atributo  Atributo a ser retornado
     * @return          Valor do atributo
     *
     * @throws UserNotRegisteredException   Exceção lançada caso o usuário não esteja cadastrado
     * @throws UnfilledAttributeException  Exceção lançada caso o atributo não esteja preenchido
     */

    public String getAtributoUsuario(String login, String atributo)
            throws UserNotRegisteredException, UnfilledAttributeException {
        User usuario = this.system.getUsuario(login);

        if (usuario == null) {
            throw new UserNotRegisteredException();
        }

        if (atributo.equals("nome")) {
            return usuario.getNome();
        } else {
            return usuario.getPerfil().getAtributo(atributo);
        }
    }

    /**
     * <p> Modifica o valor de um atributo do perfil de um usuário para o valor especificado. </p>
     * <p> Uma sessão válida <b>(identificada por id)</b> deve estar aberta para o usuário
     * cujo perfil se quer editar </p>
     *
     * @param id        ID da sessão
     * @param atributo  Atributo a ser modificado
     * @param valor     Novo valor do atributo
     *
     * @throws UserNotRegisteredException Exceção lançada caso o usuário não esteja cadastrado
     */

    public void editarPerfil(String id, String atributo, String valor)
            throws UserNotRegisteredException {
        User usuario = this.system.getSessaoUsuario(id);

        if (usuario == null) {
            throw new UserNotRegisteredException();
        }


        usuario.getPerfil().setAtributo(atributo, valor);
    }

    /**
     * <p> Adiciona um amigo ao usuário aberto na sessão especificada através de id. </p>
     *
     * @param id     ID da sessão
     * @param amigo  Login do amigo a ser adicionado
     *
     * @throws UserAlreadyFriendException             Exceção lançada caso o usuário já seja amigo do usuário aberto na sessão
     * @throws UserNotRegisteredException       Exceção lançada caso o usuário ou o amigo não estejam cadastrados
     * @throws UserSelfFriendshipException     Exceção lançada caso o usuário tente adicionar a si mesmo como amigo
     * @throws UserAlreadyRequestedFriendshipException  Exceção lançada caso o usuário já tenha solicitado amizade ao amigo
     */

    public void adicionarAmigo(String id, String amigo) throws UserAlreadyFriendException, UserNotRegisteredException,
            UserSelfFriendshipException, UserAlreadyRequestedFriendshipException {
        User usuario = this.system.getSessaoUsuario(id);
        User amigoUsuario = this.system.getUsuario(amigo);

        if (amigoUsuario == null || usuario == null) {
            throw new UserNotRegisteredException();
        }

        if (usuario.getLogin().equals(amigoUsuario.getLogin())) {
            throw new UserSelfFriendshipException();
        }

        if (usuario.getAmigos().contains(amigoUsuario) || amigoUsuario.getAmigos().contains(usuario)) {
            throw new UserAlreadyFriendException();
        }

        if (usuario.getSolicitacoesEnviadas().contains(amigoUsuario)) {
            throw new UserAlreadyRequestedFriendshipException();
        } else if (usuario.getSolicitacoesRecebidas().contains(amigoUsuario)) {
            usuario.aceitarSolicitacao(amigoUsuario);
        } else {
            usuario.enviarSolicitacao(amigoUsuario);
        }
    }

    /**
     * <p> Retorna true se os dois usuários são amigos. </p>
     *
     * @param login   Login do primeiro usuário
     * @param amigo   Login do segundo usuário
     * @return        Booleano indicando se os usuários são amigos
     *
     * @throws UserNotRegisteredException Exceção lançada caso um dos usuários não esteja cadastrado
     */

    public boolean ehAmigo(String login, String amigo) throws UserNotRegisteredException {
        User usuario = this.system.getUsuario(login);
        User amigoUsuario = this.system.getUsuario(amigo);

        if (usuario == null || amigoUsuario == null) {
            throw new UserNotRegisteredException();
        }

        return usuario.getAmigos().contains(amigoUsuario);
    }

    /**
     * <p> Retorna a lista de amigos do usuário especificado. </p>
     * <p> O retorno é formatado como uma String no formato: <b>{amigo1,amigo2,amigo3,...}</b> </p>
     *
     * @param login  Login do usuário
     * @return       Lista de amigos do usuário formatada em uma String
     *
     * @throws UserNotRegisteredException Exceção lançada caso o usuário não esteja cadastrado
     */

    public String getAmigos(String login) throws UserNotRegisteredException {
        User usuario = this.system.getUsuario(login);

        if (usuario == null) {
            throw new UserNotRegisteredException();
        }

        String amigos = "{";
        for (int i = 0; i < usuario.getAmigos().size(); i++) {
            User amigo = usuario.getAmigos().get(i);
            amigos += amigo.getLogin();
            if (i < usuario.getAmigos().size() - 1) {
                amigos += ",";
            }
        }

        amigos += "}";
        return amigos;
    }

    /**
     * <p> Envia o recado especificado ao destinatário especificado. </p>
     * <p> Uma sessão válida <b>(identificada por id)</b> deve estar aberta 
     * para o usuário que deseja enviar o recado. </p>
     *
     * @param id            ID da sessão
     * @param destinatario  Login do destinatário
     * @param recado        Recado a ser enviado
     *
     * @throws UserNotRegisteredException    Exceção lançada caso o usuário ou o destinatário não estejam cadastrados
     * @throws UserSelfMessageException  Exceção lançada caso o usuário tente enviar um recado para si mesmo
     */

    public void enviarRecado(String id, String destinatario, String recado) throws UserNotRegisteredException, UserSelfMessageException {
        User usuario = this.system.getSessaoUsuario(id);
        User destinatarioUsuario = this.system.getUsuario(destinatario);

        if (usuario == null || destinatarioUsuario == null) {
            throw new UserNotRegisteredException();
        }

        if (usuario.getLogin().equals(destinatarioUsuario.getLogin())) {
            throw new UserSelfMessageException();
        }

        usuario.enviarRecado(destinatarioUsuario, recado);
    }

    /**
     * <p> Retorna o primeiro recado da fila de recados do usuário com a sessão aberta
     * identificada por id. </p>
     *
     * @param id  ID da sessão
     * @return    Primeiro recado da fila de recados do usuário
     *
     * @throws UserNotRegisteredException  Exceção lançada caso o usuário não esteja cadastrado
     * @throws NoMessagesException          Exceção lançada caso o usuário não tenha recados na fila
     */

    public String lerRecado(String id) throws UserNotRegisteredException, NoMessagesException {
        User usuario = this.system.getSessaoUsuario(id);

        if (usuario == null) {
            throw new UserNotRegisteredException();
        }

        Messages messages = usuario.getRecado();

        if (messages == null) {
            throw new NoMessagesException();
        }

        return messages.getRecado();
    }

    /**
     * <p> Grava o cadastro em arquivo e encerra o programa.</p>
     * <p> Atingir o final de um script (final de arquivo) é equivalente 
     * a encontrar este comando. </p>
     * <p> Neste caso, o comando não tem parâmetros, e salvará nesse momento
     * apenas os usuários cadastrados. </p>
     *
     * @throws UnfilledAttributeException Exceção lançada caso algum atributo não esteja preenchido
     */

    public void encerrarSistema() throws UnfilledAttributeException {
        try {
            File arquivoUsuarios = new File("./database/usuarios.txt");
            arquivoUsuarios.createNewFile();

            FileWriter fw = new FileWriter(arquivoUsuarios);
            BufferedWriter bw = new BufferedWriter(fw);

            for (User usuario : this.system.getUsuarios().values()) {
                bw.write(usuario.getLogin() + ";" + usuario.getSenha() + ";" + usuario.getNome());
                for (String atributo : usuario.getPerfil().getAtributos().keySet()) {
                    bw.write(";" + atributo + ":" + usuario.getPerfil().getAtributo(atributo));
                }
                bw.newLine();
            }

            bw.flush();
            bw.close();
            fw.close();

            File arquivoAmigos = new File("./database/amigos.txt");
            arquivoAmigos.createNewFile();

            FileWriter fw2 = new FileWriter(arquivoAmigos);
            BufferedWriter bw2 = new BufferedWriter(fw2);

            for (User usuario : this.system.getUsuarios().values()) {
                String amigos = this.getAmigos(usuario.getLogin());
                bw2.write(usuario.getLogin() + ";" + amigos);
                bw2.newLine();
            }

            bw2.flush();
            bw2.close();
            fw2.close();

            File arquivoRecados = new File("./database/recados.txt");
            arquivoRecados.createNewFile();

            FileWriter fw3 = new FileWriter(arquivoRecados);
            BufferedWriter bw3 = new BufferedWriter(fw3);

            for (User usuario : this.system.getUsuarios().values()) {
                for (Messages messages : usuario.getRecados()) {
                    bw3.write(usuario.getLogin() + ";" + messages.getRemetente().getLogin() + ";" + messages.getRecado());
                    bw3.newLine();
                }
            }

            bw3.flush();
            bw3.close();
            fw3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}