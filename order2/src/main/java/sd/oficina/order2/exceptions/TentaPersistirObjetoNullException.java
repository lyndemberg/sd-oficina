package sd.oficina.order2.exceptions;

public class TentaPersistirObjetoNullException extends RuntimeException {

    public TentaPersistirObjetoNullException() {
        super("Nao e possivel persistir um objeto nulo");
    }

    public TentaPersistirObjetoNullException(final String message) {
        super(message);
    }
}
