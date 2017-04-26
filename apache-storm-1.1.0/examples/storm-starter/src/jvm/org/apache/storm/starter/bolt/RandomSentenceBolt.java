package org.apache.storm.starter.bolt;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

public class RandomSentenceBolt implements IRichBolt {
   Map<String, Integer> counterMap;
   private OutputCollector collector;

   @Override
   public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
      this.counterMap = new HashMap<String, Integer>();
      this.collector = collector;
   }

   @Override
   public void execute(Tuple tuple) {
      System.out.println();
      System.out.println("***In Bolt***");
      String timeStamp = tuple.getString(0).split(",")[0];
      System.out.println("timeStamp: " + timeStamp);

      String key = getDateTimeStr(timeStamp);
     // Integer duration = tuple.getInteger(1);
		
      if(!counterMap.containsKey(key)){
         counterMap.put(key, 1);
      }else{
         Integer c = counterMap.get(key) + 1;
         counterMap.put(key, c);
      }
      System.out.println("key: " + key + "  value: " + counterMap.get(key));
      System.out.println("Map size: " + counterMap.size());
      writeToFile(key, counterMap.get(key));
      collector.ack(tuple);
   }

  private void writeToFile(String key, Integer val) {
	BufferedWriter bw = null;
	FileWriter fw = null;
	try{
	    String filename = "/home/ubuntu/Storm/my_logs" + key;
	    File file = new File(filename);
	    if (!file.exists()) {
		file.createNewFile();
             }

	    fw = new FileWriter(file.getAbsoluteFile(), true);
	    bw = new BufferedWriter(fw);
	    bw.write(key);
	    bw.write(val);
	    //PrintWriter writer = new PrintWriter(filename, "UTF-8");
	    //writer.println(key);
	    //writer.println(val);
	    bw.close();
	    fw.close();
	} catch (IOException e) {
	   System.out.println("fileIo exception");
	}finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

  }

   private String getDateTimeStr(String timeStamp) {
      Long mili = Long.parseLong(timeStamp);

      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(mili);

      int mYear = calendar.get(Calendar.YEAR);
      int mMonth = calendar.get(Calendar.MONTH);
      int mDay = calendar.get(Calendar.DAY_OF_MONTH);
      int mHour = calendar.get(Calendar.HOUR_OF_DAY);
      int mMin = calendar.get(Calendar.MINUTE);
      mMin = mMin % 4;

      String str = Integer.toString(mYear)+"_" + Integer.toString(mMonth)+"_" + Integer.toString(mDay)+"_" + Integer.toString(mHour)+"_" + Integer.toString(mMin);
      System.out.println("The key: " + str);
      return str;
   }

   @Override
   public void cleanup() {
      for(Map.Entry<String, Integer> entry:counterMap.entrySet()){
         System.out.println(entry.getKey()+" : " + entry.getValue());
      }
   }

   @Override
   public void declareOutputFields(OutputFieldsDeclarer declarer) {
      declarer.declare(new Fields("randsen"));
   }
	
   @Override
   public Map<String, Object> getComponentConfiguration() {
      return null;
   }
	
}
