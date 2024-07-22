import org.json.JSONArray;
import programa.model.OperacaoCalculada;
import programa.model.OperacaoEntrada;
import programa.model.OperacaoSaida;
import programa.service.GerenciadorDeExcecucaoService;
import programa.utils.JsonConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        GerenciadorDeExcecucaoService gerenciadorDeExcecucaoService = new GerenciadorDeExcecucaoService();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String jsonInput = scanner.nextLine();
            if (jsonInput.trim().isEmpty()) {
                continue;
            }
            processInput(jsonInput, gerenciadorDeExcecucaoService);
        }
    }

    private static void processInput(String jsonInput, GerenciadorDeExcecucaoService gerenciadorDeExcecucaoService) {
        JSONArray jsonArray = new JSONArray(jsonInput);
        List<OperacaoEntrada> operacaoEntradas = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            operacaoEntradas.add(JsonConverter.jsonToOperacaoEntrada(jsonArray.getJSONObject(i)));
        }

        OperacaoCalculada operacaoCalculada = new OperacaoCalculada();
        operacaoCalculada.setOperacaoEntradas(operacaoEntradas);

        List<OperacaoSaida> operacaoSaidas = gerenciadorDeExcecucaoService.gerarOperacaoSaida(operacaoCalculada);

        JSONArray jsonArraySaida = new JSONArray();
        for (OperacaoSaida operacaoSaida : operacaoSaidas) {
            jsonArraySaida.put(JsonConverter.operacaoSaidaToJson(operacaoSaida));
        }

        System.out.println(jsonArraySaida.toString());
    }
}