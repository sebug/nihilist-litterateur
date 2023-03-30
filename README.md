# Nihilist Litterateur - Creating PDFs from DOCX with XDocReport
Trying to push [XDocReport](https://github.com/opensagres/xdocreport) to its limits with complex Freemarker templates and data sources.


    cd nl-app
    mvn clean compile assembly:single
    java -jar target/nl-app-1.0-SNAPSHOT-jar-with-dependencies.jar

Next step is to convert it reliably to a pdf - I have tried JODConverter and the POI converter, but both have issues with the styling in the output docx, so my idea is to see whether there is a more closely related to Word version for that.