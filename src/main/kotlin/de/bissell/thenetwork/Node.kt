package de.bissell.thenetwork

class Node(val nodeMessageHandler: NodeMessageHandler) {

    fun sockets(): List<Int> {
        return nodeMessageHandler.sockets()
    }

    fun receive(socket: Int, payload: String) {
        sockets()
                .filter { it != socket }
                .forEach { send(it, payload) }
    }

    fun send(socket: Int, payload: String) {
        nodeMessageHandler.send(socket, payload)
    }
}
