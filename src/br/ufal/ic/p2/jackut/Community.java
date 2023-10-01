package br.ufal.ic.p2.jackut;

import java.util.ArrayList;


/**
 * <p> Class representing a community. </p>
 */

public class Community {
    private final User creator;
    private final String name;
    private final String description;
    private final ArrayList<User> members = new ArrayList<>();

    /**
     * <p> Constructs a new {@code Community} in Jackut. </p>
     * <p> Initializes the list of members with the community's creator. </p>
     *
     * @param criador    Creator of the community
     * @param nome       Name of the community
     * @param descricao  Description of the community
     */

    public Community(User criador, String nome, String descricao) {
        this.creator = criador;
        this.name = nome;
        this.description = descricao;
        this.members.add(criador);
    }

    /**
     * <p> Returns the name of the community. </p>
     *
     * @return Name of the community
     */

    public String getNome() {
        return name;
    }

    /**
     * <p> Returns the description of the community. </p>
     *
     * @return Description of the community
     */

    public String getDescricao() {
        return description;
    }

    /**
     * <p> Returns the creator of the community. </p>
     *
     * @return Creator of the community
     */

    public User getCriador() {
        return creator;
    }

    /**
     * <p> Returns the list of members of the community. </p>
     *
     * @return List of members of the community
     */

    public ArrayList<User> getMembros() {
        return members;
    }

    /**
     * Returns the name of the community as a string representation.
     *
     * @return The name of the community
     */
    public String toString() {
        return this.getNome();
    }

    /**
     * Returns a formatted string representation of the community's members using UtilsString.
     *
     * @return Formatted string of community members
     */
    public String getMembrosString() {
        return UtilsString.formatArrayList(members);
    }

    /**
     * Adds a user as a member to the community.
     *
     * @param usuario The user to be added as a member
     */
    public void adicionarMembro(User usuario) {
        this.members.add(usuario);
    }

    /**
     * Sets the members of the community based on the provided ArrayList of users.
     *
     * @param membros The ArrayList of users to set as members of the community
     */
    public void setMembros(ArrayList<User> membros) {
        this.members.clear();
        this.members.addAll(membros);
    }

    /**
     * Sends a message to all members of the community.
     *
     * @param mensagem The message to be sent to community members
     */
    public void enviarMensagem(Messages mensagem) {
        for (User membro : members) {
            membro.receberMensagem(mensagem);
        }
    }

    /**
     * Removes a member from the community.
     *
     * @param membro The user to be removed from the community
     */
    public void removerMembro(User membro) {
        this.members.remove(membro);
    }
}
