package com.borsim

import java.net.{URL, HttpURLConnection, SocketTimeoutException}
import java.io.IOException
import net.liftweb.json

class Carbooking(numPassengersInput: Integer) {

  val numPassengers = numPassengersInput

  implicit val formats = DefaultFormats
  case class CarOption(cat_type: String, price: Integer)
  case class CarResponse(supplier_id: String, pickup: String, dropoff: String, options: List[CarOption])

  def filterByPassengers(optionslist: List[(String, String, Integer)]): List[(String, String, Integer)] = {
    optionslist.filter(filterTest(_))
  }
  def filterTest(inp: (String, String, Integer)): Boolean = {
    // If the list of capacity types get large an array might be warranted but that sounds unreasonable.
    val capacities = List((4, List("STANDARD", "EXECUTIVE", "LUXURY")), 
                      (6, List("PEOPLE_CARRIER","LUXURY_PEOPLE_CARRIER")),
                      (16, List("MINIBUS")))
    var typeid: Integer = -1
    for (c <- capacities) {
      if (c._2.contains(inp._1)) typeid = c._2.indexOf(inp._1)
    }
    if (numPassengers <= capacities(typeid)._1) true
    else false
  }

  def parseJsonOptions(rawjson: String): List[(String, String, Integer)] = {
    val jsonobj = json.extract[CarResponse]
    var listWithSupplier: List[(String, String, Integer)] = Nil
    for (o <- jsonobj.options) {
      val newTuple = (o._1, jsonobj.supplier, o._2)
      listWithSupplier = newTuple :: listWithSupplier
    }
    listWithSupplier
  }

  def getRideData(supplier: String, pickup: String, dropoff: String): (String, Boolean) = {
    val param1 = "pickup"
    val param2 = "dropoff"
    val timeoutvalue = "Response timed out"
    val paramstring = "?" + param1 + "=" + pickup + "&" + param2 + "=" + dropoff
    val querystring: String = "https://techtest.rideways.com/" + supplier + paramstring
    try {
      val content = getRequest(querystring)
      (content, true)
    } catch {
      case ioe: java.io.IOException => {
        println("Error reading data from API.")
        (timeoutvalue, false) 
        }
      case ste: java.net.SocketTimeoutException => {
        println("API connection timed out. Please try again later.")
        (timeoutvalue, false)
      }
      
      (timeoutvalue, false)
    }
  }

  /**
    * @param url The full URL to connect to.
    * @param connectTimeout Sets a specified timeout value, in milliseconds,
    * to be used when opening a communications link to the resource referenced
    * by this URLConnection. If the timeout expires before the connection can
    * be established, a java.net.SocketTimeoutException
    * is raised. A timeout of zero is interpreted as an infinite timeout.
    * @param readTimeout If the timeout expires before there is data available
    * for read, a java.net.SocketTimeoutException is raised. A timeout of zero
    * is interpreted as an infinite timeout.
    * @param requestMethod Defaults to "GET"
    * @example get("http://www.example.com/getInfo")
    * @example get("http://www.example.com/getInfo", 5000)
    * @example get("http://www.example.com/getInfo", 5000, 5000)
    */
  @throws(classOf[java.io.IOException])
  @throws(classOf[java.net.SocketTimeoutException])
  def getRequest(url: String,
          connectTimeout: Int = 2000,
          readTimeout: Int = 2000,
          requestMethod: String = "GET") = {
      val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
      connection.setConnectTimeout(connectTimeout)
      connection.setReadTimeout(readTimeout)
      connection.setRequestMethod(requestMethod)
      val inputStream = connection.getInputStream
      val content = io.Source.fromInputStream(inputStream).mkString
      if (inputStream != null) inputStream.close
      content
  }
}