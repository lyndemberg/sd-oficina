package sd.oficina.customer1.exceptions;

import com.sun.istack.internal.NotNull;

public class TentaPersistirObjetoNullException extends RuntimeException {

    public TentaPersistirObjetoNullException() {
        super("Nao e possivel persistir um objeto nulo");
    }

    public TentaPersistirObjetoNullException(@NotNull final String message) {
        super(message);
    }
}
