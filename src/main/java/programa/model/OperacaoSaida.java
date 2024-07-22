package programa.model;

public class OperacaoSaida {
    //Exemplo : [{"tax":0}, {"tax":10000}]
    // (tax) Imposto a ser pago
    private double taxa;

    public OperacaoSaida(double taxa) {
        this.taxa = taxa;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }
}
