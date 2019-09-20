/** Runs the API requesting application

*/
object Main {
  def main(args: Array[String]): Unit = {
    val pickup = args[0]
    val dropoff = args[1]
    val numPassengers = args[2]
    val carbooking = new Carbooking(numPassengers)
    val names = List("dave", "eric", "jeff")
    for (name <- names) {
    	val returnedRequest = carbooking.getRideData(name, pickup, dropoff)
    	if (returnedRequest._2) {
    		val parsed = carbooking.parseJsonOptions(returnedRequest._1)
    	} else println("Request " + name + " timed out.")
    }
      
  }
}