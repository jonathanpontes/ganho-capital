package programa.model;

import java.util.List;

public class OperacaoCalculada {

    private List<OperacaoEntrada> operacaoEntradas;
    private double mediaPonderada;
    private double diferencaLucroPorPrejuizo;
    private double totalAcoesCompradas;
    private double lucrosUltimaOperacao;
    private double prejuizosUltimaOperacao;


    public double getPrejuizosUltimaOperacao() {
        return prejuizosUltimaOperacao;
    }

    public void setPrejuizosUltimaOperacao(double prejuizosUltimaOperacao) {
        this.prejuizosUltimaOperacao = prejuizosUltimaOperacao;
    }

    public double getLucrosUltimaOperacao() {
        return lucrosUltimaOperacao;
    }

    public void setLucrosUltimaOperacao(double lucrosUltimaOperacao) {
        this.lucrosUltimaOperacao = lucrosUltimaOperacao;
    }

    public double getTotalAcoesCompradas() {
        return totalAcoesCompradas;
    }

    public void setTotalAcoesCompradas(double totalAcoesCompradas) {
        this.totalAcoesCompradas = totalAcoesCompradas;
    }

    public List<OperacaoEntrada> getOperacaoEntradas() {
        return operacaoEntradas;
    }

    public void setOperacaoEntradas(List<OperacaoEntrada> operacaoEntradas) {
        this.operacaoEntradas = operacaoEntradas;
    }

    public double getMediaPonderada() {
        return mediaPonderada;
    }

    public void setMediaPonderada(double mediaPonderada) {
        this.mediaPonderada = mediaPonderada;
    }

    public double getDiferencaLucroPorPrejuizo() {
        return diferencaLucroPorPrejuizo;
    }

    public void setDiferencaLucroPorPrejuizo(double diferencaLucroPorPrejuizo) {
        this.diferencaLucroPorPrejuizo = diferencaLucroPorPrejuizo;
    }
}
