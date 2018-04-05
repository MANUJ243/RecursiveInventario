package util;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * @author mmorende
 */
public class Metodos {

    private static ChromeDriver driver;
    //private static WebDriver driver;
    private static boolean bandera = false;
    private static int index;
    private static BufferedWriter out;
    private static ArrayList<String> componentes;

    public static void initDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mmorende\\Desktop\\chromedriver.exe");
        driver = new ChromeDriver();
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\mmorende\\Desktop\\geckodriver.exe");
        //driver = new FirefoxDriver();

        componentes = LecturaExcel.listado();
        for (int i = 0; i < componentes.size(); i++) {
            buscarComponente(componentes.get(i));
        }
    }

    public static void buscarComponente(String comp) {
        driver.get("https://tpw.bankinter.bk/www/es-es/cgi/int+aqu+analisis_impacto");    //link de busqueda
                                     //html/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[2]/td/form/table[1]/tbody/tr[2]/td[2]/input
        driver.findElement(By.xpath("//html/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[2]/td/form/table[1]/tbody/tr[2]/td[2]/input"))
                .sendKeys(comp);  //introduzco nombre de el componente

        driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[2]/td/form/table[1]/tbody/tr[3]/td[2]/input[1]"))
                .click();  //selecciono Rutina

        driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[2]/td/form/table[1]/tbody/tr[3]/td[2]/input[2]"))
                .click();

        driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[2]/td/form/table[1]/tbody/tr[3]/td[2]/input[1]"))
                .click();

        Select selector = new Select(driver.findElement(By.id("H")));
        selector.selectByVisibleText("Rutina Cobol");

        driver.findElement(By.xpath("/html/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[2]/td/form/table[2]/tbody/tr/td/a"))
                .click(); //aceptar

        getLlamantes();  //llamo al metodo para buscar las llamantes
    }

    public static void getLlamantes() {    //metodo para coger las llamantes de una tabla
        WebElement tabla = null;  //tabla es null en principio 
        String indexAux = "";

        try {
            tabla = driver.findElement(By.xpath("//*[@id=\"llamantes\"]/td/table"));  //si existen llamantes 
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("no ha encontrado parametros");  //si no existen llamantes, es null
            tabla = null;
        }
        
        //System.out.println(tabla.getText());
        //System.out.println(tabla.getTagName());
        //System.out.println(tabla);

        if (tabla != null /*&& !tabla.getText().equals("") && tabla.getText() != null*/) {                    //si no es null cojo sus llamantes 
            System.out.println("He entrado");
            List<WebElement> parTipo = tabla.findElements(By.cssSelector("#oddRow > td:nth-child(2)"));
            List<WebElement> par = tabla.findElements(By.cssSelector("#oddRow > td:nth-child(1)"));
            List<WebElement> imParTipo = tabla.findElements(By.cssSelector("#evenRow > td:nth-child(2)"));
            List<WebElement> imPar = tabla.findElements(By.cssSelector("#evenRow > td:nth-child(1)"));

            for (int i = 0; i < parTipo.size(); i++) {                  //por cada llamante 
                if (tabla.findElements(By.cssSelector("#oddRow > td:nth-child(2)")).get(i).getText().equals("TxHOST")) {        //cojo el elemento llamante en concreto 
                    System.out.println(par.get(i).getText());           //click sobre el 
                    if (i + 1 <= parTipo.size()) {
                        //indexAux = par.get(i + 1).getText();
                        indexAux = tabla.findElements(By.cssSelector("#oddRow > td:nth-child(1)")).get(i+1).getText();
                    }
                    par.get(i).click();                                 //y llamo al metodo de llamantes para el
                    getLlamantes();
                } else if (tabla.findElements(By.cssSelector("#oddRow > td:nth-child(2)")).get(i).getText().equals("RUTINA")) {
                    System.out.println(par.get(i).getText());
                    if (i + 1 <= parTipo.size()) {
                        //indexAux = par.get(i + 1).getText();
                        indexAux = tabla.findElements(By.cssSelector("#oddRow > td:nth-child(1)")).get(i+1).getText();
                    }
                    par.get(i).click();
                    getLlamantes();
                }
            }

            for (int i = 0; i < imParTipo.size(); i++) {
                if (tabla.findElements(By.cssSelector("#evenRow > td:nth-child(2)")).get(i).getText().equals("TxHOST")) {
                    System.out.println(imPar.get(i).getText());
                    if (i + 1 <= imParTipo.size()) {
                        indexAux = tabla.findElements(By.cssSelector("#evenRow > td:nth-child(1)")).get(i+1).getText();
                    }
                    imPar.get(i).click();
                    getLlamantes();
                } else if (tabla.findElements(By.cssSelector("#evenRow > td:nth-child(2)")).get(i).getText().equals("RUTINA")) {
                    System.out.println(imPar.get(i).getText());
                    if (i + 1 <= imParTipo.size()) {
                        indexAux = tabla.findElements(By.cssSelector("#evenRow > td:nth-child(1)")).get(i+1).getText();
                    }
                    imPar.get(i).click();
                    getLlamantes();
                }
            }
        } else { //si no ha encontrado llamantes, volver al nivel anterior y seguir con la siguiente
            if (!indexAux.equals("")) {
                buscarComponente(indexAux);
            }
        }
    }
}
