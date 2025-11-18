public class Sigma {
    double current_value;
    double user_value;

    public Sigma() {
        this.user_value = 0.5; // Default value
        this.current_value = this.user_value;
    }

    public Sigma(double user_value) {
        this.user_value = user_value;
        this.current_value = user_value;
    }

    public Sigma(Sigma s) {
        this.current_value = s.current_value;
        this.user_value = s.user_value;
    }

    public void update_sigma(int current_generation, int max_generations) {
        this.current_value = this.user_value * (1.0 - (double) current_generation / max_generations);
    }
}
