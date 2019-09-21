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
I extended Main.scala, created Boot.scala and RestHelper.scala to create the API. Everything is fine except conversion of the results into a json payload. The serve function has been commented out to keep the rest of the program free of syntax errors.
Attempting to resolve that has taken longer than expected due to unfamiliarity with the topic but I hope to succeed by Sunday.

Sample request: .../api/car?pickup=3.410632,-2.157533&dropoff=3.410632,-2.157533&passengers=5
