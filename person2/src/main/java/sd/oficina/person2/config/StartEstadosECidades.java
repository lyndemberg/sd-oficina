package sd.oficina.person2.config;

import com.google.gson.Gson;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.person2.Person2Application;
import sd.oficina.person2.dao.CidadeDao;
import sd.oficina.person2.dao.EstadoDao;
import sd.oficina.person2.infra.cache.ConnectionFactory;
import sd.oficina.shared.model.person.Cidade;
import sd.oficina.shared.model.person.Estado;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StartEstadosECidades {

    public static void start() {

        EstadoDao estadoDao = new EstadoDao();
        CidadeDao cidadeDao = new CidadeDao();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Gson gson = new Gson();

        if (estadoDao.getAll().isEmpty()) {

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(
                                    Person2Application
                                            .class
                                            .getClassLoader()
                                            .getResourceAsStream("estados-cidades.json")
                            )
                    )
            );

            //Converte String JSON para objeto Java
            Estados estados = gson.fromJson(bufferedReader, Estados.class);

            // Inicializa a lista de Objetos para o cache
            final List<Estado> estadosToCache = new ArrayList<>();
            final List<Cidade> cidadesToCache = new ArrayList<>();

            System.out.println("INSERINDO ESTADOS E CIDADES, POR FAVOR AGUARDE!");

            // Define runnable de persistencia em DB
            Runnable runnableDB = () -> {
                for (EstadoJSON estadoJSON : estados.getEstados()) {
                    Estado estado = new Estado();

                    estado.setNome(estadoJSON.getNome());
                    Estado estadoParaSalvar = estadoDao.salvar(estado);

                    estadosToCache.add(estadoParaSalvar);

                    for (String cidadeJSON : estadoJSON.getCidades()) {
                        Cidade cidade = new Cidade();

                        cidade.setNome(cidadeJSON);
                        cidade.setEstado(estadoParaSalvar);

                        cidadesToCache.add(
                                cidadeDao.salvar(cidade)
                        );
                    }

                }

            };

            System.out.println("INSERÇÕES DE ESTADOS E CIDADES CONCLUÍDAS!");

            executorService.execute(runnableDB);

            // Inicializa Objetos do Cache para Estado
            final RedisTemplate<String, Estado> redisTemplateEstado = ConnectionFactory.getRedisTemplate();
            final HashOperations<String, Object, Estado> hashOperationsEstado = redisTemplateEstado.opsForHash();

            // Inicializa Objetos do Cache para Cidade
            final RedisTemplate<String, Cidade> redisTemplateCidade = ConnectionFactory.getRedisTemplate();
            final HashOperations<String, Object, Cidade> hashOperationsCidade = redisTemplateCidade.opsForHash();

            try {
                executorService.awaitTermination(1, TimeUnit.MINUTES);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            System.out.println("INICIANDO INSERÇAO DE ESTADOS E CIDADES NO CACHE");

            estadosToCache.forEach(estado -> executorService.execute(() -> {
                System.out.println(estado);
                hashOperationsEstado.put(Estado.class.getSimpleName(), estado.getId(), estado);
            }));
            cidadesToCache.forEach(cidade -> executorService.execute(() -> {
                System.out.println(cidade);
                hashOperationsCidade.put(Cidade.class.getSimpleName(), cidade.getId(), cidade);
            }));

            try {
                executorService.awaitTermination(1, TimeUnit.MINUTES);
                System.out.println("INSERÇOES NO CACHE DE ESTADOS E CIDADES FORAM CONCLUIDAS");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }

    }
}