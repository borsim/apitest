# apitest
API mini program done for a technical test

# Setup
Code is done in Scala and dependency on the Lift framework is managed through sbt

- Clone the repository 
- cd to bookingtechtest
- sbt

# Part 1
run 1.123,5.567 2.234,3.345 3
-> searches all 3 apis with the given arguments as pickup, dropoff and passengers
-> lists the cheapest of all suitable vehicle types as "$type - $supplier - $price\n"

Notes: 
- Vehicle types are not subsorted by price to return the cheapest vehicle type first
- 2s timeout
- No exception when no available car is large enough
- No reliance on there always being some kind of data

# Part 2
To run the server:
- sbt
- jetty:start

I extended Main.scala, created Boot.scala and RestHelper.scala to have an API.
It supports GET with parameters in the same way as the command line version.

Sample request: 
http://localhost:8080/api/car?pickup=3.410632,-2.157533&dropoff=3.410632,-2.157533&passengers=5
