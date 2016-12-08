/**
 * Created by yangmeng on 12/5/16.
 */
import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class EmotionReducer extends  Reducer<Text, Text, Text, Text> {
    @Override

    /*
    input:
    K                       V
    "ID:orgName"         "0.1,0.2,0.3,0.4,0.5"; "0.0,0.2,0.0,0.4,0.0"; ..

    output:
    K                       V
    "ID:orgName"         "0.1,0.4,0.3,0.8,0.5"
     */
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        double[] mood_sum_arr = new double[5];
        for (Text value : values) {
            String[] vals = value.toString().split(",");
            for (int i = 0; i < 5; ++i) {
                mood_sum_arr[i] += Double.parseDouble(vals[i].trim());
            }
        }
        String str_result = new String();
        for (double each_mood_val : mood_sum_arr) {
            str_result += each_mood_val + ",";
        }
        context.write(key, new Text(str_result.substring(0, str_result.length() - 1)));
    }
}