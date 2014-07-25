package com.mikeycaine

import org.scalatest.FunSuite

/**
 * Created by Mike on 24/07/2014.
 */
class EmailTest extends org.scalatest.FunSuite {
  test("Email with valid address") {
    val email = Email("mikey.caine@gmail.com")
    assert(email.address != null)
  }

  test("Email with invalid address") {
    intercept[IllegalArgumentException] {
      val email = Email("mikey.caine")
    }
  }

}

