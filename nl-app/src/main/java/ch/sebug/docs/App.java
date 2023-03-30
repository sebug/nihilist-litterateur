package ch.sebug.docs;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        if (args.length < 2) {
            System.err.println("Usage: ch.sebug.docs.App input.docx output.pdf");
            System.exit(1);
        }
        var templateProcessor = new TemplateProcessor(args[0], args[1]);
        templateProcessor.process();
    }
}
