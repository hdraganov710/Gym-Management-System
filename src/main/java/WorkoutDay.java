import java.util.ArrayList;
import java.util.List;

public class WorkoutDay {
    private String dayName;
    private List<String> exercises;
    public WorkoutDay(String dayName, List<String> Exercises) {
        this.dayName = dayName;
        this.exercises = new ArrayList<>();
    }
    public String getDayName() {return dayName;}
    public void setDayName(String dayName) {}
    public List<String> getExercises() {return exercises;}
    public void setExercises(List<String> exercises) {}
    public void addExercise(String exercise) {
        this.exercises.add(exercise);
    }
    @Override
    public String toString() {
        return "Day: " +  dayName + ", Exercises: " + exercises;
    }


}
