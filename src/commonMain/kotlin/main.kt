import com.soywiz.korge.*
import com.soywiz.korge.view.*
import com.soywiz.korge.input.*
import com.soywiz.korge.service.storage.storage
import com.soywiz.korge.ui.textAlignment
import com.soywiz.korge.ui.textColor
import com.soywiz.korge.ui.textSize
import com.soywiz.korge.ui.uiText
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.geom.vector.*
import com.soywiz.korim.font.*
import com.soywiz.korim.text.TextAlignment
import kotlin.properties.Delegates

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier


val score = Score()

var font: BitmapFont by Delegates.notNull()

var popup = Popup()

var gameView = View()

var animator = Animator()

var tutorial = Tutorial()



suspend fun main() = Korge(width = 360, height = 640, title = "app", bgcolor = RGBA(253, 247, 240)) {
	Napier.base(DebugAntilog())

	val storage = views.storage



	score.init(storage)
	tutorial.init(storage)

	font = resourcesVfs["clear_sans.fnt"].readBitmapFont()

	val backgroundImg = resourcesVfs["background.png"].readBitmap()

	Napier.d("UI Initialized")



	if (tutorial.isIncomplete(TutorialType.EXAMPLE)) showTutorial(TutorialType.EXAMPLE)

	touch {
		onUp { handleUp(mouseXY) }
	}

}

fun Container.restart() {
	Napier.d("Running Restart Function...")
	score.current.update(0)
}
