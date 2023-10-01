package br.ufal.ic.p2.jackut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.ufal.ic.p2.jackut.Exceptions.ComunidadeAlreadyExistsException;
import br.ufal.ic.p2.jackut.Exceptions.ComunidadeNotExistException;
import br.ufal.ic.p2.jackut.Exceptions.UnfilledAttributeException;

/**
 * <p> Classe que representa o sistema. </p>
 */

public class System {
    private Map<String, User> sessions = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private Map<String, Comunidade> comunidades = new HashMap<>();
    

    /**
     * <p> Constrói um novo {@code Sistema} responsável por
     * gerenciar os usuários e as sessões. </p>
     *
     * <p> Inicializa as listas de usuários e sessões. </p>
     *
     * @see User
     */

    public System() {
    	try {
    		UtilsFileHandler.criarPasta();

            Map<String, String[]> comunidades = new HashMap<>();

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

                    comunidades.put(login, comunidadesUsuario);

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

//            File arquivo3 = new File("./database/recados.txt");
//            if (arquivo3.exists()) {
//                String[] dados;
//                String linha;
//
//                BufferedReader br = new BufferedReader(new FileReader(arquivo3));
//
//                while ((linha = br.readLine()) != null) {
//                    dados = linha.split(";");
//                    User usuario = this.getUsuario(dados[0]);
//                    User amigo = this.getUsuario(dados[1]);
//                    String recado = dados[2];
//                    try {
//                        this.enviarRecado(amigo, usuario, recado);
//                    } catch (UsuarioEhInimigoException e) {
//                    }
//                }
//                br.close();
//            }

            File arquivo4 = new File("./database/comunidades.txt");
            if (arquivo4.exists()) {
                String[] dados;
                String linha;

                BufferedReader br = new BufferedReader(new FileReader(arquivo4));

                while ((linha = br.readLine()) != null) {
                    dados = linha.split(";");
                    User dono = this.getUsuario(dados[0]);
                    String nome = dados[1];
                    String descricao = dados[2];

                    Comunidade comunidade = new Comunidade(dono, nome, descricao);

                    dono.setCriadorComunidade(comunidade);

                    String[] membros = dados[3].substring(1, dados[3].length() - 1).split(",");

                    for (String membro : membros) {
                        if (membro.equals(dono.getLogin())) {
                            continue;
                        }
                        comunidade.adicionarMembro(this.getUsuario(membro));
                    }

                    this.comunidades.put(nome, comunidade);
                }

                try {
                    for (String login : comunidades.keySet()) {
                        User usuario = this.getUsuario(login);
                        for (String comunidade : comunidades.get(login)) {
                            usuario.setParticipanteComunidade(this.getComunidade(comunidade));
                        }
                    }
                } catch (ComunidadeNotExistException e) {
                }

                br.close();
            }

//            File arquivo5 = new File("./database/mensagens.txt");
//            if (arquivo5.exists()) {
//                String[] dados;
//                String linha;
//
//                BufferedReader br = new BufferedReader(new FileReader(arquivo5));
//
//                while ((linha = br.readLine()) != null) {
//                    dados = linha.split(";");
//                    User usuario = this.getUsuario(dados[0]);
//                    String mensagem = dados[1];
//                    Messages msg = new Messages(mensagem);
//                    usuario.receberMensagem(msg);
//                }
//                br.close();
//            }

//            File arquivo6 = new File("./database/relacoes.txt");
//            if (arquivo6.exists()) {
//                String[] dados;
//                String linha;
//
//                BufferedReader br = new BufferedReader(new FileReader(arquivo6));
//
//                while ((linha = br.readLine()) != null) {
//                    dados = linha.split(";");
//                    User usuario = this.getUsuario(dados[0]);
//                    User amigo = this.getUsuario(dados[1]);
//                    String tipo = dados[2];
//
//                    switch (tipo) {
//                        case "idolo":
//                            usuario.setIdolo(amigo);
//                            break;
//                        case "fa":
//                            usuario.setFa(amigo);
//                            break;
//                        case "paquera":
//                            usuario.setPaquera(amigo);
//                            break;
//                        case "paqueraRecebida":
//                            usuario.setPaquerasRecebidas(amigo);
//                            break;
//                        case "inimigo":
//                            usuario.setInimigo(amigo);
//                            amigo.setInimigo(usuario);
//                            break;
//                    }
//                }
//                br.close();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void encerrarSistema() throws UnfilledAttributeException {
        try {
            UtilsFileHandler.criarPasta();

            UtilsFileHandler.persistirDados(this.users, this.comunidades);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    
    public Comunidade getComunidade(String nome) throws ComunidadeNotExistException {
        if (!this.comunidades.containsKey(nome)) {
            throw new ComunidadeNotExistException();
        }

        return this.comunidades.get(nome);
    }

    
    public void criarComunidade(User dono, String nome, String descricao) throws ComunidadeAlreadyExistsException{
    	if(this.comunidades.containsKey(nome)) {
    		throw new ComunidadeAlreadyExistsException();
    	}
    	
    	Comunidade comunidade = new Comunidade(dono, nome,descricao);
    	this.comunidades.put(nome, comunidade);
    	dono.setCriadorComunidade(comunidade);
    	dono.setParticipanteComunidade(comunidade);

    }
    
    public String getDescricaoComunidade(String nome) throws ComunidadeNotExistException {
        Comunidade comunidade = this.getComunidade(nome);

        return comunidade.getDescricao();
    }
    
    public String getDonoComunidade(String name) throws ComunidadeNotExistException {
        Comunidade comunidade = this.getComunidade(name);

        return comunidade.getCriador().getLogin();
    }
    public String getMembrosComunidade(String name) throws ComunidadeNotExistException {
        Comunidade comunidade = this.getComunidade(name);

        return comunidade.getMembrosString();
    }

}
