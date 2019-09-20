import java.net.{URL, HttpURLConnection, SocketTimeoutException}
import java.io.IOException
import net.liftweb.json._

class Carbooking() {

  implicit val formats = DefaultFormats
  case class CarOption(cat_type: String, price: Integer)
  case class CarResponse(supplier_id: String, pickup: String, dropoff: String, options: List[CarOption])

  def parseJsonOptions(rawjson: String): List {
    val m = json.extract[CarResponse]
  }

  def getRideData(supplier: String, pickup: String, dropoff: String): (String, Boolean) = {
    val param1 = "pickup"
    val param2 = "dropoff"
    val paramstring = "?" + param1 + "=" pickup + "&" + param2 + "=" + dropoff
    val querystring: String = "https://techtest.rideways.com/" + supplier + paramstring
    try {
      val content = getRequest(querystring)
      (content, true)
    } catch {
      case ioe: java.io.IOException =>  println("Error reading data from API.")
      case ste: java.net.SocketTimeoutException => println("API connection timed out. Please try again later.")
      val timeoutvalue = "Response timed out"
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