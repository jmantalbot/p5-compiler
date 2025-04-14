package submit;


public class StringLabelGenerator {
    private static int counter = 0;
    public static String getLabel() {
        return "datalabel" + counter++;
    }

}
