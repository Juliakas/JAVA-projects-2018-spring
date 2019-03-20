package IO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class OutputFile {

    PrintWriter writer;
    public OutputFile() {
        try {
            writer = new PrintWriter("Financial report.txt", "UTF-8");
        } catch(UnsupportedEncodingException uee) {
            Gui gui = new Gui(false);
            gui.setUpTextArea();
            gui.addToTextArea("Unsupported encoding exception");
        } catch(FileNotFoundException fnfe) {
            Gui gui = new Gui(false);
            gui.setUpTextArea();
            gui.addToTextArea("File not found exception");
        }
    }

    public void writeln(String line){
        writer.printf("%s", line);
    }

    public void closeFile() {
        writer.close();
    }
}
