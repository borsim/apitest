/** Runs the API requesting application

*/
object Main {
  def main(args: Array[String]): Unit = {
    val pickup = args(0)
    val dropoff = args(1)
    val numPassengers = args(2)
    val carbooking = new Carbooking(numPassengers)
    val names = List("dave", "eric", "jeff")
    var carOptions: List[(String, Integer)] = Nil
    var outputOptions: List[(String, Integer)] = Nil
    for (name <- names) {
    	val returnedRequest = carbooking.getRideData(name, pickup, dropoff)
    	if (returnedRequest._2) {
    		val parsed = carbooking.parseJsonOptions(returnedRequest._1)
    		for (par <- parsed) carOptions =  par :: carOptions
    	} else println("Request " + name + " timed out.")
    }
    if (carOptions.length() > 0) {
	    carbooking.filterByPassengers(carOptions)
	    carOptions = carOptions.sortWith(_._3 < _._3)
	  }

    while (carOptions.length() != 0) {
    	outputOptions = carOptions.head :: outputOptions
    	carOptions.filter(_._1 == outputOptions.head._1)
    }
    for (oo <- outputOptions) {
    	println(oo._1 + " - " + oo._2 + " - " + oo._3)
    }
  }
}