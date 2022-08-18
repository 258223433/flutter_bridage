package com.dodo.flutterbridge.call.strategy

import androidx.annotation.CallSuper
import com.dodo.flutterbridge.call.Handler
import kotlin.jvm.Throws

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/12
 *     desc   :
 *     version: 1.0
 */
abstract class StickyHandlerStrategy <A> : HandlerStrategy<A> {
    private val stickyData = mutableMapOf<String, A>()

    override fun addHandler(handler: Handler<A>) {
        val data = stickyData[handler.name]
        if (data !=null){
            handler.onCall(data)
        }
    }

    @CallSuper
    @Throws(HandlerNotFoundException::class, MutableHandlerException::class)
    override fun onCallStrategy(name: String, sticky: Boolean, data: A): Any? {
        if (sticky){
            stickyData[name] = data
        }
        return onCallStrategy(name,data)
    }

    @Throws(HandlerNotFoundException::class, MutableHandlerException::class)
    abstract fun onCallStrategy(name: String,  data: A): Any?
}