package com.borsim
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.OkResponse
import net.liftweb.util.Helpers.AsInt
import net.liftweb.http.JsonResponse
import net.liftweb.json.JsonDSL._
import net.liftweb.json.JsonAST
import net.liftweb.json.Extraction

object CarAPI extends RestHelper {


  serve {
    case Get("api" :: "car" :: _, req) => {
      val pickupS = req.param("pickup") openOr ""
      val dropoffS = req.param("dropoff") openOr ""
      val numPassengers = (req.param("passengers") openOr "0").toInt
      val vehicleList = Main.listAvailableVehicles(pickupS, dropoffS, numPassengers)

      val jsonresponse = Extraction.decompose(vehicleList)
      val boxedresponse = JsonResponse(jsonresponse)
      println(boxedresponse)
      boxedresponse
    }
  }
}