package com.mikeycaine

/**
 * Created by Mike on 24/07/2014.
 */
case class Email (val address: String) {
 val matcher = """([\w\.]+)@([\w\.]+)""".r.pattern.matcher(address)
  if (!matcher.matches()) {
    throw new IllegalArgumentException("Address " + address + " is invalid")
  }
}
