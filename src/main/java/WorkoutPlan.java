import java.util.ArrayList;
import java.util.List;

public class WorkoutPlan {
    private String dayName;
    private List<String> exercises;
    public WorkoutPlan(String dayName, List<String> Exercises) {
        this.dayName = dayName;
        this.exercises = new ArrayList<>();
    }
    public String getDayName() {return dayName;}
    public void setDayName(String dayName) {}
    public List<String> getExercises() {return exercises;}
    public void setExercises(List<String> exercises) {}


}
