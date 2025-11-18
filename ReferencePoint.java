public class ReferencePoint {
    double[] objectives;

    public ReferencePoint(double[] objectives) {
        this.objectives = objectives;
    }

    public ReferencePoint(ReferencePoint ref) {
        this.objectives = new double[ref.objectives.length];
        System.arraycopy(ref.objectives, 0, this.objectives, 0, ref.objectives.length);
    }

    public String reference_point_to_string() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < objectives.length; i++) {
            sb.append(objectives[i]);
            if (i < objectives.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
