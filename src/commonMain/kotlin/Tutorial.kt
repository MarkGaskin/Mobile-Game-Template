import com.soywiz.korge.input.*
import com.soywiz.korge.service.storage.NativeStorage
import com.soywiz.korge.ui.textAlignment
import com.soywiz.korge.ui.textColor
import com.soywiz.korge.ui.textSize
import com.soywiz.korge.ui.uiText
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korio.async.ObservableProperty
import io.github.aakira.napier.Napier

enum class TutorialType(val index: Int,
                        val uiText: String,
                        val color: RGBA,
                        val textColor: RGBA = Colors.BLACK,
                        val topIndent: Double = 20.0) {
    EXAMPLE(0,
        """This is a
          |template example of
          |a tutorial
        """.trimMargin(), Colors["#bca8ff"], topIndent = 25.0),
}

data class Tutorial
    (var MergeTutorial: MutableList<Boolean> = MutableList(6) { false },
     val tutorialProperty: ObservableProperty<String> = ObservableProperty("")) {


    private fun fromString (string: String): Tutorial {
        string.toList().forEachIndexed { i, c -> this.MergeTutorial[i] = c == 'Y' }
        return this
    }

    fun init (storage: NativeStorage) {
        tutorial.fromString (storage.getOrNull("Tutorial") ?: "")

        tutorial.tutorialProperty.observe {
            // new code line here
            storage["Tutorial"] = tutorial.toString()
        }
    }

    override fun toString (): String {
        return this.MergeTutorial.map { complete -> if (complete) 'Y' else 'N'}.toCharArray().concatToString()
    }

    fun isIncomplete (tutorialType: TutorialType) : Boolean {
        return !this.MergeTutorial[tutorialType.index]
    }

    fun markComplete (tutorialType: TutorialType) : Unit {
        this.MergeTutorial[tutorialType.index] = true
        this.tutorialProperty.update(tutorial.toString())
    }


}

fun getContainerWidth(tutorialType: TutorialType) : Int {
    return when (tutorialType) {
        TutorialType.EXAMPLE -> gameView.fieldWidth * 4 / 5
    }
}

fun getContainerHeight(tutorialType: TutorialType) : Int {
    return when (tutorialType) {
        TutorialType.EXAMPLE -> gameView.fieldWidth / 3
    }
}

fun getTextSize(tutorialType: TutorialType) : Int {
    return when (tutorialType) {
        TutorialType.EXAMPLE -> 16
    }
}

fun getSubsequentTutorial(tutorialType: TutorialType) : TutorialType? {
    return when (tutorialType) {
        TutorialType.EXAMPLE -> null
    }
}

fun Container.showTutorial(tutorialType: TutorialType) = container {
    var currentTutorial = tutorialType
    popup.showTutorial()
    Napier.d("Showing Tutorial Container...")
    fun closeTutorial(redrawTutorial: () -> Unit){
        tutorial.markComplete(currentTutorial)

        if (getSubsequentTutorial(currentTutorial) == null) {
            Napier.d("Closing Tutorial Container...")
            this@container.removeFromParent()
            popup.hidePopup()
        }
        else{
            Napier.d("Showing Second Tutorial Container...")
            currentTutorial = getSubsequentTutorial(tutorialType)!!
            this.removeChildren()
            redrawTutorial()
        }
    }
    fun drawTutorial() {
        val restartBackground = roundRect(gameView.fieldWidth, gameView.fieldHeight, 5, fill = Colors["#aaa6a4cc"]) {
            centerXOn(gameView.gameField)
            centerYOn(gameView.gameField)
            onUp {
                Napier.d("Background container clicked when showing tutorial")
                closeTutorial { drawTutorial() }
            }
        }
        val bgTutorialContainer = container {
            val rect = roundRect(
                getContainerWidth(currentTutorial),
                getContainerHeight(currentTutorial),
                25,
                stroke = Colors.BLACK,
                strokeThickness = 1.0,
                fill = currentTutorial.color
            ) {
                centerXOn(restartBackground)
                centerYOn(restartBackground)
            }
            uiText(currentTutorial.uiText) {
                alignTopToTopOf(rect, currentTutorial.topIndent)
                centerXOn(rect)
                textAlignment = TextAlignment.MIDDLE_CENTER
                textSize = getTextSize(currentTutorial).toDouble()
                textColor = currentTutorial.textColor
            }
            onUp {
                Napier.d("onUp - Tutorial - YES Clicked")
                closeTutorial { drawTutorial() }
            }
        }
    }
    drawTutorial()
}