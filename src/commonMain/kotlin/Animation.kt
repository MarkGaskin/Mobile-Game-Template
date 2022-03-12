import com.soywiz.klock.seconds
import com.soywiz.korge.animate.Animator
import com.soywiz.korge.animate.animateSequence
import com.soywiz.korge.tween.get
import com.soywiz.korge.view.Stage
import com.soywiz.korge.view.View
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korma.interpolation.Easing
import io.github.aakira.napier.Napier
import com.soywiz.korge.view.position
import kotlin.math.sin
import kotlin.math.cos
import kotlin.random.Random

data class Animator (
    var isAnimating: Boolean = false) {

    fun startAnimating() {
        isAnimating = true
    }

    fun stopAnimating() {
        isAnimating = false
    }
}


