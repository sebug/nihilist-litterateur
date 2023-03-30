# Nihilist Litterateur - Creating PDFs from DOCX with XDocReport
Trying to push [XDocReport](https://github.com/opensagres/xdocreport) to its limits with complex Freemarker templates and data sources.

This project requires libreoffice (for JODConverter and better output), you can install it as follows if you are using Codespaces:

    sudo add-apt-repository ppa:libreoffice/ppa
    sudo apt install libreoffice

    cd nl-app
    mvn clean compile assembly:single
    java -jar target/nl-app-1.0-SNAPSHOT-jar-with-dependencies.jar
