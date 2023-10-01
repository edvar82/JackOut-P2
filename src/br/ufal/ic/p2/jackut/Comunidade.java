package br.ufal.ic.p2.jackut;

import java.util.ArrayList;

/**
 * <p> Classe que representa uma comunidade. </p>
 */

public class Comunidade {
	private final User create;
    private final String name;
    private final String description;
    private final ArrayList<User> members = new ArrayList<>();
    

    /**
     * <p> Constrói uma nova {@code Comunidade}. </p>
     * <p> Inicializa a lista de membros com o criador da comunidade. </p>
     *
     * @param criador    Criador da comunidade
     * @param nome       Nome da comunidade
     * @param descricao  Descrição da comunidade
     * @param name 
     */
    
    public Comunidade(User create, String name, String description) {
    	this.create = create;
    	this.name = name;
    	this.description = description;
    	this.members.add(create);
    }
    
    
    /**
     * <p> Retorna o nome da comunidade. </p>
     *
     * @return Nome da comunidade
     */
    public String getNome() {
    	return name;
    }
    /**
     * <p> Retorna a descrição da comunidade. </p>
     *
     * @return Descrição da comunidade
     */
    
    public String toString() {
        return this.getNome();
    }

    public String getDescricao() {
    	return description;
    }
    
    public User getCriador() {
    	return create;
    }
    
    public ArrayList<User> getMembros() {
        return members;
    }
    
    public void adicionarMembro(User usuario) {
        this.members.add(usuario);
    }

    public void setMembros(ArrayList<User> membros) {
        this.members.clear();
        this.members.addAll(membros);
    }
    
    public String getMembrosString() {
        return UtilsString.formatArrayList(members);
    }
    
    public void removerMembro(User membro) {
        this.members.remove(membro);
    }
}
;