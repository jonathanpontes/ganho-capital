package programa.utils;

import org.json.JSONObject;
import programa.model.OperacaoEntrada;
import programa.model.OperacaoSaida;
import programa.model.TipoOperacaoEnum;

public class JsonConverter {

    public static OperacaoEntrada jsonToOperacaoEntrada(JSONObject jsonObject) {
        TipoOperacaoEnum tipoOperacao = TipoOperacaoEnum.valueOf(jsonObject.getString("operation"));
        double precoUnitario = jsonObject.getDouble("unit-cost");
        int quantidade = jsonObject.getInt("quantity");
        return new OperacaoEntrada(tipoOperacao, precoUnitario, quantidade);
    }

    public static JSONObject operacaoSaidaToJson(OperacaoSaida operacaoSaida) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tax", operacaoSaida.getTaxa());
        return jsonObject;
    }

    public static JSONObject operacaoEntradaToJson(OperacaoEntrada operacaoEntrada) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operation", operacaoEntrada.getTipoOperacao().toString());
        jsonObject.put("unit-cost", operacaoEntrada.getPrecoUnitario());
        jsonObject.put("quantity", operacaoEntrada.getQuantidade());
        return jsonObject;
    }

    public static OperacaoSaida jsonToOperacaoSaida(JSONObject jsonObject) {
        double taxa = jsonObject.getDouble("tax");
        return new OperacaoSaida(taxa);
    }
}