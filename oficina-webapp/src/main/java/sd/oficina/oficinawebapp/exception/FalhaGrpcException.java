package sd.oficina.oficinawebapp.exception;

public class FalhaGrpcException extends Exception{

    public FalhaGrpcException() {
        super("Não foi possível se comunicar com o serviço gRPC");
    }
}
