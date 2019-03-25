package br.edu.ifpb;

import br.edu.ifpb.grpc.FabricanteImpl;
import br.edu.ifpb.grpc.VeiculoImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        Server server = ServerBuilder.forPort(2222)
                .addService(new FabricanteImpl())
                .addService(new VeiculoImpl())
                .build();

        System.out.println("Iniciando Server - Customer 2");
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
