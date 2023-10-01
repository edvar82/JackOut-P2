package br.ufal.ic.p2.jackut;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class UtilsFileHandler {
    public static void criarPasta() {
        String caminho = "./database";

        File diretorio = new File(caminho);

        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
    }

    public static void escreverArquivo(String nomeArquivo, String conteudo) throws IOException {
        File arquivo = new File("./database/" + nomeArquivo);
        arquivo.createNewFile();

        FileWriter fw = new FileWriter(arquivo);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(conteudo);
        bw.flush();
        bw.close();
        fw.close();
    }

    public static void salvarUsuarios(Map<String, User> usuarios) throws IOException {
        StringBuilder usuariosData = new StringBuilder();
        for (User usuario : usuarios.values()) {
            usuariosData.append(usuario.getLogin()).append(";")
                    .append(usuario.getPassword()).append(";")
                    .append(usuario.getName()).append(";");

            for (String atributo : usuario.getProfile().getAtributos().keySet()) {
                usuariosData.append(atributo).append(":")
                        .append(usuario.getProfile().getAtributo(atributo)).append(";");
            }

            usuariosData.append(UtilsString.formatArrayList(usuario.getComunidadesParticipantes())).append("\n");
        }

        escreverArquivo("usuarios.txt", usuariosData.toString());
    }

    public static void salvarAmigos(Map<String, User> usuarios) throws IOException {
        StringBuilder amigosData = new StringBuilder();
        for (User usuario : usuarios.values()) {
            String amigos = usuario.getAmigosString();
            amigosData.append(usuario.getLogin()).append(";").append(amigos).append("\n");
        }

        escreverArquivo("amigos.txt", amigosData.toString());
    }

    public static void salvarRecados(Map<String, User> usuarios) throws IOException {
        StringBuilder recadosData = new StringBuilder();
        for (User usuario : usuarios.values()) {
            for (Errand recado : usuario.getErrands()) {
                recadosData.append(usuario.getLogin()).append(";")
                        .append(recado.getRemetente().getLogin()).append(";")
                        .append(recado.getRecado()).append("\n");
            }
        }

        escreverArquivo("recados.txt", recadosData.toString());
    }

    public static void salvarComunidades(Map<String, Community> comunidades) throws IOException {
        StringBuilder comunidadesData = new StringBuilder();
        for (Community comunidade : comunidades.values()) {
            String membros = comunidade.getMembrosString();
            comunidadesData.append(comunidade.getCriador().getLogin()).append(";")
                    .append(comunidade.getNome()).append(";")
                    .append(comunidade.getDescricao()).append(";")
                    .append(membros).append("\n");
        }

        escreverArquivo("comunidades.txt", comunidadesData.toString());
    }

    public static void salvarMensagens(Map<String, User> usuarios) throws IOException {
        StringBuilder mensagensData = new StringBuilder();
        for (User usuario : usuarios.values()) {
            for (Messages mensagem : usuario.getMessages()) {
                mensagensData.append(usuario.getLogin()).append(";")
                        .append(mensagem.getMensagem()).append("\n");
            }
        }

        escreverArquivo("mensagens.txt", mensagensData.toString());
    }

    public static void salvarRelacoes(Map<String, User> usuarios) throws IOException {
        StringBuilder relacoesData = new StringBuilder();
        for (User usuario : usuarios.values()) {
            for (User idolo : usuario.getIdols()) {
                relacoesData.append(usuario.getLogin()).append(";")
                        .append(idolo.getLogin()).append(";")
                        .append("idolo").append("\n");
            }

            for (User fa : usuario.getFas()) {
                relacoesData.append(usuario.getLogin()).append(";")
                        .append(fa.getLogin()).append(";")
                        .append("fa").append("\n");
            }

            for (User paquera : usuario.getCrushes()) {
                relacoesData.append(usuario.getLogin()).append(";")
                        .append(paquera.getLogin()).append(";")
                        .append("paquera").append("\n");
            }

            for (User paquerasRecebidas : usuario.getCrushesReceived()) {
                relacoesData.append(usuario.getLogin()).append(";")
                        .append(paquerasRecebidas.getLogin()).append(";")
                        .append("paqueraRecebida").append("\n");
            }

            for (User inimigos : usuario.getEnemy()) {
                relacoesData.append(usuario.getLogin()).append(";")
                        .append(inimigos.getLogin()).append(";")
                        .append("inimigo").append("\n");
            }
        }

        escreverArquivo("relacoes.txt", relacoesData.toString());
    }

    public static void persistirDados(Map<String, User> usuarios, Map<String, Community> comunidades) throws IOException {
        salvarUsuarios(usuarios);
        salvarAmigos(usuarios);
        salvarRecados(usuarios);
        salvarComunidades(comunidades);
        salvarMensagens(usuarios);
        salvarRelacoes(usuarios);
    }

    public static void limparArquivos() throws IOException {
        escreverArquivo("usuarios.txt", "");
        escreverArquivo("amigos.txt", "");
        escreverArquivo("recados.txt", "");
        escreverArquivo("comunidades.txt", "");
        escreverArquivo("mensagens.txt", "");
        // escreverArquivo("relacoes.txt", "");
    }
}
