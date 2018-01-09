package de.bissell.thenetwork

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test
import javax.script.ScriptEngineManager

class ScriptEngineTest {

    @Test
    fun usesScriptEngine() {
        val scriptEngineManager = ScriptEngineManager()
        val scriptEngine = scriptEngineManager.getEngineByExtension("js")
        assertThat(scriptEngine).isNotNull()

        scriptEngine.put("c", 4)
        scriptEngine.put("test", ScriptEngineTest())

        scriptEngine.eval("function mult(a) { return a * 2; }")
        scriptEngine.eval("var b = mult(3)")

        assertThat(scriptEngine.eval("b")).isEqualTo(6L)
        assertThat(scriptEngine.eval("c")).isEqualTo(4)
        assertThat(scriptEngine.eval("test.mult2(c)")).isEqualTo(20)
    }

    fun mult2(a: Int) = a * 5
}