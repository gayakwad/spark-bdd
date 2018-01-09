package com.gayakwad.sparkbdd

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith
@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("classpath:features/Average.feature"),
  glue = Array("classpath:steps"),
  tags = Array("@foo"),
  monochrome = true,
  plugin = Array("pretty",
    "html:target/cucumber",
    "json:target/cucumber/test-report.json",
    "junit:target/cucumber/test-report.xml")
)
class TestRunner {}