
/* Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.storm.starter.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class RandomSentenceSpout extends BaseRichSpout {
  private static final Logger LOG = LoggerFactory.getLogger(RandomSentenceSpout.class);
  int lineIndex = 0;
  int fileIndex = 0;
  SpoutOutputCollector _collector;
  Random _rand;


  @Override
  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    _collector = collector;
    _rand = new Random();
  }

  protected String sentence(String input) {
    return input;
  }

  @Override
  public void ack(Object id) {
  }

  @Override
  public void fail(Object id) {
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("randsen"));
  }

  @Override
  public void nextTuple() {
    Utils.sleep(100);
    String filePath[] = {"/home/ubuntu/deletions/deletions.csv-00000-of-00020",
			"/home/ubuntu/deletions/deletions.csv-00001-of-00020",
			"/home/ubuntu/deletions/deletions.csv-00002-of-00020",
			"/home/ubuntu/deletions/deletions.csv-00003-of-00020",
			"/home/ubuntu/deletions/deletions.csv-00004-of-00020"};
        System.out.println("**In Spout**");   
	writeToFile("**In Spout**");
        Utils.sleep(100);
        String line = null;
	//for (String filename : filePath) {
        String filename = "/home/ubuntu/deletions/deletions.csv-00000-of-00020"; 
	try
         {
            //FileWriter fw =  ew FileWriter(file.getAbsoluteFile(), true);

            BufferedReader bufferReader = new BufferedReader(new FileReader(filename));
            while((line = bufferReader.readLine()) != null)
            {
                if(line!=null)
                {
                    System.out.println(line);
		    writeToFile(line);
                    _collector.emit(new Values(line));
                }
            }

         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
//	}
        System.out.println("Emitting Next Tuple..");
  }

 private void writeToFile(String key) {
       FileWriter fw = null;
	BufferedWriter bw = null;
	 try{
            String filename = "/home/ubuntu/Storm/my_logs/nimbus.txt";
	    File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
             }

            fw = new FileWriter(file.getAbsolutePath(), true);
	    bw = new BufferedWriter(fw);
            // PrintWriter writer = new PrintWriter(filename, "UTF-8");
            
	   bw.write(key);
            if (bw != null)
		bw.close();
	    if (fw != null)
		fw.close();
        } catch (Exception e) {
           System.out.println("fileIo exception");
//	   if (bw != null)
//		bw.close();
//	   if (fw != null)
//		fw.close();
        }
  }


  private String getDataLine(String filename) {

    BufferedReader br = null;
    FileReader fr = null;

    try {
        fr = new FileReader(filename);
        br = new BufferedReader(fr);
        String sCurrentLine;
        int i = 0;
        while ((sCurrentLine = br.readLine()) != null) {
          if (i == lineIndex) {
            lineIndex++;

            if (br != null)
              br.close();
            if (fr != null)
              fr.close();
		System.out.println(sCurrentLine);
            return sCurrentLine;
          }
        }
        lineIndex = 0;
        fileIndex++;

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null)
          br.close();
        if (fr != null)
          fr.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    return "";
}
}
/*

  // Add unique identifier to each tuple, which is helpful for debugging
  public static class TimeStamped extends RandomSentenceSpout {
    private final String prefix;

    public TimeStamped() {
      this("");
    }

    public TimeStamped(String prefix) {
      this.prefix = prefix;
    }

    protected String sentence(String input) {
      return prefix + currentDate() + " " + input;
    }

    private String currentDate() {
      return new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss.SSSSSSSSS").format(new Date());
    }
  }*/
