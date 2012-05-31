/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.bd;

import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joaodias
 */
public class BaseDadosTest {
    private String[][] matriz=new String[2][3];
    public BaseDadosTest() {
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
     * Se a matriz é algo assim A B  
     *                          A B C
     * deverá dar erro, visto que a coluna 3 não tem qualquer valor, gerando assim uma mensagem de erro
      */
    @Test
    public void testVerificarErro() throws SQLException, ClassNotFoundException {
        System.out.println("verificarErro");
        matriz[0][0]="A";
        matriz[0][1]="B";
        matriz[0][2]="";
        matriz[1][0]="A";
        matriz[1][1]="B";
        matriz[1][2]="C";
        IBaseDados instance = BaseDadosFactory.getBD("mysql");
        boolean expResult = false;
        boolean result = instance.verificarErro(matriz);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    
    
    /**
     * Se a matriz é algo assim A B C 
     *                          A B C
     * deverá deixar continuar, visto que as colunas estão todas preenchidas com valores.
      */
    @Test
    public void testVerificarErro_correcto() throws SQLException, ClassNotFoundException {
        System.out.println("verificarErro");
        matriz[0][0]="A";
        matriz[0][1]="B";
        matriz[0][2]="C";
        matriz[1][0]="A";
        matriz[1][1]="B";
        matriz[1][2]="C";
        IBaseDados instance = BaseDadosFactory.getBD("mysql");
        boolean expResult = true;
        boolean result = instance.verificarErro(matriz);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    
       /**
     * Criar Coluna se tem A B C, o resultado esperado para receber a string é 
     * A varchar(120), B varchar(120), C varchar(120)
      */
    @Test
    public void CriarColuna() throws SQLException, ClassNotFoundException {
        System.out.println("verificarErro");
        matriz[0][0]="A";
        matriz[0][1]="B";
        matriz[0][2]="C";
        matriz[1][0]="A";
        matriz[1][1]="B";
        matriz[1][2]="C";
        IBaseDados instance = BaseDadosFactory.getBD("mysql");;
        String expResult ="A varchar(120), B varchar(120), C varchar(120)";
        String result = instance.criarColunas(matriz);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
    
    
    
    
    
    
    

  
}
