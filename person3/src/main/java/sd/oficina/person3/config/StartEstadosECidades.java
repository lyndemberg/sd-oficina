package sd.oficina.person3.config;

import com.google.gson.Gson;
import sd.oficina.person3.Person3Application;
import sd.oficina.person3.daos.CidadeDao;
import sd.oficina.person3.daos.EstadoDao;
import sd.oficina.shared.model.person.Cidade;
import sd.oficina.shared.model.person.Estado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartEstadosECidades {

    public static void start() {

        EstadoDao estadoDao = new EstadoDao();
        CidadeDao cidadeDao = new CidadeDao();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Gson gson = new Gson();

        if (estadoDao.listar().isEmpty()) {

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(
                                    Person3Application
                                            .class
                                            .getClassLoader()
                                            .getResourceAsStream("estados-cidades.json")
                            )
                    )
            );

            //Converte String JSON para objeto Java
            Estados estados = gson.fromJson(bufferedReader, Estados.class);

            System.out.println("INSERINDO ESTADOS E CIDADES, POR FAVOR AGUARDE!");

            Runnable runnable = () -> {
                for (EstadoJSON estadoJSON : estados.getEstados()) {
                    Estado estado = new Estado();
                    estado.setNome(estadoJSON.getNome());
                    Estado estadoParaSalvar = estadoDao.salvar(estado);
                    for (String cidadeJSON : estadoJSON.getCidades()) {
                        Cidade cidade = new Cidade();
                        cidade.setNome(cidadeJSON);
                        cidade.setEstado(estadoParaSalvar);
                        cidadeDao.salvar(cidade);
                    }
                }
            };

            System.out.println("INSERÇÕES DE ESTADOS E CIDADES CONCLUÍDAS!");

            executorService.execute(runnable);

        }

    }
}