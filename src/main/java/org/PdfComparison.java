package org;

import de.redsix.pdfcompare.CompareResult;
import de.redsix.pdfcompare.PdfComparator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PdfComparison {
    public static void main(String[] args) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String currentDateTime = dateFormat.format(new Date());

        String file1 = "src/main/resources/data/File13.pdf";
        String file2 = "src/main/resources/data/File14.pdf";
        String results = "src/main/resources/results/result_" + currentDateTime;

        new PdfComparator(file1, file2).compare().writeTo(results);

        checkDifferences(file1, file2);
    }

    private static void checkDifferences(String expectedPdfFileName, String actualPdfFileName) throws IOException {
        final CompareResult result = new PdfComparator(expectedPdfFileName, actualPdfFileName)
//                ignore some page
//                .withIgnore(new PageArea(1))
                .compare();
        if (result.isNotEqual()) {
            System.out.println("Differences found!");
        }
        if (result.isEqual()) {
            System.out.println("No Differences found!");
        }
        if (result.hasDifferenceInExclusion()) {
            System.out.println("Differences in excluded areas found!");
        }
        result.getDifferences();
    }


}
