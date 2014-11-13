import org.apache.spark._
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import org.apache.spark.storage.StorageLevel
import org.apache.spark.Logging
import org.apache.log4j.{Level, Logger}

object Elastic {
  def main(args: Array[String]) {
    val numThreads = 1
    val zookeeperQuorum = "localhost:2181"
    val groupId = "test"
    val topic = Array("test").map((_, numThreads)).toMap

    val sc = new SparkConf()
                 .setMaster("local[*]")
                 .setAppName("Elastic Search Indexer App")

    val ssc = new StreamingContext(sc, Seconds(10))
    //val ssc = new StreamingContext("local", "Elastic Search Indexer", Seconds(10))
    ssc.checkpoint("checkpoint")
    val logs = KafkaUtils.createStream(ssc, zookeeperQuorum, groupId, topic, StorageLevel.MEMORY_AND_DISK_SER).map(_._2)

    //val logs = ssc.socketTextStream("localhost", 9999, StorageLevel.MEMORY_AND_DISK_SER)
    //val logs = ssc.socketTextStream("localhost", 9999)

    logs.print()
    ssc.start()
    ssc.awaitTermination()

  }
}
