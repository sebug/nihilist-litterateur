package ch.sebug.docs;

public class TemplateProcessor
{
    private final String inputPath;
    private final String outputPath;

    public TemplateProcessor(String inputPath, String outputPath)
    {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public void process() {
        System.out.println("Processing " + inputPath + " to generate " + outputPath);
    }
}