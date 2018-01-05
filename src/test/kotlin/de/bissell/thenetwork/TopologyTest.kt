package de.bissell.thenetwork

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test

class TopologyTest {

    @Test
    fun calculatesSocketIds() {
        val topology = Topology()
        topology.addEdge(1, 2)
        topology.addEdge(2, 3)
        topology.addEdge(3, 4)
        topology.addEdge(3, 5)

        assertThat(topology.nodeSockets(1)).containsExactly(0)
        assertThat(topology.nodeSockets(2)).containsExactly(0, 1)
        assertThat(topology.nodeSockets(3)).containsExactly(0, 1, 2)
        assertThat(topology.nodeSockets(4)).containsExactly(0)
        assertThat(topology.nodeSockets(5)).containsExactly(0)
    }

    @Test
    fun calculatesSockets() {
        val topology = Topology()
        topology.addEdge(1, 2)
        topology.addEdge(2, 3)

        assertThat(topology.connectedTo(1, 0)).isEqualTo(Socket(2, 0))
        assertThat(topology.connectedTo(2, 0)).isEqualTo(Socket(1, 0))
        assertThat(topology.connectedTo(2, 1)).isEqualTo(Socket(3, 0))
        assertThat(topology.connectedTo(3, 0)).isEqualTo(Socket(2, 1))

        assertThat(topology.connectedTo(3, 1)).isNull()
    }

}