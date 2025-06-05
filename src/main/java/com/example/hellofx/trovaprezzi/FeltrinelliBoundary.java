package com.example.hellofx.trovaprezzi;

import com.example.hellofx.graphiccontroller.VendorBoundaryInterface;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FeltrinelliBoundary implements VendorBoundaryInterface {

    private static final String URL = "https://www.lafeltrinelli.it";
    private static final String SEARCHBOXSELECTOR = "inputSearch";
    private static final String ELEMENTSELECTOR = ".cc-product-list-item";
    private static final String TITLESELECTOR = ".cc-title";
    private static final String AUTHORSELECTOR = ".cc-author";
    private static final String EDITORDATESELECTOR = ".cc-publisher";
    private static final String PRICESELECTOR = ".cc-price";
    private static final String NAME = "Feltrinelli";

    public List<TrovaPrezziBean> fetchResults(TrovaPrezziBean trovaPrezziBean) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        // Connessione al sito
        driver.get(URL);
        // Trovo la searchbox e inserisco il titolo del libro
        WebElement searchBox = driver.findElement(By.id(SEARCHBOXSELECTOR));
        searchBox.sendKeys(trovaPrezziBean.getRicerca());
        searchBox.sendKeys(Keys.ENTER);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            // Attendo che il primo risultato sia visibile
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ELEMENTSELECTOR)));
        } catch (TimeoutException e) {
            System.out.println("Errore: L'elemento non è visibile dopo il timeout.");
            e.printStackTrace();
        }
        // Tutti gli elementi trovati
        List<WebElement> bookElements = driver.findElements(By.cssSelector(ELEMENTSELECTOR));
        List<TrovaPrezziBean> books = new ArrayList<>();
        String[] editoreAnno;
        // Iterazione sui risultati
        for (WebElement element : bookElements) {
            TrovaPrezziBean bean = new TrovaPrezziBean();

            // Estrazione prezzo
            if (element.findElements(By.cssSelector(PRICESELECTOR)).isEmpty()) {
                continue;
            } else {
                WebElement priceElement = element.findElement(By.cssSelector(PRICESELECTOR));
                bean.setPrezzo(priceElement.getText());
            }

            // Estrazione titolo
            WebElement titleElement = element.findElement(By.cssSelector(TITLESELECTOR));
            bean.setTitolo(titleElement.getText());
            bean.setLink(URL+titleElement.getDomAttribute("href"));

            // Estrazione autore
            if (element.findElements(By.cssSelector(AUTHORSELECTOR)).isEmpty()) {
                bean.setAutore("");
            } else {
                WebElement authorElement = element.findElement(By.cssSelector(AUTHORSELECTOR));
                bean.setAutore(authorElement.getText());
            }

            // Estrazione editore e data
            editoreAnno = estraiEditoreAnno(element);
            bean.setEditore(editoreAnno[0]);
            bean.setAnnoPubblicazione(editoreAnno[1]);

            bean.setVendor(NAME);
            books.add(bean);


        }

        // Chiusura del browser
        driver.quit();
        return books;
    }


    private String[] estraiEditoreAnno(WebElement element){
        String editore;
        String anno;
        if (element.findElements(By.cssSelector(EDITORDATESELECTOR)).isEmpty()) {
            editore = "";
            anno = "";
        } else {
            WebElement publisherElement = element.findElement(By.cssSelector(EDITORDATESELECTOR));
            String fullText = publisherElement.getText();
            if(fullText.contains(",")){
                String[] parts = fullText.split(","); // Divideremo in base alla virgola
                editore = parts[0].trim();
                anno = parts[1].trim();
            } else if(fullText.matches(".*\\d{4}.*")){
                editore = "";
                anno = fullText;
            }else{
                editore = fullText;
                anno = "";
            }
        }
        return new String[]{editore, anno};
    }

}
