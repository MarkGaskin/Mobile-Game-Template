import com.soywiz.korge.service.storage.NativeStorage
import com.soywiz.korio.async.ObservableProperty

class Score (
    val current: ObservableProperty<Int> = ObservableProperty(0),
    val best: ObservableProperty<Int> = ObservableProperty(0)){

    fun init (storage: NativeStorage): Unit
    {
        this.best.update(storage.getOrNull("best")?.toInt() ?: 0)

        this.current.observe {
            if (it > score.best.value) score.best.update(it)
        }
        this.best.observe {
            // new code line here
            storage["best"] = it.toString()
        }
    }
}