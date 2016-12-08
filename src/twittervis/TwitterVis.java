/*
 * GPL 3.0 License
 */

package twittervis;

import java.util.Iterator;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import static twittervis.TwitterDevApiKey.KEY;
import static twittervis.TwitterDevApiKey.SECRET;
import static twittervis.TwitterDevApiKey.TOKEN;
import static twittervis.TwitterDevApiKey.TOKENSECRET;

public class TwitterVis {

    public static String keyword1;
    public static String keyword2;
    public ConfigurationBuilder cb;
    private final Graph graph;
    TwitterFactory tf;
    private String JoyNode;
    private String SadnessNode;
    private String AngerNode;
    private String DisgustNode;
    private String FearNode;

    public static void main(String args[]) {

        // The following two keywords will be taken as Search Keywords
        // They correspond to Topic A and Topic B
        keyword1 = "Trump";
        keyword2 = "Hilary";
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        // Creating a new twitterVis instance
        TwitterVis twitterVis = new TwitterVis();
    }

    // Inititate an instance for twitter Search instance
    public void initiate() {
        cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(KEY)
                .setOAuthConsumerSecret(SECRET)
                .setOAuthAccessToken(TOKEN)
                .setOAuthAccessTokenSecret(TOKENSECRET);
        tf = new TwitterFactory(cb.build());
        
        // Retrieve tweets for two topics using keyword1 and keyword2
        // the "true" and "false" values represent the binary status of two topics.
        // true = the 1st topic, and false = the 2nd topic
        retrieveTweets(keyword1, true);
        retrieveTweets(keyword2, false);
    }

      
    public void retrieveTweets(String keyword, boolean topic) {

       
        Twitter twitter = tf.getInstance();
        Query query = new Query(keyword);
        // The query parameter ("keyword") can be modified to make more
        // specific search queries based on language, users, time, etc.
        //Query query = new Query(keyword +" AND lang:en AND until:2016-12-01");
        
        //number of tweets needed for each topic.
        query.count(240);
        
        // Assign the tweets to the corresponding Emotion Node
        if (topic == true) {
            JoyNode = "Joy1";
            SadnessNode = "Sadness1";
            AngerNode = "Anger1";
            DisgustNode = "Disgust1";
            FearNode = "xFear1";
        } else {
            JoyNode = "Joy2";
            SadnessNode = "Sadness2";
            AngerNode = "Anger2";
            DisgustNode = "Disgust2";
            FearNode = "xFear2";
        }

        //Try making the query request.
        try {
            QueryResult result = twitter.search(query);
            
            // Process the analysis results and create new edges and nodes accordingly
            result.getTweets().forEach((status) -> {
                status.getText();  //get the textual info from a tweet
                // String userName = status.getUser().getScreenName();
                String TwtText = status.getText();
                System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
                String twtEmotion = getTweetEmotion(TwtText);
                
                // A switch to assign the tweet to the correct node
                switch (twtEmotion) {
                    case "Joy":
                        System.out.println("Joy (*^__^*)");
                        graph.addEdge(TwtText, JoyNode, TwtText);
                        break;
                    case "Sadness":
                        System.out.println("Sad  (*T_T*) ");
                        graph.addEdge(TwtText, SadnessNode, TwtText);
                        break;
                    case "Anger":
                        System.out.println("Anger ヽ(｀Д´)ﾉ");
                        graph.addEdge(TwtText, AngerNode, TwtText);
                        break;
                    case "Fear":
                        System.out.println("Fear ⊙﹏⊙|||");
                        graph.addEdge(TwtText, FearNode, TwtText);
                        break;
                    case "Disgust":
                        System.out.println("Disgust (*⊙~⊙)");
                        graph.addEdge(TwtText, DisgustNode, TwtText);
                        break;
                }
            });
           
        } catch (TwitterException ex) {  //fallback if exceptions occur
            System.out.println("Couldn't connect: " + ex);
        }
    }

    /**
     * Get the overall emotion of a single tweet
     * @param tweet
     * @return the final emotion of a single tweet
     */
    public String getTweetEmotion(String tweet) {
        GetEmotion newEmj = new GetEmotion(tweet);
        System.out.println("Final Emotion is: ");
        //System.out.println(GetEmotion.finalE);
        String twtEmotion = GetEmotion.finalE;
        return twtEmotion;
    }

    // Start the visualiztion part
    public TwitterVis() {
        graph = new SingleGraph("Twitter Visualization");
        
        System.setProperty("http.agent", "Chrome");
        graph.addAttribute("ui.stylesheet", "url('https://raw.githubusercontent.com/xavierwu2016/plexus/master/src/twittervis/stylesheet.css')");
        // the stylesheet can also be local, shown as below
        // graph.addAttribute("ui.stylesheet", "url('file:///Users/Xavier/NetBeansProjects/TwitterVis/src/twittervis/stylesheet.css')");
        graph.setAutoCreate(true);
        graph.setStrict(false);
        graph.display();

        // The topics here determine what to display in the graph
        // If you change the words below, you have to change them
        // in the stylesheet as well. tHE stylesheet is written in css
        // and thus the variables names cannot be altered here. 
        // You need to change them manually.
        String topicA = "Trump";
        String topicB = "Hilary";

        // Create new edges
        // The following 10 "addEdge" commands will build the structure 
        // of the visualization
        graph.addEdge("joy1", topicA, "Joy1");
        graph.addEdge("sadness1", topicA, "Sadness1");
        graph.addEdge("anger1", topicA, "Anger1");
        // There is an "x" before Fear1 because of a bug in Java AWT
        // The node name cannot start with or contain "fea" otherwise
        // it will be read as a color element, like #ffeeaa
        graph.addEdge("xfear1", topicA, "xFear1");
        graph.addEdge("disgust1", topicA, "Disgust1");

        graph.addEdge("joy2", topicB, "Joy2");
        graph.addEdge("sadness2", topicB, "Sadness2");
        graph.addEdge("anger2", topicB, "Anger2");
        graph.addEdge("xfear2", topicB, "xFear2");
        graph.addEdge("disgust2", topicB, "Disgust2");

        Node n = graph.getNode(topicA);
        
        // Label all the nodes
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }

        initiate();

        // Label the nodes for tweets again.
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }

    }
    
    // apply the styles based on the ui class
    public void explore(Node source) {
        Iterator<? extends Node> k = source.getBreadthFirstIterator();

        while (k.hasNext()) {
            Node next = k.next();
            next.setAttribute("ui.class", "marked");

            sleep();
        }
    }

    protected void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
        }
    }
}
