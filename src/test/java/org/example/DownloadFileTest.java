package org.example;

import org.pages.PdfPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.pages.PdfPage.downloadedFiles;

public class DownloadFileTest extends BaseTest {


    @Test
    public void downloadDifferentPdfFile()  {
        openUrl("https://filesamples.com/formats/pdf");
        PdfPage pdfPage = new PdfPage(driver, wait);

        pdfPage.acceptCookies();
        pdfPage.closeGoogleVignetteAdd();
        pdfPage.scrollByPixel(0, 250);
        pdfPage.downloadPdfFile(3);
        pdfPage.downloadPdfFile(2);
        Assert.assertFalse(pdfPage.pdfFilesAreSame(downloadedFiles.get(0), downloadedFiles.get(1)),
                "PDF are the same");
    }
    @Test
    public void downloadSamePdfFile()  {
        openUrl("https://filesamples.com/formats/pdf");
        PdfPage pdfPage = new PdfPage(driver, wait);

        pdfPage.acceptCookies();
        pdfPage.closeGoogleVignetteAdd();
        pdfPage.scrollByPixel(0, 250);
        pdfPage.downloadPdfFile(3);



        Assert.assertTrue(pdfPage.pdfFilesAreSame(downloadedFiles.get(0), downloadedFiles.get(0)),
                "PDF are different");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp() {
        PdfPage pdfPage = new PdfPage(driver, wait);
        pdfPage.cleanAllDownloadedFiles();
        downloadedFiles.clear();
    }
}
