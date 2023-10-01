package br.ufal.ic.p2.jackut.Exceptions;

/**
 * <p> Exception to be thrown when there is no errand. </p>
    */

public class DontHaveErrandExcpetion extends RuntimeException {
    public DontHaveErrandExcpetion() {
        super("Não há recados.");
    }
}
