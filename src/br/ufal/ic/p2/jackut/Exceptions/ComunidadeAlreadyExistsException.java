package br.ufal.ic.p2.jackut.Exceptions;

public class ComunidadeAlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ComunidadeAlreadyExistsException() {
		super("Comunidade com esse nome já existe.");
	}
}
