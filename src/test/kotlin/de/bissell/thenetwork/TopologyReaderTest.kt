package de.bissell.thenetwork

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test
import java.nio.file.Paths

class TopologyReaderTest {

    @Test
    fun readsTopology() {
        val topologyReader = TopologyReader()
        val topologyPath = Paths.get(this.javaClass.classLoader.getResource("topology.txt").toURI())

        val topology = topologyReader.read(topologyPath)

        assertThat(topology.nodes()).containsExactlyInAnyOrder(1, 2, 3, 4)
        assertThat(topology.connectedTo(1, 0)).isEqualTo(Socket(2, 0))
        assertThat(topology.connectedTo(2, 0)).isEqualTo(Socket(1, 0))
        assertThat(topology.connectedTo(2, 1)).isEqualTo(Socket(3, 0))
        assertThat(topology.connectedTo(2, 2)).isEqualTo(Socket(4, 0))
        assertThat(topology.connectedTo(4, 1)).isEqualTo(Socket(1, 1))
    }
}