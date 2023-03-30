package ch.sebug.docs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
        throws FileNotFoundException {
        System.out.println("Processing " + inputPath + " to generate " + outputPath);
        var in = new FileInputStream(new File(inputPath));
    }
}