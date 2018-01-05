package de.bissell.thenetwork

data class Message(
        val id: Int,
        val source: Socket,
        val target: Socket,
        val payload: String,
        val startTime: Int,
        val endTime: Int
)

class NodeMessageHandler(private val id: Int, private val messageHandler: MessageHandler) {

    fun send(socket: Int, payload: String) {
        messageHandler.send(id, socket, payload)
    }

    fun sockets() = messageHandler.sockets(id)
}

class MessageHandler(private val topology: Topology) {

    val messageRuntime = 5
    val messages = mutableListOf<Message>()
    val nodes: MutableMap<Int, Node>

    var nextMessageId = 1
    var currentClock = 0

    init {
        nodes = mutableMapOf()

        topology.nodes().forEach {
            nodes[it] = Node(NodeMessageHandler(it, this))
        }
    }

    fun send(node: Int, socket: Int, payload: String) {
        val source = Socket(node, socket)
        val target = topology.connectedTo(node, socket)

        if (target != null) {
            messages.add(createMessage(source, target, payload))
            nextMessageId++
        }
    }

    private fun createMessage(source: Socket, target: Socket, payload: String) =
            Message(nextMessageId, source, target, payload, currentClock, currentClock + messageRuntime)

    fun sockets(id: Int) = topology.nodeSockets(id)

    fun update(clock: Int) {
        currentClock = clock

        updateMessages()
    }

    fun updateMessages() {
        val arrivingMessages: List<Message> = messages
                .filter { it.endTime <= currentClock }
                .sortedBy { it.id }
                .sortedBy { it.endTime }

        messages.removeAll(arrivingMessages)

        arrivingMessages.forEach {
            val targetNode = nodes[it.target.node]
            targetNode?.receive(it.target.socket, it.payload)
        }
    }

    fun areMessagesEnroute() = !messages.isEmpty()
}
