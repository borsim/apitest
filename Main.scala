/** Runs the API requesting application

*/
object Main {
  def main(args: Array[String]): Unit = {
		val carbooking = new Carbooking();
    val pickup = args[0]
    val dropoff = args[1]
    val numPass = args[2]
    val names = {"dave", "eric", "jeff"}
    for (name <- names) {
    	val returnedRequest = carbooking.getRideData(name, pickup, dropoff)
    	if (returnedRequest._2) {
    		// TODO
    	} else println("Request " + name + " timed out.")
    }
      
  }
}