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

public class MondadoriBoundary implements VendorBoundaryInterface {

    private static final String URL = "https://www.mondadoristore.it";
    private static final String SEARCHBOXSELECTOR = "search-input";
    private static final String ELEMENTSELECTOR = "div.info-data-product";
    private static final String TITLESELECTOR = "h3.title a";
    private static final String AUTHORSELECTOR = "a.nti-author";
    private static final String EDITOR = "a.nti-editor";
    private static final String DATE = "anno";
    private static final String PRICESELECTOR = "span.new-price, span.new-price.no-discount";
    private static final String NAME = "Mondadori";

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
        // Iterazione sui risultati
        for (WebElement element : bookElements) {
            TrovaPrezziBean bean = new TrovaPrezziBean();

            // Estrazione prezzo
            if (element.findElements(By.cssSelector(PRICESELECTOR)).isEmpty()) {
                continue;
            } else {
                WebElement priceElement = element.findElement(By.cssSelector(PRICESELECTOR));
                WebElement decimals = priceElement.findElement(By.cssSelector("span.decimals"));
                String price = priceElement.getText().replace(decimals.getText(), "") + decimals.getText();
                bean.setPrezzo(price);

            }

            // Estrazione titolo
            WebElement titleElement = element.findElement(By.cssSelector(TITLESELECTOR));
            bean.setTitolo(titleElement.getText());
            bean.setLink(titleElement.getDomAttribute("href"));

            // Estrazione autore
            if (element.findElements(By.cssSelector(AUTHORSELECTOR)).isEmpty()) {
                bean.setAutore("");
            } else {
                WebElement authorElement = element.findElement(By.cssSelector(AUTHORSELECTOR));
                bean.setAutore(authorElement.getText());
            }

            // Estrazione editore
            if (element.findElements(By.cssSelector(EDITOR)).isEmpty()) {
                bean.setEditore("");
            } else {
                WebElement editorElement = element.findElement(By.cssSelector(EDITOR));
                bean.setEditore(editorElement.getText());
            }

            // Estrazione anno
            if (element.findElements(By.cssSelector(DATE)).isEmpty()) {
                bean.setAnnoPubblicazione("");
            } else {
                WebElement dateElement = element.findElement(By.cssSelector(DATE));
                bean.setAnnoPubblicazione(dateElement.getText());
            }
            bean.setVendor(NAME);
            books.add(bean);

        }

        // Chiusura del browser
        driver.quit();
        return books;
    }
}
