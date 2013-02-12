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
package asia.stampy.client.mina.disconnect;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import asia.stampy.client.message.disconnect.DisconnectMessage;
import asia.stampy.common.gateway.MessageListenerHaltException;
import asia.stampy.common.gateway.StampyMessageListener;
import asia.stampy.common.message.StompMessageType;
import asia.stampy.common.mina.AbstractMinaListenerTest;
import asia.stampy.server.message.receipt.ReceiptMessage;

@RunWith(MockitoJUnitRunner.class)
public class MinaDisconnectListenerAndInterceptorTest extends AbstractMinaListenerTest {

  private MinaDisconnectListenerAndInterceptor disconnect = new MinaDisconnectListenerAndInterceptor();

  @After
  public void after() throws Exception {
    disconnect.setReceiptId(null);
  }

  @Test
  public void testTypes() throws Exception {
    testTypes((StampyMessageListener) disconnect, new StompMessageType[] { StompMessageType.DISCONNECT,
        StompMessageType.RECEIPT });
  }

  @Test
  public void testIsForMessage() throws Exception {
    DisconnectMessage message = new DisconnectMessage();
    assertFalse(disconnect.isForMessage(message));

    message.getHeader().setReceipt("blah");
    assertTrue(disconnect.isForMessage(message));

    ReceiptMessage receipt = new ReceiptMessage("blah");
    assertFalse(disconnect.isForMessage(receipt));

    disconnect.interceptMessage(message, hostPort);
    assertTrue(disconnect.isForMessage(receipt));
  }

  @Test(expected = MessageListenerHaltException.class)
  public void testMessageReceived() throws Exception {
    disconnect.setGateway(clientGateway);
    disconnect.setReceiptId("test");

    ReceiptMessage message = new ReceiptMessage("blah");

    disconnect.setCloseOnDisconnectMessage(false);
    disconnect.messageReceived(message, hostPort);

    disconnect.setReceiptId("test");
    disconnect.setCloseOnDisconnectMessage(true);

    disconnect.messageReceived(message, hostPort);
  }

}