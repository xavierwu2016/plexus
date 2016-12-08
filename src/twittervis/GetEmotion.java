/*
 *  GPL 3.0 License
 *  This software is offered AS-IS. The author does not take any responsibilities of
 *  any misuse of this package.
 */
package twittervis;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentEmotion;
import com.ibm.watson.developer_cloud.service.exception.BadRequestException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 * Get the emotion of a String text
 * @author Xavier Wu
 */
public class GetEmotion {

    public static double joyValue;
    public static double angerValue;
    public static double fearValue;
    public static double disgustValue;
    public static double sadnessValue;
    public static String finalEmotion;
    public static String finalE;

    /**
     * Get emotions.
     *
     * @param tweetText
     */
    public GetEmotion(String tweetText) {
        AlchemyLanguage service = new AlchemyLanguage();

        // the following API key is not valid. Get yours for free
        // at IBM Bluemix Developer site
        // Create a new AlchemyLanguage App and copy the API and replace it to the
        // following key
        service.setApiKey("e8bc2sdhfgsd6553279[INVALID API KEY!!]e35d891a082e7");
        Map<String, Object> params = new HashMap<>();
        params.put(AlchemyLanguage.TEXT, tweetText);

        try {
            DocumentEmotion emotion = service.getEmotion(params).execute();
            JSONObject obj = new JSONObject(emotion);

            joyValue = obj.getJSONObject("emotion").getDouble("joy");
            angerValue = obj.getJSONObject("emotion").getDouble("anger");
            fearValue = obj.getJSONObject("emotion").getDouble("fear");
            sadnessValue = obj.getJSONObject("emotion").getDouble("sadness");
            disgustValue = obj.getJSONObject("emotion").getDouble("disgust");
            System.out.println("joy: " + joyValue + ", anger: " + angerValue
                    + ", Sad: " + sadnessValue + ", Disgust: "
                    + disgustValue + ", fear: " + fearValue + ".");
            finalE = getFinalEmotion(joyValue, angerValue, fearValue, sadnessValue, disgustValue);
        } catch (BadRequestException | NullPointerException e) {
            System.out.println("Error, Skipped.");
        }

    }

    public static void main(String args[]) {

    }

    /**
     * Find the emotion with the largest relevance value using a created HashMap
     * 
     * @param e1
     * @param e2
     * @param e3
     * @param e4
     * @param e5
     * @return Final Emotion
     */
    public static String getFinalEmotion(double e1, double e2, double e3,
            double e4, double e5) {
        // Create a map that stores the keys and values of the emotion 
        // analysis results
        HashMap<String, Double> map = new HashMap<>();
        map.put("Joy", e1);
        map.put("Anger", e2);
        map.put("Fear", e3);
        map.put("Sadness", e4);
        map.put("Disgust", e5);

        Map.Entry<String, Double> maxEntry = null;
        
        // Now look for the variable with the maximum in the created HashMap
        double maxValueInMap = (Collections.max(map.values()));  // This will return max value in the Hashmap
        map.entrySet().stream().filter((entry) -> (entry.getValue() == maxValueInMap)).forEachOrdered((entry) -> {
            // System.out.println(entry.getKey());     // Print the key with max value
            finalEmotion = (String) entry.getKey();
        }); // Itrate through HashMap to find the max
        return finalEmotion; // this the emotion with the largest relevance value
    }
}
