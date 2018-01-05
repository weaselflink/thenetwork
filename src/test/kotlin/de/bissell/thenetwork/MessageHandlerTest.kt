package de.bissell.thenetwork

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test

class MessageHandlerTest {

    @Test
    fun sendsMessages() {
        val topology = Topology()
        topology.addEdge(1, 2)
        topology.addEdge(2, 3)

        val messageHandler = MessageHandler(topology)

        assertThat(messageHandler.areMessagesEnroute()).isFalse()

        messageHandler.send(2, 1, "payload1")
        messageHandler.send(2, 0, "payload2")

        assertThat(messageHandler.messages).containsExactly(
                Message(1, Socket(2, 1), Socket(3, 0), "payload1", 0, 5),
                Message(2, Socket(2, 0), Socket(1, 0), "payload2", 0, 5)
        )

        assertThat(messageHandler.areMessagesEnroute()).isTrue()
    }

    @Test
    fun updatesMessages() {
        val topology = Topology()
        topology.addEdge(1, 2)
        topology.addEdge(2, 3)

        val messageHandler = MessageHandler(topology)

        assertThat(messageHandler.areMessagesEnroute()).isFalse()

        messageHandler.send(1, 0, "payload1")

        assertThat(messageHandler.messages).containsExactly(
                Message(1, Socket(1, 0), Socket(2, 0), "payload1", 0, 5)
        )
        assertThat(messageHandler.areMessagesEnroute()).isTrue()

        messageHandler.update(5)

        assertThat(messageHandler.messages).containsExactly(
                Message(2, Socket(2, 1), Socket(3, 0), "payload1", 5, 10)
        )
        assertThat(messageHandler.areMessagesEnroute()).isTrue()

        messageHandler.update(10)

        assertThat(messageHandler.messages).isEmpty()
        assertThat(messageHandler.areMessagesEnroute()).isFalse()
    }

    @Test
    fun knowsNeighbours() {
        val topology = Topology()
        topology.addEdge(1, 2)
        topology.addEdge(2, 3)

        val messageHandler = MessageHandler(topology)

        assertThat(messageHandler.sockets(1)).containsExactly(0)
        assertThat(messageHandler.sockets(2)).containsExactly(0, 1)
        assertThat(messageHandler.sockets(3)).containsExactly(0)
    }
}