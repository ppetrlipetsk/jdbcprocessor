import org.junit.Test;

import static org.junit.Assert.*;

public class wwwTest {

    @Test
    public void sumXY() {
        int sumxy=www.sumXY(1,2);
        int exp=3;
        assertEquals(sumxy,exp);
    }
}