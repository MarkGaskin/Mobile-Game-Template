import com.soywiz.korge.input.*
import com.soywiz.korge.ui.textAlignment
import com.soywiz.korge.ui.textColor
import com.soywiz.korge.ui.textSize
import com.soywiz.korge.ui.uiText
import com.soywiz.korge.view.*
import com.soywiz.korge.view.onClick
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.text.TextAlignment
import io.github.aakira.napier.Napier

enum class PopupState {
    NONE,
    TUTORIAL,
    RESTART,
    GAMEOVER
}

data class Popup (
    var state: PopupState = PopupState.NONE,
    var restartPopupContainer: Container = Container()){

    fun showRestart () { state = PopupState.RESTART }
    fun showTutorial () { state = PopupState.TUTORIAL }
    fun showGameOver () { state = PopupState.GAMEOVER }
    fun hidePopup () { state = PopupState.NONE }

    fun hasPopup () : Boolean { return state == PopupState.NONE}

}



fun Container.showGameOver(onGameOver: () -> Unit) = container {
    popup.showGameOver()
    Napier.d("Showing Restart Container...")
    fun restart() {
        this@container.removeFromParent()
        onGameOver()
    }

    val restartBackground = roundRect(gameView.gameField.gameWidth, gameView.gameField.gameHeight, 5, fill = Colors["#aaa6a4cc"]) {
        centerXOn(gameView.gameField.container)
        centerYOn(gameView.gameField.container)
    }
    val bgRestartContainer = container {
        roundRect(gameView.gameField.gameWidth / 2, gameView.gameField.gameHeight / 4, 25, fill = Colors["#bbd0f2"]) {
            centerXOn(restartBackground)
            centerYOn(restartBackground)
        }
        uiText("Restart?") {
            centerXOn(restartBackground)
            centerYOn(restartBackground)

            textAlignment = TextAlignment.MIDDLE_CENTER
            textSize = 30.0
            textColor = RGBA(0, 0, 0)
            onOver { textColor = RGBA(90, 90, 90) }
            onOut { textColor = RGBA(0, 0, 0) }
            onDown { textColor = RGBA(120, 120, 120) }
            onUp { textColor = RGBA(120, 120, 120) }
        }
        onUp {
            Napier.d("Restart Button - YES Clicked")
            popup.hidePopup()
            restart()
            this@container.removeFromParent()
        }
        onClick {
            Napier.d("Restart Button - YES Clicked")
            popup.hidePopup()
            restart()
            this@container.removeFromParent()
        }
    }
    val gameOverText = container {
        alignBottomToTopOf(bgRestartContainer, 8)
        centerXOn(bgRestartContainer)
        text("Out of moves") {
            alignment = TextAlignment.MIDDLE_CENTER
            textSize = 50.0
            color = RGBA(0, 0, 0)
        }
    }
}



fun Container.showRestart(onRestart: () -> Unit) = container {
    popup.showRestart()
    Napier.d("Showing Restart Container...")
    fun restart() {
        this@container.removeFromParent()
        onRestart()
    }

    val restartBackground = roundRect(gameView.gameField.gameWidth, gameView.gameField.gameHeight, 5, fill = Colors["#aaa6a4cc"]) {
        centerXOn(gameView.gameField.container)
        centerYOn(gameView.gameField.container)
        onClick {
            Napier.d("Restart Button - NO Clicked")
            popup.hidePopup()
            this@container.removeFromParent()
        }
    }
    val bgRestartContainer = container {
        roundRect(gameView.gameField.gameWidth / 2, gameView.gameField.gameHeight / 4, 25, fill = Colors["#bbd0f2"]) {
            centerXOn(restartBackground)
            centerYOn(restartBackground)
        }
        uiText("Restart?") {
            centerXOn(restartBackground)
            centerYOn(restartBackground)

            textAlignment = TextAlignment.MIDDLE_CENTER
            textSize = 30.0
            textColor = RGBA(0, 0, 0)
            onOver { textColor = RGBA(90, 90, 90) }
            onOut { textColor = RGBA(0, 0, 0) }
            onDown { textColor = RGBA(120, 120, 120) }
            onUp { textColor = RGBA(120, 120, 120) }
        }
        onUp {
            Napier.d("Restart Button - YES Clicked")
            popup.hidePopup()
            restart()
            this@container.removeFromParent()
        }
        onClick {
            Napier.d("Restart Button - YES Clicked")
            popup.hidePopup()
            restart()
            this@container.removeFromParent()
        }
    }
}