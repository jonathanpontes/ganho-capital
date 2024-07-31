package utils;

import org.json.JSONObject;
import org.junit.Test;
import programa.model.OperacaoEntrada;
import programa.model.TipoOperacaoEnum;
import programa.utils.JsonConverter;

import static org.junit.Assert.assertEquals;

public class JsonConverterTest {

    @Test
    public void testJsonToOperacaoEntrada() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operation", "buy");
        jsonObject.put("unit-cost", 10.00);
        jsonObject.put("quantity", 100);

        OperacaoEntrada operacaoEntrada = JsonConverter.jsonToOperacaoEntrada(jsonObject);

        assertEquals(TipoOperacaoEnum.buy, operacaoEntrada.getTipoOperacao());
        assertEquals(10.00, operacaoEntrada.getPrecoUnitario(), 0.01);
        assertEquals(100, operacaoEntrada.getQuantidade());
    }
}
