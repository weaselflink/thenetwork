package de.bissell.thenetwork

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test
import javax.script.ScriptContext
import javax.script.ScriptEngineManager

class ScriptEngineTest {

    @Test
    fun usesScriptEngine() {
        val scriptEngineManager = ScriptEngineManager()
        val scriptEngine = scriptEngineManager.getEngineByExtension("js")
        assertThat(scriptEngine).isNotNull()

        scriptEngine.context.setAttribute("c", 4, ScriptContext.GLOBAL_SCOPE)
        val bindings = scriptEngine.getBindings(ScriptContext.GLOBAL_SCOPE)

        bindings.put("test", ScriptEngineTest())

        scriptEngine.eval("function mult(a) { return a * 2; }", bindings)
        scriptEngine.eval("var b = mult(3)", bindings)

        assertThat(scriptEngine.eval("b", bindings)).isEqualTo(6L)
        assertThat(scriptEngine.eval("c", bindings)).isEqualTo(4)
        assertThat(scriptEngine.eval("test.mult2(c)", bindings)).isEqualTo(20)
    }

    fun mult2(a: Int) = a * 5
}