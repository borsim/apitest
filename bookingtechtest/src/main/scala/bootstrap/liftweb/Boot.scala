package com.borsim
import net.liftweb.http.LiftRules

class Boot
{
  def boot
  {
    LiftRules.dispatch.append(CarAPI)
  }
}