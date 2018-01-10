package pipeline

import org.apache.spark.sql.DataFrame

object Aggregate {

  def aggregate(salesDF: DataFrame): DataFrame = {
    salesDF.groupBy("date", "itemId").sum("quantity").withColumnRenamed("sum(quantity)", "total_quantity")
  }

}
