package steps

import com.gayakwad.sparkbdd.Calculator
import cucumber.api.Scenario
import cucumber.api.scala.{EN, ScalaDsl}
import org.junit.Assert._

class AverageSteps extends ScalaDsl with EN {
  val calc = new Calculator

  When("""^I add (\d+) and (\d+)$""") { (arg1: Double, arg2: Double) =>
    calc push arg1
    calc push arg2
    calc push "+"
  }

  Then("^the result is (\\d+)$") { expected: Double =>
    assertEquals(expected, calc.value, 0.001)
  }

  Before("~@foo") { scenario: Scenario =>
    println("Runs before scenarios *not* tagged with @foo")
  }
}

