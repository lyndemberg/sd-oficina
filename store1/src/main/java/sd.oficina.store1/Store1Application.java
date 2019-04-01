package sd.oficina.store1;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.model.store.Servico;
import sd.oficina.store1.dao.EstoqueDAO;
import sd.oficina.store1.dao.ServicoDAO;
import sd.oficina.store1.grpc.ServicoImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

class Store1Application {
    public static void main(String[] args) {

        Server server = ServerBuilder.forPort(2223)
                .addService(new ServicoImpl())
                .build();

        System.out.println("Iniciando Server - Store 1");
        try {
            server.start();
            server.awaitTermination();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}