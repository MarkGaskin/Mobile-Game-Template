import com.soywiz.korge.input.onClick
import com.soywiz.korge.input.onDown
import com.soywiz.korge.input.onMove
import com.soywiz.korge.input.touch
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Rectangle
import io.github.aakira.napier.Napier

class GameField(
    var container: RoundRect = RoundRect(0.0,0.0,0.0),
    var gameWidth: Int = 0,
    var gameHeight: Int = 0) {

    fun Stage.init(fieldWidthIn: Int, fieldHeightIn: Int, leftIndent: Int, topIndent: Int) {
        gameWidth = fieldHeightIn
        gameHeight = fieldWidthIn
        container = roundRect(gameWidth, gameHeight, 5, fill = Colors["#e0d8e880"]) {
            position(leftIndent, topIndent)
            touch {
                onDown { handleDown(mouseXY) }
                onMove { handleDrag(mouseXY) }
            }
        }
    }
}

class View (
    var gameField: GameField = GameField(),
    var totalWidth: Int = 0,
    var totalHeight: Int = 0) {

}

data class InitBitmaps (
    var restart: Bitmap)

fun Container.initializeViews (initBitmaps: InitBitmaps) {
    val btnSize = 45.0
    val restartBlock = container {
        val backgroundBlock = roundRect(btnSize, btnSize, 5.0, fill = Colors["#639cd9"])
        image(initBitmaps.restart) {
            size(btnSize * 0.8, btnSize * 0.8)
            centerOn(backgroundBlock)
        }
        alignLeftToLeftOf(gameView.gameField.container, 8)
        alignBottomToTopOf(gameView.gameField.container, 8)
        onClick {
            if(popup.hasPopup()){ Napier.d("Restart Button Clicked while a popup is displayed") }
            else{
                Napier.d("Restart Button Clicked when already showing restart")
                popup.restartPopupContainer.removeFromParent()
                popup.hidePopup()
            }
        }
    }
}