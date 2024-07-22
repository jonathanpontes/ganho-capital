package service;

import org.junit.Before;
import org.junit.Test;
import programa.model.OperacaoCalculada;
import programa.model.OperacaoEntrada;
import programa.model.TipoOperacaoEnum;
import programa.service.CalcularCapitalService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CalcularCapitalServiceTest {


    private CalcularCapitalService calcularCapitalService;
    private List<OperacaoEntrada> operacaoEntradas;
    private OperacaoCalculada operacaoCalculada;

    @Before
    public void setup() {

        calcularCapitalService = new CalcularCapitalService();
        operacaoEntradas = new ArrayList<>();
        operacaoCalculada = new OperacaoCalculada();
    }

    private OperacaoCalculada criarOperacaoCalculada(List<OperacaoEntrada> operacaoEntradas, double lucroOuPrejuizo, double mediaPonderada) {

        operacaoCalculada.setOperacaoEntradas(operacaoEntradas);
        operacaoCalculada.setMediaPonderada(mediaPonderada);
        operacaoCalculada.setDiferencaLucroPorPrejuizo(lucroOuPrejuizo);

        return operacaoCalculada;
    }


    @Test
    public void testCalcularTaxaPorOperacaoMenorQue2000() {

        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 10.0, 10));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 20.0, 10));
        criarOperacaoCalculada(operacaoEntradas, 0, 10);

        // Simulando primeira Operacao de compra seguida de venda
        double imposto = calcularCapitalService.calcularTaxaPorOperacao(
                operacaoCalculada.getOperacaoEntradas().get(0),
                operacaoCalculada.getOperacaoEntradas().get(1),
                operacaoCalculada
        );

        assertEquals(0, imposto, 0.0);
    }

    @Test
    public void testCalcularTaxaPorOperacaoMaiorQue20000() {

        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 100.0, 150)); //total 15000.00
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 200.0, 150)); //total 30000.00
        criarOperacaoCalculada(operacaoEntradas, 0, 100);

        // Simulando primeira Operacao de compra seguida de venda
        double imposto = calcularCapitalService.calcularTaxaPorOperacao(
                operacaoCalculada.getOperacaoEntradas().get(0),
                operacaoCalculada.getOperacaoEntradas().get(1),
                operacaoCalculada
        );

        //Diferença entre a venda e a compra = 30000.00 - 15000.00 = 15000.00
        //Imposto = 15000.00 * 0.20 = 3000.00
        assertEquals(3000.0, imposto, 0.0);
    }

    @Test
    public void testCalcularLucroOperacao() {

        //Não estava devendo nada
        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas, 0, 10);
        OperacaoEntrada operacaoEntradaVenda = new OperacaoEntrada(TipoOperacaoEnum.sell, 20.0, 10);

        double lucro = calcularCapitalService.calcularLucroOperacao(operacaoEntradaVenda, operacaoCalculada);
        assertEquals(100.0, lucro, 0.0);
    }

    @Test
    public void testCalcularPrejuizosOperacao() {

        //Não estava devendo nada
        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas, 0, 10);
        OperacaoEntrada operacaoEntradaVenda = new OperacaoEntrada(TipoOperacaoEnum.sell, 5.0, 10);

        double lucro = calcularCapitalService.calcularLucroOperacao(operacaoEntradaVenda, operacaoCalculada);
        assertEquals(-50.0, lucro, 0.0);
    }

    @Test
    public void testCalcularPrejuizosComVendaInferiorQueCompra() {

        //Estava devendo 50 e comecei a dever mais 100
        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas, -50.0, 20);
        OperacaoEntrada operacaoEntradaVenda = new OperacaoEntrada(TipoOperacaoEnum.sell, 10.0, 10);

        double lucro = calcularCapitalService.calcularLucroOperacao(operacaoEntradaVenda, operacaoCalculada);
        assertEquals(-150.0, lucro, 0.0);
    }


    @Test
    public void testCalcularPrejuizosComVendaSemLucros() {

        //Estava devendo 50 mas a operação não deu Lucro então continuei devendo 50
        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas, -50.0, 10);
        OperacaoEntrada operacaoEntradaVenda = new OperacaoEntrada(TipoOperacaoEnum.sell, 10.0, 5);

        double lucro = calcularCapitalService.calcularLucroOperacao(operacaoEntradaVenda, operacaoCalculada);
        assertEquals(-50.0, lucro, 0.0);
    }

    @Test
    public void testCalcularPrejuizosComVendaSuperiorQueCompra() {

        //Estava devendo 200 e comecei a dever mais 100 pois tive um lucro de 100
        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas, -200.0, 10);
        OperacaoEntrada operacaoEntradaVenda = new OperacaoEntrada(TipoOperacaoEnum.sell, 20.0, 10);

        double lucro = calcularCapitalService.calcularLucroOperacao(operacaoEntradaVenda, operacaoCalculada);
        assertEquals(-100, lucro, 0.0);
    }

    @Test
    public void testCalcularPrejuizosZeradosComVendaInferiorQueCompra() {

        //Estava devendo 50 e comecei a dever mais 100
        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas, 100.0, 20);
        OperacaoEntrada operacaoEntradaVenda = new OperacaoEntrada(TipoOperacaoEnum.sell, 10.0, 10);

        double lucro = calcularCapitalService.calcularLucroOperacao(operacaoEntradaVenda, operacaoCalculada);
        assertEquals(0, lucro, 0.0);
    }


    @Test
    public void testCalcularImpostoPagoOperacaoSuperiorA20000() {
        double lucro = 100.0;
        double valorTotalOperacao = 30000.0;
        double imposto = calcularCapitalService.calcularImpostoPago(lucro, TipoOperacaoEnum.sell, 100.0, 200.0, valorTotalOperacao);
        assertEquals(20.0, imposto, 0.0);
    }

    @Test
    public void testCalcularImpostoPagoOperacaoIgualA2000() {
        double lucro = 100.0;
        double valorTotalOperacao = 2000.0;
        double imposto = calcularCapitalService.calcularImpostoPago(lucro, TipoOperacaoEnum.sell, 10.0, 20.0, valorTotalOperacao);
        assertEquals(0, imposto, 0.0);
    }

    @Test
    public void testCalcularImpostoPagoOperacaoInferiorA2000() {
        double lucro = 100.0;
        double valorTotalOperacao = 1000.0;
        double imposto = calcularCapitalService.calcularImpostoPago(lucro, TipoOperacaoEnum.sell, 10.0, 20.0, valorTotalOperacao);
        assertEquals(0, imposto, 0.0);
    }

    @Test
    public void testCalcularNovaMediaPonderada() {
        int quantidadeAcoesAtual = 5;
        double mediaPonderadatual = 20.0;
        int quantidadeAcoesCompradas = 5;
        double valorCompra = 10.0;
        double novaMediaPonderada = calcularCapitalService.calcularNovaMediaPonderada(quantidadeAcoesAtual, mediaPonderadatual, quantidadeAcoesCompradas, valorCompra);
        assertEquals(15.0, novaMediaPonderada, 0.0);
    }

}