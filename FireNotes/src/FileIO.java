import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * IO for HTML generation
 */
public class FileIO {

    /**
     * Creates a valid HTML doc from file contents
     * @param input String path to file to read
     * @return valid HTML
     */
    public static String createHTML(String input, String title) {
        StringBuilder output = new StringBuilder();

        output.append("<!DOCTYPE html>\n<html>\n");
        
        // handle head
        // String textColor = "D3C6AA";
        // String backgroundColor = "2D353B";
        // String font = "Segoe UI";
        // String style = sandwich("\nbody {\nfont-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\nbackground-color: #2D353B;\ncolor: #D3C6AA;\n}", "style");
        output.append(sandwich(title, "title"));
        output.append("<link rel=\"stylesheet\" href=\"mystyle.css\">");


        for (String s : input.split("\n")) {
            if (s.isEmpty()) continue;

            if (s.contains("###")) {
                output.append(sandwich(s.replace("###", "").trim(), "h3"));
                output.append("<hr>\n");
                continue;
            }
            else if (s.contains("##")) {
                output.append(sandwich(s.replace("##", "").trim(), "h2"));
                output.append("<hr>\n");
                continue;
            }
            else if (s.contains("#")) {
                output.append(sandwich(s.replace("#", "").trim(), "h1"));
                output.append("<hr>\n");
                continue;
            }
            else {
                output.append(sandwich(s, "p"));
                continue;
            }
        }
        output.append("</html>");
        return output.toString();
    }

    public static String sandwich(String input, String tag) {
        return String.format("<%s>%s</%s>\n", tag, input, tag);
    }

    /**
     * Returns a string containing file contents
     * @param path String representation of file path
     * @return String of all file contents
     */
    public static String read(String filepath) throws Exception {
        BufferedReader br = Files.newBufferedReader(Paths.get(filepath));
        Stream<String> lines = br.lines();
        String output = lines.collect(Collectors.joining("\n"));
        br.close();
        return output;
    }

    public static void write(String path, String input) throws Exception {
        File myObj = new File(path);
        myObj.createNewFile();

        FileWriter writer = new FileWriter(myObj);
        PrintWriter pw = new PrintWriter(writer, true);

        pw.print(input);

        pw.close();
        writer.close();
    }

    public static void main(String[] args) throws Exception {
        // String emptyHTML = createHTML("");
        // write("test.html", emptyHTML);
        var contents = read("input.md");
        var html = createHTML(contents, "output");
        write("test.html", html);
    }

}