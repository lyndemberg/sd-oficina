package sd.oficina.oficinawebapp.person.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sd.oficina.oficinawebapp.person.services.CidadeService;
import sd.oficina.oficinawebapp.person.services.EstadoService;
import sd.oficina.shared.model.person.Cidade;
import sd.oficina.shared.model.person.Estado;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class StartEstadosECidades {

    @Bean
    public void start() {

//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//
//        EstadoService estadoService = new EstadoService();
//        CidadeService cidadeService = new CidadeService();
//
//        Gson gson = new Gson();
//
//        try {
//
//            if (estadoService.listar().isEmpty()) {
//
//                BufferedReader bufferedReader = new BufferedReader(new FileReader("oficina-webapp/estados-cidades.json"));
//
//                //Converte String JSON para objeto Java
//                Estados estados = gson.fromJson(bufferedReader, Estados.class);
//
//                Estado estado = new Estado();
//                Cidade cidade = new Cidade();
//
//                System.out.println("INSERINDO ESTADOS E CIDADES, POR FAVOR AGUARDE!");
//
//                Runnable runnable = () -> {
//                    for (EstadoJSON estadoJSON : estados.getEstados()) {
//                        estado.setNome(estadoJSON.getNome());
//                        Estado estadoParaSalvar = estadoService.salvar(estado);
//                        for (String cidadeJSON : estadoJSON.getCidades()) {
//                            cidade.setNome(cidadeJSON);
//                            cidade.setEstado(estadoParaSalvar);
//                            cidadeService.salvar(cidade);
//                        }
//                    }
//                };
//
//                System.out.println("INSERÇÕES DE ESTADOS E CIDADES CONCLUÍDAS!");
//
//                executorService.execute(runnable);
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
