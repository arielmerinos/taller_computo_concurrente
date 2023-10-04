package kas.concurrente;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Por .5 extra, genera 2 test concurrente y uno facil o normal
 * Si no los haces no pasa nada
 */
public class MainTest {
    
    @Test
    void testRegalo(){
        System.out.println("Este es un test de regalo");
        assertTrue(true);
    }
}
