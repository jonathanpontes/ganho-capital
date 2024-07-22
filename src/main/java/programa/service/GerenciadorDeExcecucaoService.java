package programa.service;

import programa.model.OperacaoCalculada;
import programa.model.OperacaoEntrada;
import programa.model.OperacaoSaida;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeExcecucaoService {

    CalcularCapitalService calcularCapitalService = new CalcularCapitalService();

    public List<List<OperacaoSaida>> gerarListaOperacaoSaida(List<OperacaoCalculada> operacaoCalculadas) {
        List<List<OperacaoSaida>> listaDeOperacaoSaidas = new ArrayList<List<OperacaoSaida>>();

        for (OperacaoCalculada operacaoCalculada : operacaoCalculadas) {
            listaDeOperacaoSaidas.add(gerarOperacaoSaida(operacaoCalculada));
        }
        return listaDeOperacaoSaidas;
    }

    public List<OperacaoSaida> gerarOperacaoSaida(OperacaoCalculada operacaoCalculada) {

        List<OperacaoSaida> operacaoSaidas = new ArrayList<OperacaoSaida>();

        //Primeira operação não tem taxa pois representa sempre a primeira compra
        operacaoSaidas.add(new OperacaoSaida(0));

        //A media ponderada da primeira operação é o preço unitário da primeira compra
        operacaoCalculada.setMediaPonderada(operacaoCalculada.getOperacaoEntradas().get(0).getPrecoUnitario());
        operacaoCalculada.setTotalAcoesCompradas(operacaoCalculada.getOperacaoEntradas().get(0).getQuantidade());

        for (int i = 0; i < operacaoCalculada.getOperacaoEntradas().size() - 1; i++) {
            //Gerando variaveis para melhorar a leitura
            OperacaoEntrada operacaoAnterior = operacaoCalculada.getOperacaoEntradas().get(i);
            OperacaoEntrada operacaoAtual = operacaoCalculada.getOperacaoEntradas().get(i + 1);

            double taxa = calcularCapitalService.calcularTaxaPorOperacao(
                    operacaoAnterior,
                    operacaoAtual,
                    operacaoCalculada);

            OperacaoSaida operacaoSaida = new OperacaoSaida(taxa);
            operacaoSaidas.add(operacaoSaida);

        }

        return operacaoSaidas;
    }
}
