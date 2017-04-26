import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

//import storm configuration packages
import org.apache.storm.StormSubmitter;
import org.apache.storm.Config;
import org.apache.storm.topology.TopologyBuilder;
//import RandomSentenceSpout;
//import RandomSentenceBolt;


//Create main class LogAnalyserStorm submit topology.
public class RandomSentenceTopology_1 {
   public static void main(String[] args) throws Exception{
      //Create Config instance for cluster configuration
      Config config = new Config();
      config.setDebug(true);
      config.setNumWorkers(2);
		
      TopologyBuilder builder = new TopologyBuilder();
      builder.setSpout("randomsenspout", new RandomSentenceSpout_1());
      builder.setBolt("randomsenbolt", new RandomSentenceBolt())
         .shuffleGrouping("randomsenspout");
      StormSubmitter.submitTopology("randomsen_1", config, builder.createTopology());
   }
}
