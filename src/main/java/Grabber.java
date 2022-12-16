import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grabber {
    private final String URL;
    private final ExelCreator creator;

    public Grabber(String location) {
        this.URL = "http://www.federnuoto.toscana.it/" + location + ".htm";
        this.creator = new ExelCreator("Gare");
        writePage();
    }

    private String getPage() {
        String content = null;
        URLConnection connection;
        try {
            connection = new URL(URL).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return content;
    }

    private void writePage() {
        try {
            String page = getPage();
            FileWriter myWriter = new FileWriter("page.txt");
            myWriter.write(page);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void competitionTimeOf(Athlete athlete) {
        String line;
        String competition = null;
        String previous = "";

        try {
            FileReader fileReader = new FileReader("page.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                if (previous.equals("-------------------------------------------------------------------------------------------")) {
                    competition = line;
                    bufferedReader.readLine();
                    line = bufferedReader.readLine();
                    while (line != null && !line.equals("-------------------------------------------------------------------------------------------")) {
                        if (line.contains(athlete.getName())) {
                            /*
                            System.out.println(competition.replace("Esordienti B  Maschi", "") + '\n' +
                                    line.replace("<span class='testo13br'>", "").replace("</span>", "") + '\n');
                             */
                            competition = creator.checkCompetition(competition);
                            String time = matcher(line);
                            athlete.setCompetition(competition, time);
                        }
                        line = bufferedReader.readLine();
                    }
                }
                previous = line;
            }
            bufferedReader.close();
            fileReader.close();
            // System.out.println("-------------------------------------------------------------------------------------------\n");
        } catch (IOException ex) {
            System.out.println("Error reading file");
        }
    }

    private String matcher(String line) {
        final String regex = "([0-9]')?[0-9]{2}[.][0-9]{2}";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(line);
        String time = "-1";

        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            time = matcher.group(0);
        }
        return time;
    }
}
