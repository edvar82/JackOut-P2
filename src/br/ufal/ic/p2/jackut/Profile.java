package br.ufal.ic.p2.jackut;

import java.util.HashMap;
import java.util.Map;

import br.ufal.ic.p2.jackut.Exceptions.AttributeNotFilledException;

/**
 * <p> Class representing a user's profile. </p>
 */
public class Profile {
    private final Map<String, String> attributes = new HashMap<>();

    /**
     * <p> Adds an attribute to the profile. </p>
     *
     * @param key   Name of the attribute.
     * @param value Value of the attribute.
     */
    public void setAtributo(String key, String value) {
        this.attributes.put(key, value);
    }

    /**
     * <p> Returns the value of a profile attribute. </p>
     *
     * @param key The attribute key.
     * @return The attribute's value.
     *
     * @throws AttributeNotFilledException Exception indicating that the attribute is not filled.
     */
    public String getAtributo(String key) throws AttributeNotFilledException {
        if (this.attributes.containsKey(key)) {
            return this.attributes.get(key);
        } else {
            throw new AttributeNotFilledException();
        }
    }

    /**
     * <p> Returns all the attributes of the profile. </p>
     *
     * @return Map containing all the attributes of the profile.
     */
    public Map<String, String> getAtributos() {
        return this.attributes;
    }
}
