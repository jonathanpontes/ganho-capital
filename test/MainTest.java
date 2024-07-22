import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testMainComEntradaUnica() {
        String entrada = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}]\n";

        String saida = "[{\"tax\":0},{\"tax\":10000}]";

        System.setIn(new ByteArrayInputStream(entrada.getBytes()));
        Main.main(new String[]{});

        assertEquals(saida, outContent.toString().trim());
    }


   @Test
    public void testMainCom2ListasDeEntrada() {
        String entrada =
                "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}]\n" +
                "[{\"operation\":\"buy\", \"unit-cost\":20.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":10.00, \"quantity\": 5000}]\n";

        String saida =
                "[{\"tax\":0},{\"tax\":10000}]\r\n" +
                "[{\"tax\":0},{\"tax\":0}]";

        System.setIn(new ByteArrayInputStream(entrada.getBytes()));
        Main.main(new String[]{});

        assertEquals(saida, outContent.toString().trim());
    }

    /* ------------------------------------------------------------------------------------------------------------------------------------------
     Caso de testes do Execicio
       ------------------------------------------------------------------------------------------------------------------------------------------ */

    //Case #1 + Case #2
    @Test
    public void testMainCaso1eCaso2() {
        String entrada =
      "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100}, {\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}, {\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}]\n" +
     "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}, {\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\": 5000}]\n";

        String saida =
                "[{\"tax\":0},{\"tax\":0},{\"tax\":0}]\r\n" +
                "[{\"tax\":0},{\"tax\":10000},{\"tax\":0}]";

        System.setIn(new ByteArrayInputStream(entrada.getBytes()));
        Main.main(new String[]{});

        assertEquals(saida, outContent.toString().trim());
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}