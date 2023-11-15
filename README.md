# PDF Compare Library

Java library to compare two PDF files. Files are rendered and compared pixel by pixel.

Allow you to ignore specified areas during comparison.

## Usage

#### Before: You need to download the  pdfCompare.jar and add it as library

### Comparing PDF Files

```java
PdfCompare pdfCompare=new PdfCompare();

// Compare two PDF files
        boolean areEqual=pdfCompare.comparePdf("path/to/expected.pdf","path/to/actual.pdf");

        if(areEqual){
        System.out.println("The PDF files are identical.");
        }else{
        System.out.println("The PDF files are different.");
        }
```

### Comparing PDF Files with Ignored Areas

Use the `comparePdfWithIgnoredAreas` method when you want to exclude certain regions from comparison.

**Parameters:**

- `expectedFilePath` (String): The file path to the expected PDF file.
- `actualFilePath` (String): The file path to the actual PDF file.
- `exclusions` (List&lt;PageArea&gt;): A list of `PageArea` objects representing the areas to be ignored during the
  comparison.

**Example:**

```java
PdfCompare pdfCompare=new PdfCompare();
        List<PageArea> exclusions=Arrays.asList(
        new PageArea(2,100,200,300,400), // Excludes a specific area on page 2 in pixels
        new PageArea(130,300,190,200),   // Page is optional. When not given, the exclusion applies to all pages.
        new PageArea(7)                  // coordinates are optional. When not given, the whole page is excluded.
        // Add more exclusions as needed
        );

        boolean areEqualWithIgnoredAreas=pdfCompare.comparePdfWithIgnoredAreas(
        "path/to/expected.pdf","path/to/actual.pdf",exclusions);
```

In the example above:

- **Page (2):** Specifies that the exclusions apply for one page of the PDF.
- **X1 (100):** Sets the horizontal starting coordinate of the area to be ignored.
- **Y1 (200):** Sets the vertical starting coordinate of the area to be ignored.
- **X2 (300):** Sets the horizontal ending coordinate of the area to be ignored.
- **Y2 (400):** Sets the vertical ending coordinate of the area to be ignored.

# CMD Run

- **Build Fat Jar:** mvn clean compile assembly:single

