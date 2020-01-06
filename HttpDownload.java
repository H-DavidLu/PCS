package Hawaii;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

import com.gnostice.pdfone.PdfDocument;
// we can apply third party library to easily integrate the PDF files

public class HttpDownload
{
    public static void main(String[] args) throws IOException {

        // W3 test dummy fil
        URL url1 =
                new URL("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf");


        byte[] baTest = new byte[1024];
        int baLength;
        FileOutputStream fos1 = new FileOutputStream("download.pdf");

        try {
            // Contacting the URL server
            System.out.print("Connecting to " + url1.toString() + " ...... ");
            URLConnection urlConn = url1.openConnection();

            // Checking whether the URL contains a PDF
            if (!urlConn.getContentType().equalsIgnoreCase("application/pdf")) {
                System.out.println("FAILED.\n[Sorry. This is not a PDF file.]");
            } else {
                try {

                    // Read the PDF from the URL and save to a local file
                    InputStream is1 = url1.openStream();
                    while ((baLength = is1.read(baTest)) != -1) {
                        fos1.write(baTest, 0, baLength);
                    }
                    fos1.flush();
                    fos1.close();
                    is1.close();

                    // Load the PDF document and display its page count
                    System.out.print("DONE.\nProcessing the PDF file ...... ");
                    PdfDocument doc = new PdfDocument();
                    try {
                        doc.load("download.pdf");
                        System.out.println("DONE.\nNumber of pages in the PDF is " +
                                doc.getPageCount());
                        doc.close();
                    } catch (Exception e) {
                        System.out.println("FAILED.\n[" + e.getMessage() + "]");
                    }

                } catch (ConnectException ce) {
                    System.out.println("FAILED.\n[" + ce.getMessage() + "]\n");
                }
            }

        } catch (NullPointerException npe) {
            System.out.println("FAILED.\n[" + npe.getMessage() + "]\n");
        }
    }
}



