/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Tiago
 */
public class EncryptorTest {
    
    public EncryptorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of encrypt method, of class Encryptor.
     */
    @Test
    public void testEncryptAtoC() {
        System.out.println("encrypt");
        String target = "A";
        String expResult = "C";
        String result = Encryptor.encrypt(target);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of decrypt method, of class Encryptor.
     */
    @Test
    public void testDecryptCtoA() {
        System.out.println("decrypt");
        String target = "C";
        String expResult = "A";
        String result = Encryptor.decrypt(target);
        assertEquals(expResult, result);
        
    }
}
