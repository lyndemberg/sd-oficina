package sd.oficina.oficinawebapp.person.services;

import org.springframework.stereotype.Service;
import sd.oficina.oficinawebapp.person.grpc.FornecedorClient;
import sd.oficina.shared.model.person.Fornecedor;

import java.util.List;

@Service
public class FornecedorService {

    private FornecedorClient fornecedorClient;

    public FornecedorService() {
        this.fornecedorClient = new FornecedorClient();
    }

    public Fornecedor salvar (Fornecedor fornecedor) {
        return fornecedorClient.salvar(fornecedor);
    }

    public Fornecedor atualizar(Fornecedor fornecedor) {
        return fornecedorClient.atualizar(fornecedor);
    }

    public void deletar(int idDoFornecedor) {
        fornecedorClient.deletar(idDoFornecedor);
    }

    public Fornecedor buscar(int idDoFornecedor) {
        return fornecedorClient.buscar(idDoFornecedor);
    }

    public List<Fornecedor> listar() {
        return fornecedorClient.listar();
    }
}
