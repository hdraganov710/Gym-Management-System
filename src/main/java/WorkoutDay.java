import java.util.ArrayList;
import java.util.List;

public class WorkoutDay {
    private String dayName;
    private List<String> exercises;
    public WorkoutDay(String dayName, List<String> exercises) {
        this.dayName = dayName;
        this.exercises = exercises != null ? new ArrayList<>(exercises) : new ArrayList<>();
    }
    public String getDayName() {return dayName;}
    public void setDayName(String dayName) {this.dayName = dayName;}
    public List<String> getExercises() {return exercises;}
    public void setExercises(List<String> exercises) {this.exercises = exercises;}
    public void addExercise(String exercise) {
        this.exercises.add(exercise);
    }
    @Override
    public String toString() {
        return "Day: " +  dayName + ", Exercises: " + exercises;
    }


}
