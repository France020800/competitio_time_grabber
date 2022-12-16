import java.util.ArrayList;
import java.util.List;

public class Athlete {

    private final String name;
    private List<List<String>> competition = new ArrayList<>();

    public Athlete(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCompetition(String competitionName, String time) {
        List<String> list = new ArrayList<>();
        list.add(competitionName);
        list.add(time);
        competition.add(list);
    }

    public List<List<String>> getCompetition() {
        return competition;
    }
}
