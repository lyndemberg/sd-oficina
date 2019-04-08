package sd.oficina.order1.exception;

public class ErroEmissaoOrcamento extends Exception{

    public ErroEmissaoOrcamento() {
        super("Não foi possível gerar o arquivo do orçamento");
    }
}
