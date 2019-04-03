package sd.oficina.oficinawebapp.person.services;

import org.springframework.stereotype.Service;
import sd.oficina.oficinawebapp.person.grpc.CidadeClient;
import sd.oficina.shared.model.person.Cidade;

import java.util.List;

@Service
public class CidadeService {

    private CidadeClient cidadeClient;

    public CidadeService () {
        this.cidadeClient = new CidadeClient();
    }

    public Cidade salvar (Cidade cidade) {
        return cidadeClient.salvar(cidade);
    }

    public Cidade atualizar(Cidade cidade) {
        return cidadeClient.atualizar(cidade);
    }

    public void deletar(int idDaCidade) {
        cidadeClient.deletar(idDaCidade);
    }

    public Cidade buscar(int idDaCidade) {
        return cidadeClient.buscar(idDaCidade);
    }

    public List<Cidade> listar() {
        return cidadeClient.listar();
    }
}
