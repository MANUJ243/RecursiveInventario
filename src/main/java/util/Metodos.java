package util;

import java.io.BufferedWriter;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author mmorende
 */
public class Metodos {
    private static ChromeDriver driver;
    private static boolean bandera = false, bandera2 = false;
    private static int index;
    private static BufferedWriter out;
    ArrayList<String> componentes;
    
    public static void initDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mmorende\\Desktop\\chromedriver.exe");
        driver = new ChromeDriver();
        
        //for (int i = 0; i < 10; i++) {
            
        //}
    }
    
    public static void buscarComponente(String comp){
        driver.get("https://tpw.bankinter.bk/www/es-es/cgi/int+aqu+analisis_impacto");

        driver.findElement(By.xpath("//html/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[2]/td/form/table[1]/tbody/tr[2]/td[2]/input"))
                .sendKeys(comp);
        
        driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[2]/td/form/table[1]/tbody/tr[3]/td[2]/input[1]"))
                .click();
        
        driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[2]/td/form/table[1]/tbody/tr[3]/td[2]/input[2]"))
                .click(); 
        
        driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[2]/td/form/table[1]/tbody/tr[3]/td[2]/input[1]"))
                .click(); 
        
        WebElement selector = driver.findElement(By.xpath("//*[@id=\"H\"]"));
        //selector.findElement(By.).click();
        
        driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[2]/td/form/table[2]/tbody/tr/td/a"))
                .click(); //aceptar
    }
}
