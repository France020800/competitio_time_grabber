import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/athletes.txt"));
        List<Athlete> athletes = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            athletes.add(new Athlete(line));
        }
        ExelCreator creator = new ExelCreator("Gare");
        creator.create(athletes);

        // Grab the data
        /*
        if (args.length < 1) {
            System.out.println("Fornisci la località della competizione");
            return;
        }
         */
        Grabber grabber = new Grabber("calenzano");
        athletes.forEach(grabber::competitionTimeOf);

        // Write the data
        athletes.forEach(a -> {
            try {
                creator.insert(a);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
