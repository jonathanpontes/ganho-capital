package programa.model;

public class OperacaoEntrada {

    //Exemplo : [{"operation":"buy", "unit-cost":10.00, "quantity": 10000}, {"operation":"sell", "unit-cost":20.00, "quantity": 5000}]

    // (operation) Tipos de operação pode ser buy ou sell
    private TipoOperacaoEnum tipoOperacao;
    // (unit-cost) Preço unitário da ação
    private double precoUnitario;
    // (quantity) Quantidade de ações
    private int quantidade;

    public OperacaoEntrada(TipoOperacaoEnum tipoOperacao, double precoUnitario, int quantidade) {
        this.tipoOperacao = tipoOperacao;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public TipoOperacaoEnum getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(TipoOperacaoEnum tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
