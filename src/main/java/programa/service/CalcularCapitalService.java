package programa.service;

import programa.model.OperacaoCalculada;
import programa.model.OperacaoEntrada;
import programa.model.TipoOperacaoEnum;

public class CalcularCapitalService {


    public double calcularTaxaPorOperacao(OperacaoEntrada operacaoAnterior, OperacaoEntrada operacaoAtual, OperacaoCalculada operacaoCalculada) {

        if (operacaoAtual.getTipoOperacao().equals(TipoOperacaoEnum.sell)) {
            // Subtrai a quantidade de ações vendidas do total de ações compradas
            operacaoCalculada.setTotalAcoesCompradas(operacaoCalculada.getTotalAcoesCompradas() - operacaoAtual.getQuantidade());
        }

        if (operacaoAtual.getTipoOperacao().equals(TipoOperacaoEnum.buy)) {

            double novaMediaPonderada = calcularNovaMediaPonderada(
                    operacaoAnterior.getQuantidade(),
                    operacaoCalculada.getMediaPonderada(),
                    operacaoAtual.getQuantidade(),
                    operacaoAtual.getPrecoUnitario()
            );

            //Se o total de ações acabar significa que a média ponderada precisar ser calculada novamente apra a nova compra
            if (operacaoCalculada.getTotalAcoesCompradas() == 0) {

                // Zerando variaveis para a nova compra
                operacaoCalculada.setLucrosUltimaOperacao(0);
                operacaoCalculada.setPrejuizosUltimaOperacao(0);
                operacaoCalculada.setDiferencaLucroPorPrejuizo(0);
                operacaoCalculada.setMediaPonderada(operacaoAtual.getPrecoUnitario());

            } else {
                operacaoCalculada.setMediaPonderada(novaMediaPonderada);
            }

            // Soma a quantidade de ações compradas do total de ações compradas
            operacaoCalculada.setTotalAcoesCompradas(operacaoCalculada.getTotalAcoesCompradas() + operacaoAtual.getQuantidade());
        }

        double lucroAtual = calcularLucroOperacao(
                operacaoAtual,
                operacaoCalculada
        );

        double valorTotalOperacao = (operacaoAtual.getPrecoUnitario() * operacaoAtual.getQuantidade());

        return calcularImpostoPago(lucroAtual, operacaoAtual.getTipoOperacao(), operacaoCalculada.getMediaPonderada(), operacaoAtual.getPrecoUnitario(), valorTotalOperacao);
    }


    public double calcularNovaMediaPonderada(int quantidadeAcoesAtual, double mediaPonderadatual, int quantidadeAcoesCompradas, double valorCompra) {
        //nova-media-ponderada = ((5 x 20.00) + (5 x 10.00)) / (5 + 5) = 15.00
        return ((quantidadeAcoesAtual * mediaPonderadatual) + (quantidadeAcoesCompradas * valorCompra)) / (quantidadeAcoesAtual + quantidadeAcoesCompradas);
    }


    public double calcularLucroOperacao(OperacaoEntrada operacaoAtual, OperacaoCalculada operacaoCalculada) {
        double custoTotalCompra = calcularCustoTotalCompra(operacaoAtual, operacaoCalculada);
        double valorTotalVenda = calcularValorTotalVenda(operacaoAtual);
        double lucro = calcularLucro(custoTotalCompra, valorTotalVenda);

        atualizarLucrosOuPrejuizos(operacaoCalculada, lucro);

        return atualizarDiferencaLucroPorPrejuizo(operacaoAtual, operacaoCalculada, lucro);
    }

    private double calcularCustoTotalCompra(OperacaoEntrada operacaoAtual, OperacaoCalculada operacaoCalculada) {
        return operacaoCalculada.getMediaPonderada() * operacaoAtual.getQuantidade();
    }

    private double calcularValorTotalVenda(OperacaoEntrada operacaoAtual) {
        return operacaoAtual.getPrecoUnitario() * operacaoAtual.getQuantidade();
    }

    private double calcularLucro(double custoTotalCompra, double valorTotalVenda) {
        return valorTotalVenda - custoTotalCompra;
    }

    private void atualizarLucrosOuPrejuizos(OperacaoCalculada operacaoCalculada, double lucro) {
        if (lucro > 0) {
            operacaoCalculada.setLucrosUltimaOperacao(lucro);
        } else {
            operacaoCalculada.setPrejuizosUltimaOperacao(lucro);
        }
    }

    private double atualizarDiferencaLucroPorPrejuizo(OperacaoEntrada operacaoAtual, OperacaoCalculada operacaoCalculada, double lucro) {
        double diferencaLucroPorPrejuizo = operacaoCalculada.getDiferencaLucroPorPrejuizo() + lucro;
        operacaoCalculada.setDiferencaLucroPorPrejuizo(diferencaLucroPorPrejuizo);
        return diferencaLucroPorPrejuizo;
    }

    public double calcularImpostoPago(double lucro, TipoOperacaoEnum tipoOperacao, double precoMedioPonderadoCompra, double valorCompra, double valorTotalOperacao) {

        if (TipoOperacaoEnum.sell.equals(tipoOperacao) && valorCompra > precoMedioPonderadoCompra && valorTotalOperacao > 20000 && lucro > 0) {
            double imposto = (lucro * 0.20);
            return imposto;
        }
        return 0;
    }

}
