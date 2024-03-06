import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.github.bytehole.channel.ByteHole
import com.github.bytehole.channel.IDebugger
import com.github.bytehole.channel.handler.IHandler

class AndroidByteHole : ByteHole() {

    companion object {
        private const val TAG = "AndroidByteHole"
    }

    override val debugger: IDebugger by lazy { AndroidDebugger() }

    override val handler: IHandler by lazy { AndroidHandler() }

    class AndroidHandler : IHandler {

        private val thread by lazy { HandlerThread("ByteHoleThread") }

        private val handler by lazy { Handler(thread.looper) }

        init {
            thread.start()
        }

        override fun post(task: Runnable) {
            handler.post(task)
        }

    }

    class AndroidDebugger : IDebugger {
        override fun onBroadcastReady() {
            Log.d(TAG, "onBroadcastReady")
        }

        override fun onBroadcastReceived(fromIp: String, data: ByteArray) {
            Log.d(TAG, "onBroadcastReceived fromIp=$fromIp data=${String(data)}")
        }

        override fun onMessageReady() {
            Log.d(TAG, "onMessageReady")
        }

        override fun onMessageReceived(fromIp: String, data: ByteArray) {
            Log.d(TAG, "onMessageReceived fromIp=$fromIp data=${String(data)}")
        }
    }

}