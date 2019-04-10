package sd.oficina.person1.config;

import com.google.gson.Gson;
import sd.oficina.person1.Person1Application;
import sd.oficina.person1.daos.CidadeDao;
import sd.oficina.person1.daos.EstadoDao;
import sd.oficina.shared.model.person.Cidade;
import sd.oficina.shared.model.person.Estado;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartEstadosECidades {

    public static void start() {

        EstadoDao estadoDao = new EstadoDao();
        CidadeDao cidadeDao = new CidadeDao();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Gson gson = new Gson();

        try {
            if (estadoDao.listar().isEmpty()) {

                File file = new File(Person1Application.class.getClassLoader().getResource("estados-cidades.json").toURI());

                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

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

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}