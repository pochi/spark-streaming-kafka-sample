package pochi.spark.streaming

// About Spark
import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

// About ElasticSearch
import org.elasticsearch.hadoop.mr.EsInputFormat
import org.elasticsearch.hadoop.mr.EsOutputFormat
import org.elasticsearch.hadoop.cfg.ConfigurationOptions

// About Hadoop
import org.apache.hadoop.mapred.{FileOutputCommitter, FileOutputFormat, JobConf, OutputFormat}
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{MapWritable, Text, NullWritable}

object ElasticSearchConfig {
  def setupElasticSearchOnSparkContext(sc: SparkContext,
                                       elasticSearchResource: String,
                                       elasticSearchNodes: Option[String] = None,
                                       elasticSearchPartition: Boolean = false) = {
    println("ElasticResource: " + elasticSearchResource)
    println("ElasticNodes:" + elasticSearchNodes)
    val jobConf = new JobConf(sc.hadoopConfiguration)
    jobConf.set("mapred.output.format.class","org.elasticsearch.hadoop.mr.EsOutputFormat")
    jobConf.setOutputCommitter(classOf[FileOutputCommitter])
    jobConf.set(ConfigurationOptions.ES_RESOURCE, elasticSearchResource)
    elasticSearchNodes match {
      case Some(node) => jobConf.set(ConfigurationOptions.ES_NODES, node)
      case _ => // Skip it
    }

    if (elasticSearchPartition) {
      jobConf.set("es.sparkpartition", "tru")
    }

    FileOutputFormat.setOutputPath(jobConf, new Path("-"))
    jobConf
  }
}
