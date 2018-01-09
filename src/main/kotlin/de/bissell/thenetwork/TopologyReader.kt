package de.bissell.thenetwork

import java.nio.file.Files
import java.nio.file.Path

class TopologyReader {

    fun read(path: Path): Topology {
        val topology = Topology()

        Files.readAllLines(path)
                .filter { !it.isBlank() }
                .filter { !it.startsWith("#") }
                .map { parseEdge(it) }
                .forEach { topology.addEdge(it.first, it.second) }

        return topology
    }

    private fun parseEdge(input: String): Pair<Int, Int> {
        val nodeIds = input.split("-")
                .map { it.trim() }
                .map { it.toInt() }

        if (nodeIds.size != 2) {
            throw IllegalArgumentException("Could not parse edge: \"${input}\"")
        }

        return Pair(nodeIds[0], nodeIds[1])
    }
}