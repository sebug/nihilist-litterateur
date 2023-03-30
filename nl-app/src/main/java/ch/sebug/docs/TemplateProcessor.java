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

import fr.opensagres.xdocreport.core.document.SyntaxKind;

import org.jodconverter.local.office.LocalOfficeManager;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.document.DocumentFamily;

import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;

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
        ParserConfigurationException, OfficeException {
        System.out.println("Processing " + inputPath + " to generate " + outputPath);
        var in = new FileInputStream(new File(inputPath));
        var report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);

        var doc = NodeModel.parse(new File(xmlPath));

        var context = report.createContext();

        var fieldsMetadata = report.createFieldsMetadata();
        fieldsMetadata.addFieldAsTextStyling("item.Description", SyntaxKind.Html);

        context.put("doc", doc);

        var intermediaryFile = File.createTempFile("nlapp-", ".odt");

        var templateOutput = new FileOutputStream(intermediaryFile);
        report.process(context, templateOutput);

        System.out.println("odt created here: " + intermediaryFile.getAbsolutePath());

        var out = new FileOutputStream(new File(outputPath));

        var builder = LocalOfficeManager.builder();
        builder.startFailFast(true);

        var officeManager = builder.install().build();
        officeManager.start();

        DocumentConverter converter = LocalConverter.builder().officeManager(officeManager)
        .build();

        var documentFormat = DefaultDocumentFormatRegistry.PDF;

        converter.convert(intermediaryFile)
        .as(DefaultDocumentFormatRegistry.ODT)
        .to(out).as(documentFormat).execute();
    }
}