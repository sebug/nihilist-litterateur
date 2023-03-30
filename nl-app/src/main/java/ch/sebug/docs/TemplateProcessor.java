package ch.sebug.docs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.core.XDocReportException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;

public class TemplateProcessor
{
    private final String inputPath;
    private final String outputPath;

    public TemplateProcessor(String inputPath, String outputPath)
    {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public void process()
        throws FileNotFoundException, IOException, XDocReportException {
        System.out.println("Processing " + inputPath + " to generate " + outputPath);
        var in = new FileInputStream(new File(inputPath));
        var report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        var context = report.createContext();
        context.put("name", "world");

        var intermediaryFile = File.createTempFile("nlapp-", ".docx");

        var templateOutput = new FileOutputStream(intermediaryFile);
        report.process(context, templateOutput);

        System.out.println("Docx created, converting to pdf");

        var documentToConvert = new XWPFDocument(new FileInputStream(intermediaryFile));
        documentToConvert.createStyles();
        var options = PdfOptions.create();

        var pdfOutput = new FileOutputStream(new File(outputPath));
        var converterInstance = PdfConverter.getInstance();
        converterInstance.convert(documentToConvert, pdfOutput, options);

        System.out.println("Converted - output can be found on " + outputPath);
    }
}