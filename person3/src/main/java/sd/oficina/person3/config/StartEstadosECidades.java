package sd.oficina.person3.config;

import com.google.gson.Gson;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.person3.Person3Application;
import sd.oficina.person3.cache.ConnectionFactory;
import sd.oficina.person3.daos.CidadeDao;
import sd.oficina.person3.daos.EstadoDao;
import sd.oficina.shared.model.person.Cidade;
import sd.oficina.shared.model.person.Estado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

public class StartEstadosECidades {

    public static void start() {

        EstadoDao estadoDao = new EstadoDao();
        CidadeDao cidadeDao = new CidadeDao();

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

            // Inicializa Objetos do Cache para Estado
            final RedisTemplate<String, Estado> redisTemplateEstado = ConnectionFactory.getRedisTemplate();
            final HashOperations<String, Object, Estado> hashOperationsEstado = redisTemplateEstado.opsForHash();

            // Inicializa Objetos do Cache para Cidade
            final RedisTemplate<String, Cidade> redisTemplateCidade = ConnectionFactory.getRedisTemplate();
            final HashOperations<String, Object, Cidade> hashOperationsCidade = redisTemplateCidade.opsForHash();

            new Thread(() -> {

                System.out.println("INSERINDO ESTADOS E CIDADES, POR FAVOR AGUARDE!");

                estados.getEstados().forEach(estadoJSON -> {
                    Estado estado = new Estado();

                    estado.setNome(estadoJSON.getNome());
                    Estado estadoSalvo = estadoDao.salvar(estado);

                    hashOperationsEstado.put(Estado.class.getSimpleName(), estadoSalvo.getId(), estadoSalvo);

                    for (String cidadeJSON : estadoJSON.getCidades()) {
                        Cidade cidade = new Cidade();

                        cidade.setNome(cidadeJSON);
                        cidade.setEstado(estadoSalvo);

                        Cidade cidadeSalva = cidadeDao.salvar(cidade);

                        hashOperationsCidade.put(Cidade.class.getSimpleName(), cidadeSalva.getId(), cidadeSalva);
                    }
                });

                System.out.println("INSERÇÕES DE ESTADOS E CIDADES CONCLUÍDAS!");

            }).start();

        }

    }

}