package br.ufal.ic.p2.jackut.Exceptions;

public class ComunidadeAlreadyExistsException extends RuntimeException{
	public ComunidadeAlreadyExistsException() {
		super("Comunidade com esse nome já existe.");
	}
}
