/*
 * Copyright (C) 2013 Burton Alexander
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 * 
 */
package asia.stampy.common.message;

import java.io.Serializable;

import asia.stampy.common.StampyLibrary;

/**
 * All Stampy implementations of STOMP messages must implement this interface.
 * 
 * @param <HDR>
 *          the generic type
 */
@StampyLibrary(libraryName="stampy-core")
public interface StampyMessage<HDR extends StampyMessageHeader> extends Serializable {

  /**
   * Gets the header.
   * 
   * @return the header
   */
  HDR getHeader();

  /**
   * Returns a STOMP-string representation of a {@link StampyMessage}.
   * 
   * @param validate
   *          if true message validation is executed
   * @return the string
   */
  String toStompMessage(boolean validate);

  /**
   * Gets the message type.
   * 
   * @return the message type
   */
  StompMessageType getMessageType();

  /**
   * Performs validation on the message.
   */
  void validate();

}
