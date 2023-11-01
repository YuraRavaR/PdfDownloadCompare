package org.pages;

import de.redsix.pdfcompare.CompareResult;
import de.redsix.pdfcompare.PdfComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

import static org.Constant.DOWNLOAD_PATH;
import static org.pages.PdfPage.downloadedFiles;

public class AbstractPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public static final Logger LOG = LogManager.getLogger(AbstractPage.class.getName());
    @FindBy(xpath = "//button[@id='ez-accept-all']")
    protected WebElement acceptCookiesBtn;

    @FindBy(id = "google_ads_iframe_/21732118914,22573772600/filesamples_com-pixel1_0")
    protected WebElement frame1;


    AbstractPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void clickByElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
    }

    public void scrollByPixel(int x, int y) {
        String script = String.format("window.scrollTo(%d, %d);", x, y);
        ((JavascriptExecutor) driver).executeScript(script);
    }

    public void acceptCookies() {
        try {
            if (acceptCookiesBtn != null ) {
                wait.until(ExpectedConditions.visibilityOf(acceptCookiesBtn));
                clickByElement(acceptCookiesBtn);
                LOG.debug("Cookies accepted");
            }
        } catch (NoSuchElementException e) {
            LOG.info("Accept cookies button not found" + e.getMessage());
        }catch (TimeoutException e) {
            LOG.info("Timeout waiting for Accept cookies button:" + e.getMessage());
        }
    }

    public void closeGoogleVignetteAdd() {
        try {
            if (frame1 != null) {
                wait.until(ExpectedConditions.visibilityOf(frame1));
                driver.switchTo().frame(frame1);
                WebElement frame2 = driver.findElement(By.id("ad_iframe"));
                driver.switchTo().frame(frame2);
                driver.findElement(By.xpath("//div[@id='dismiss-button']/div/span")).click();
                driver.switchTo().defaultContent();
                LOG.debug("Google vignette ads closed ");
            }
        } catch (NoSuchElementException e) {
            LOG.info("Google vignette ads not found: " + e.getMessage());
        } catch (TimeoutException e) {
            LOG.info("Timeout waiting for Google vignette ads:" + e.getMessage());
        }
    }

    public void cleanAllDownloadedFiles() {
        File directory = new File(DOWNLOAD_PATH);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && downloadedFiles.contains(file.getName())) {
                        file.delete();
                    }
                }
                LOG.debug("All downloaded files deleted");
            }
        }
    }

    public boolean pdfFilesAreSame(String expectedPdfFileName, String actualPdfFileName) {
        String file1 = DOWNLOAD_PATH + "\\" + expectedPdfFileName;
        String file2 = DOWNLOAD_PATH + "\\" + actualPdfFileName;
        final CompareResult result;
        try {
            result = new PdfComparator(file1, file2).compare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.isEqual();
    }
}

