package org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.Constant.DOWNLOAD_PATH;

public class PdfPage extends AbstractPage {
    public static List<String> downloadedFiles = new ArrayList<>();


    public PdfPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void downloadPdfFile(int fileNumber) {
        String xpath = String.format("//*[@href='/samples/document/pdf/sample%d.pdf']", fileNumber);
        WebElement fileElement = driver.findElement(By.xpath(xpath));
        clickByElement(fileElement);
        AbstractPage.LOG.debug("File started download");
        waitForFileDownload(20000, DOWNLOAD_PATH, "//sample" + fileNumber + ".pdf");
        AbstractPage.LOG.debug("File finished download");
        downloadedFiles.add("sample" + fileNumber + ".pdf");
    }

    public void waitForFileDownload(int totalTimeoutInMillis, String downloadsDirectory, String expectedFileName) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(totalTimeoutInMillis))
                .pollingEvery(Duration.ofMillis(500));

        File fileToCheck = new File(downloadsDirectory, expectedFileName);

        wait.until((WebDriver wd) -> fileToCheck.exists());
    }
}
