package steps

import com.gayakwad.sparkbdd.Aggregate._
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import cucumber.api.DataTable
import cucumber.api.scala.{EN, ScalaDsl}
import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.sql.types._
import org.scalatest.FunSuite

import scala.collection.JavaConversions._


class SalesAggregateSteps extends FunSuite with DataFrameSuiteBase with ScalaDsl with EN {
  var salesTransactionsDF: DataFrame = _
  var aggregateSalesDF: DataFrame = _

  Given("""^the following sales transactions:""") { (salesTransactionDataTable: DataTable) =>
    beforeAll() // explicitly invoking spark context creation
    salesTransactionsDF = convertToSalesTransactionsDF(salesTransactionDataTable)
  }

  When("""^I calculate aggregates""") {
    aggregateSalesDF = aggregate(salesTransactionsDF)
  }

  Then("""^the result is:""") { (salesDataTable: DataTable) =>
    val expectectSales = convertToExpectedAggregateSalesDF(salesDataTable)
    assertDataFrameEquals(aggregateSalesDF.sort("date"), expectectSales.sort("date"))
  }

  def convertToSalesTransactionsDF(salesDataTable: DataTable): DataFrame = {
    val sales: java.util.List[java.util.Map[String, String]] = salesDataTable.asMaps(classOf[String], classOf[String])
    val rows = sales.map(x => Row(x.get("date"), x.get("itemId").toInt, x.get("quantity").toInt)).toList
    val rowRDD = sc.parallelize(rows)
    val expectedSchema = List(
      StructField("date", StringType, nullable = true),
      StructField("itemId", IntegerType, nullable = true),
      StructField("quantity", IntegerType, nullable = true)
    )
    val schema = StructType(expectedSchema)
    val expectedDF = spark.createDataFrame(rowRDD, schema)
    expectedDF
  }

  def convertToExpectedAggregateSalesDF(expectedAggregateSalesDataTable: DataTable): DataFrame = {
    val expectedSales: java.util.List[java.util.Map[String, String]] = expectedAggregateSalesDataTable.asMaps(classOf[String], classOf[String])
    val rows = expectedSales.map(x => Row(x.get("date"), x.get("itemId").toInt, x.get("total_quantity").toLong)).toList
    val rowRDD = sc.parallelize(rows)
    val expectedSchema = List(
      StructField("date", StringType, nullable = true),
      StructField("itemId", IntegerType, nullable = true),
      StructField("total_quantity", LongType, nullable = true)
    )
    val schema = StructType(expectedSchema)
    val expectedDF = spark.createDataFrame(rowRDD, schema)
    expectedDF
  }

}
