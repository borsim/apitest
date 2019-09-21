package bootstrap.liftweb

import net.liftweb.http.LiftRules
import com.borsim.CarAPI

class Boot
{
  def boot
  {
    LiftRules.dispatch.append(CarAPI)
  }
}