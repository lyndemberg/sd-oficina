package sd.oficina.customer1.exceptions;

public class AtributoIdInvalidoException extends RuntimeException {

    public AtributoIdInvalidoException() {
        super("Um atributo de Id invalido (null or <= 0) foi passado para o service");
    }

    public AtributoIdInvalidoException(final String message) {
        super(message);
    }
}
