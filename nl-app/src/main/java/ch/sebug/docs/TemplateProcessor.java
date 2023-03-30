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

import fr.opensagres.poi.xwpf.converter.core.XWPFConverterException;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;

import freemarker.ext.dom.NodeModel;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

public class TemplateProcessor
{
    private final String inputPath;
    private final String outputPath;
    private final String xmlPath;

    public TemplateProcessor(String inputPath, String outputPath,
        String xmlPath)
    {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.xmlPath = xmlPath;
    }

    public void process()
        throws FileNotFoundException, IOException, XDocReportException, SAXException,
        ParserConfigurationException {
        System.out.println("Processing " + inputPath + " to generate " + outputPath);
        var in = new FileInputStream(new File(inputPath));
        var report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);

        var doc = NodeModel.parse(new File(xmlPath));

        var context = report.createContext();
        context.put("doc", doc);

        var intermediaryFile = File.createTempFile("nlapp-", ".docx");

        var templateOutput = new FileOutputStream(intermediaryFile);
        report.process(context, templateOutput);

        System.out.println("docx created here: " + intermediaryFile.getAbsolutePath());

        var out = new FileOutputStream(new File(outputPath));

        var document = new XWPFDocument(new FileInputStream(intermediaryFile));
        document.createStyles();

        var options = PdfOptions.create();

        PdfConverter.getInstance().convert(document, out, options);

        System.out.println("Final document created at " + outputPath);
    }
}