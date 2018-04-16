# apache_storm_impl

# Best tutorials: 
* http://storm.apache.org/releases/current/Tutorial.html

* http://www.haroldnguyen.com/blog/2015/01/setting-up-storm-and-running-your-first-topology/

* https://github.com/apache/storm/tree/v1.0.1

# Imp Points: 
## Requirements: 
Java 1.8  	on all nimbus and supervisor

Python 2.7:    sudo apt-get install python

Zookeeper : 	 sudo apt-get install zookeeperd

Storm     :    download storm 1.1 release

change its storm.yaml

	How to start: 
			Zookeeper: /etc/zookeeper/conf/
			To start te zookeeper service:	sudo service zookeeper start

			bin/zkServer.sh start 
			nohup ./storm nimbus &
			nohup ./storm supervisor &
			nohup ./storm ui &
							
			dataDir=/home/ubuntu/Storm/zookeeper/
			clientPort=2181
			bin/zkServer.sh start   	//starting zookeeper 
			bin/zkCli.ssh               //start cli
			bin/zkServer.sh stop		//stop zookeeper server

	logs:
		on nimbus machine:      apache-storm-1.1.0/logs    nimbus.log
		on supervisor machines: apache-storm-1.1.0/logs/workers-artifacts/randomsen-8-1492492775/6701  	worker.log
								This is where sysout come of both spout and bolt.


	to build the jar, do the following:

		1. Create class files                       :  javac -cp apache-storm-1.1.0/lib/storm-core-1.1.0.jar:apache-storm-1.1.0/lib/slf4j-api-1.7.21.jar RandomSentenceTopology.java RandomSentenceBolt.java RandomSentenceSpout.java 

		2. Package all the class files into a jar   :   jar -cf topology.jar *

		3. Copy								  		: cp topology.jar /tmp/

		4. kill the currently running topology      : /home/ubuntu/Storm/apache-storm-1.1.0/bin/storm kill randomsen_1

		5. deploy new jar					        : /home/ubuntu/Storm/apache-storm-1.1.0/bin/     ./storm jar /tmp/topology.jar RandomSentenceTopology

nimbus:     ssh -v -i xxx.pem ubuntu@xx.xx.xx.xx

worker_1: 	ssh -v -i xxx.pem ubuntu@xx.xx.xx.xx

worker_2: 	ssh -v -i xxx.pem ubuntu@xx.xx.xx.xx

How to start: 
			bin/zkServer.sh start 
			bin/storm nimbus
			bin/storm supervisor
			bin/storm ui


##### currently using slave1, slave2, nimbus
##### nimbus and zookeeper on one node, and supervisor on other
##### Supervisors should point to zookeeper and nimbus.seed
##### code is in /home/ubuntu/Storm/storm/examples/storm-starter


1. go to /Storm/storm/examples/storm-starter
2. To make jar of the Tolpology and all:   mvn clean install -DskipTests=true
				The jar gets created in /Storm/storm/examples/storm-starter/target
3. cd Storm/storm/examples/storm-starter/target
4. cp storm-starter-2.0.0-SNAPSHOT.jar /tmp
5. cd Storm/apache-storm-1.1.0/bin
6. ./storm jar /Storm/storm/examples/storm-starter/target/storm-starter-2.0.0-SNAPSHOT.jar org.apache.storm.starter.RandomSentenceTopology

 storm kill randomsen



Zookeeper: /etc/zookeeper/conf/
sudo service zookeeper start
zooinspector 
netstat -an | grep 2181


./storm jar /tmp/top.jar RandomSentenceTopology

tail -f ../logs/workers/f0286b79-fd81-41df-8fa5-6efe71b23221/artifacts/worker.log
tail -f ../logs/workers-artifacts/randomsen-1-1492616369/6700/worker.log
