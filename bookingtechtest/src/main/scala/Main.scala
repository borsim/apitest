package com.borsim
/** Runs the API requesting application

*/
object Main {
  def main(args: Array[String]): Unit = {
    val pickup = args(0)
    val dropoff = args(1)
    val numPassengers = args(2).toInt
    val carbooking = new Carbooking()
    val names = List("dave", "eric", "jeff")
    var carOptions: List[(String, String, Integer)] = Nil
    var outputOptions: List[(String, String, Integer)] = Nil
    for (name <- names) {
    	val returnedRequest = carbooking.getRideData(name, pickup, dropoff)
    	if (returnedRequest._2) {
    		val parsed = carbooking.parseJsonOptions(returnedRequest._1)
    		for (par <- parsed) carOptions =  par :: carOptions
    	} else println("Request " + name + " timed out.")
    }
    if (carOptions.length > 0) {
	    val filteredCarOptions = carbooking.filterByPassengers(numPassengers, carOptions)
	    val sortedCarOptions = filteredCarOptions.sortWith(_._3 < _._3)
        carOptions = sortedCarOptions
	  }

    while (carOptions.length != 0) {
    	outputOptions = carOptions.head :: outputOptions
        val donewithtype: String = carOptions.head._1
    	val newCarOptions = carOptions.filterNot(_._1 == donewithtype)
        carOptions = newCarOptions
    }
    for (oo <- outputOptions) {
    	println(oo._1 + " - " + oo._2 + " - " + oo._3)
    }
    if (outputOptions.length == 0) println("No suitable vehicle found for this number of passengers.")
  }
}