package ch.sebug.docs;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            if (args.length < 3) {
                System.err.println("Usage: ch.sebug.docs.App input.docx output.pdf data.xml");
                System.exit(1);
            }
            var templateProcessor = new TemplateProcessor(args[0], args[1], args[2]);
            templateProcessor.process();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);   
        }
    }
}
