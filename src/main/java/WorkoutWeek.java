import java.util.*;
public class WorkoutWeek {
    private List<WorkoutDay> days;

    public WorkoutWeek() {
        this.days = new ArrayList<>();
    }
    public void addDay(WorkoutDay day) { days.add(day); }
    public List<WorkoutDay> getDays() { return days; }

}
