package sd.oficina.oficinawebapp.identity;

public class CreationIdentityFailException extends Exception {
    public CreationIdentityFailException(String entityName) {
        super("Não foi possível gerar um novo ID para a entidade " + entityName);
    }
}
