package de.bissell.thenetwork

class Topology {

    private val nodeSockets: MutableMap<Int, Int> = mutableMapOf()
    private val edges: MutableList<Connection> = mutableListOf()

    fun addEdge(fromNode: Int, toNode: Int) {
        val fromSocket = nodeSockets[fromNode] ?: 0
        nodeSockets[fromNode] = fromSocket.plus(1)
        val toSocket = nodeSockets[toNode] ?: 0
        nodeSockets[toNode] = toSocket.plus(1)

        edges.add(Connection(Socket(fromNode, fromSocket), Socket(toNode, toSocket)))
    }

    fun nodes() = nodeSockets.keys

    fun nodeSockets(nodeId: Int): List<Int> {
        val connections = nodeSockets[nodeId] ?: 0
        if (connections < 1) {
            return emptyList()
        }

        return IntRange(0, connections - 1).toList()
    }

    fun connectedTo(fromNode: Int, fromSocket: Int): Socket? {
        val socketToFind = Socket(fromNode, fromSocket)

        return edges
                .filter { it.from.equals(socketToFind) || it.to.equals(socketToFind) }
                .map {
                    if (it.from.equals(socketToFind)) {
                        it.to
                    } else {
                        it.from
                    }
                }
                .firstOrNull()
    }
}

data class Connection(val from: Socket, val to: Socket)

data class Socket(val node: Int, val socket: Int)