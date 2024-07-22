package service;

import org.junit.Before;
import org.junit.Test;
import programa.model.OperacaoCalculada;
import programa.model.OperacaoEntrada;
import programa.model.OperacaoSaida;
import programa.model.TipoOperacaoEnum;
import programa.service.GerenciadorDeExcecucaoService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GerenciadoreDeExecucaoServiceTest {

    private GerenciadorDeExcecucaoService gerenciadorDeExcecucaoService;
    private List<OperacaoEntrada> operacaoEntradas;
    private OperacaoCalculada operacaoCalculada;

    @Before
    public void setup() {
        gerenciadorDeExcecucaoService = new GerenciadorDeExcecucaoService();
        operacaoEntradas = new ArrayList<>();
        operacaoCalculada = new OperacaoCalculada();
    }


    private OperacaoCalculada criarOperacaoCalculada(List<OperacaoEntrada> operacaoEntradas) {

        operacaoCalculada.setOperacaoEntradas(operacaoEntradas);
        operacaoCalculada.setMediaPonderada(10);

        return operacaoCalculada;
    }


     /* ------------------------------------------------------------------------------------------------------------------------------------------
     Casos de testes do Execicio
       ------------------------------------------------------------------------------------------------------------------------------------------ */

    //Caso #1
    @Test
    public void testGerarOperacaoSaidaCaso1() {

        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 10.0, 100));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 15.0, 50));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 15.0, 50));

        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas);
        List<OperacaoSaida> operacaoSaidas = gerenciadorDeExcecucaoService.gerarOperacaoSaida(operacaoCalculada);

        assertEquals(0, operacaoSaidas.get(0).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(1).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(2).getTaxa(), 0.0);
        assertEquals(3, operacaoSaidas.size(), 0.0);
    }


    //Caso #2
    @Test
    public void testGerarOperacaoSaidaCaso2() {

        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 10.0, 10000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 20.0, 5000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 5.0, 5000));

        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas);
        List<OperacaoSaida> operacaoSaidas = gerenciadorDeExcecucaoService.gerarOperacaoSaida(operacaoCalculada);

        assertEquals(0, operacaoSaidas.get(0).getTaxa(), 0.0);
        assertEquals(10000.0, operacaoSaidas.get(1).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(2).getTaxa(), 0.0);
        assertEquals(3, operacaoSaidas.size(), 0.0);

        double diferencasUltimaOperacao = (operacaoCalculada.getLucrosUltimaOperacao() + (operacaoCalculada.getPrejuizosUltimaOperacao()));
        assertEquals(25000, diferencasUltimaOperacao, 0.0);
    }

    //Case #3
    @Test
    public void testGerarOperacaoSaidaCaso3() {

        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 10.0, 10000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 5.0, 5000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 20.0, 3000));

        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas);
        List<OperacaoSaida> operacaoSaidas = gerenciadorDeExcecucaoService.gerarOperacaoSaida(operacaoCalculada);

        assertEquals(0, operacaoSaidas.get(0).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(1).getTaxa(), 0.0);
        assertEquals(1000, operacaoSaidas.get(2).getTaxa(), 0.0);
        assertEquals(3, operacaoSaidas.size(), 0.0);

        double diferencasUltimaOperacao = (operacaoCalculada.getLucrosUltimaOperacao() + (operacaoCalculada.getPrejuizosUltimaOperacao()));
        assertEquals(5000, diferencasUltimaOperacao, 0.0);
    }

    //Case #4
    @Test
    public void testGerarOperacaoSaidaCaso4() {

        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 10.0, 10000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 25.0, 5000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 15.0, 10000));

        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas);
        List<OperacaoSaida> operacaoSaidas = gerenciadorDeExcecucaoService.gerarOperacaoSaida(operacaoCalculada);

        assertEquals(15, operacaoCalculada.getMediaPonderada(), 0.0);
        assertEquals(3, operacaoSaidas.size(), 0.0);
        assertEquals(0, operacaoSaidas.get(0).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(1).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(2).getTaxa(), 0.0);
    }

    //Case #5
    @Test
    public void testGerarOperacaoSaidaCaso5() {

        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 10.0, 10000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 25.0, 5000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 15.0, 10000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 25.0, 5000));

        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas);
        List<OperacaoSaida> operacaoSaidas = gerenciadorDeExcecucaoService.gerarOperacaoSaida(operacaoCalculada);


        assertEquals(15, operacaoCalculada.getMediaPonderada(), 0.0);
        assertEquals(4, operacaoSaidas.size(), 0.0);
        assertEquals(0, operacaoSaidas.get(0).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(1).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(2).getTaxa(), 0.0);
        assertEquals(10000.00, operacaoSaidas.get(3).getTaxa(), 0.0);
    }


    //Case #6
    @Test
    public void testGerarOperacaoSaidaCaso6() {

        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 10.0, 10000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 2.0, 5000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 20.0, 2000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 20.0, 2000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 25.0, 1000));

        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas);
        List<OperacaoSaida> operacaoSaidas = gerenciadorDeExcecucaoService.gerarOperacaoSaida(operacaoCalculada);


        assertEquals(5, operacaoSaidas.size(), 0.0);
        assertEquals(0, operacaoSaidas.get(0).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(1).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(2).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(3).getTaxa(), 0.0);
        assertEquals(3000, operacaoSaidas.get(4).getTaxa(), 0.0);
    }

    //Case #7
    @Test
    public void testGerarOperacaoSaidaCaso7() {

        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 10.0, 10000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 2.0, 5000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 20.0, 2000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 20.0, 2000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 25.0, 1000));

        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 20.0, 10000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 15.0, 5000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 30.0, 4350));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 30.0, 650));


        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas);
        List<OperacaoSaida> operacaoSaidas = gerenciadorDeExcecucaoService.gerarOperacaoSaida(operacaoCalculada);


        assertEquals(9, operacaoSaidas.size(), 0.0);
        assertEquals(0, operacaoSaidas.get(0).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(1).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(2).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(3).getTaxa(), 0.0);
        assertEquals(3000, operacaoSaidas.get(4).getTaxa(), 0.0);

        assertEquals(0, operacaoSaidas.get(5).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(6).getTaxa(), 0.0);
        assertEquals(3700, operacaoSaidas.get(7).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(8).getTaxa(), 0.0);

    }

    //Case #8
    @Test
    public void testGerarOperacaoSaidaCaso8() {

        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 10.0, 10000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 50.0, 10000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 20.0, 10000));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 50.0, 10000));

        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas);
        List<OperacaoSaida> operacaoSaidas = gerenciadorDeExcecucaoService.gerarOperacaoSaida(operacaoCalculada);


        assertEquals(4, operacaoSaidas.size(), 0.0);
        assertEquals(0, operacaoSaidas.get(0).getTaxa(), 0.0);
        assertEquals(80000, operacaoSaidas.get(1).getTaxa(), 0.0);
        assertEquals(0, operacaoSaidas.get(2).getTaxa(), 0.0);
        assertEquals(60000, operacaoSaidas.get(3).getTaxa(), 0.0);

    }

     /* ------------------------------------------------------------------------------------------------------------------------------------------
        Testes Adicionais
       ------------------------------------------------------------------------------------------------------------------------------------------ */

    @Test
    public void testGerarOperacaoSaidaComTaxaEPrejuizo() {

        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 20.0, 10));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 10.0, 10));

        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas);
        List<OperacaoSaida> operacaoSaidas = gerenciadorDeExcecucaoService.gerarOperacaoSaida(operacaoCalculada);

        assertEquals(0, operacaoSaidas.get(0).getTaxa(), 0.0); // Taxa da primeira operação vem fixa
        assertEquals(0, operacaoSaidas.get(1).getTaxa(), 0.0); // Taxa da segunda operação vem calculada
        assertEquals(2, operacaoSaidas.size(), 0.0); // Para 2 entradas precisamos ter 2 saidas
        assertEquals(-100, operacaoCalculada.getDiferencaLucroPorPrejuizo(), 0.0); // Essa opção não vai render Lucro
    }

    @Test
    public void testGerarOperacaoSaidaSemTaxas() {
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 10.0, 10));
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 20.0, 10));

        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas);
        List<OperacaoSaida> operacaoSaidas = gerenciadorDeExcecucaoService.gerarOperacaoSaida(operacaoCalculada);

        assertEquals(0, operacaoSaidas.get(0).getTaxa(), 0.0); // Taxa da primeira operação vem fixa
        assertEquals(0, operacaoSaidas.get(1).getTaxa(), 0.0); // Taxa da segunda operação vem calculada
        assertEquals(2, operacaoSaidas.size(), 0.0); // Para 2 entradas precisamos ter 2 saidas
    }

    @Test
    public void testGerarOperacaoSaidaComTaxa() {
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.buy, 100.0, 150)); // total 15000.00
        operacaoEntradas.add(new OperacaoEntrada(TipoOperacaoEnum.sell, 200.0, 150)); // total 30000.00
        operacaoCalculada.setMediaPonderada(100);

        OperacaoCalculada operacaoCalculada = criarOperacaoCalculada(operacaoEntradas);
        List<OperacaoSaida> operacaoSaidas = gerenciadorDeExcecucaoService.gerarOperacaoSaida(operacaoCalculada);

        assertEquals(0, operacaoSaidas.get(0).getTaxa(), 0.0); // Taxa da primeira operação vem fixa
        assertEquals(3000, operacaoSaidas.get(1).getTaxa(), 0.0); // Taxa da segunda operação vem calculada
        assertEquals(2, operacaoSaidas.size(), 0.0); // Para 2 entradas precisamos ter 2 saidas
    }

}
