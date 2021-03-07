package me.kyuubiran.qqcleaner

import android.app.Application
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage
import me.kyuubiran.qqcleaner.data.hostInfo
import me.kyuubiran.qqcleaner.data.init
import me.kyuubiran.qqcleaner.hook.ModuleEntryHook
import me.kyuubiran.qqcleaner.utils.*
import java.lang.reflect.Method

private const val QQ_CLEANER_TAG = "QQ_CLEANER_TAG"
private var firstInit = false
var secondInitQQ = false
    private set

class HookLoader(lpparam: XC_LoadPackage.LoadPackageParam) {
    init {
        doInit(lpparam.classLoader)
    }

    private fun initItem(classLoader: ClassLoader) {
        Utils(classLoader)
        ModuleEntryHook()
        ResInject.initForStubActivity()
        ResInject.injectModuleResources(hostInfo.application.resources)
        ConfigManager.checkConfigIsExists()
        CleanManager.AutoClean()
    }

    private fun doInit(rtLoader: ClassLoader) {
        if (firstInit) return
        try {
            val startup: XC_MethodHook = object : XC_MethodHook(51) {
                override fun afterHookedMethod(param: MethodHookParam) {
                    try {
                        if (secondInitQQ) return
                        val clazz = rtLoader.loadClass("com.tencent.common.app.BaseApplicationImpl")
                        val ctx =
                            clazz!!.let {
                                getField(
                                    it,
                                    "sApplication",
                                    clazz
                                )?.get(null)
                            } as Application
                        init(ctx)
                        appContext = hostInfo.application
                        if ("true" == System.getProperty(QQ_CLEANER_TAG)) return
                        val classLoader = ctx.classLoader
                        System.setProperty(QQ_CLEANER_TAG, "true")
                        initItem(classLoader)
                        secondInitQQ = true
                    } catch (e: Throwable) {
                        loge(e)
                        throw e
                    }
                }
            }
            val loadDex = rtLoader.loadClass("com.tencent.mobileqq.startup.step.LoadDex")
            val ms = loadDex.declaredMethods
            var m: Method? = null
            for (method in ms) {
                if (method.returnType == Boolean::class.javaPrimitiveType && method.parameterTypes.isEmpty()) {
                    m = method
                    break
                }
            }
            XposedBridge.hookMethod(m, startup)
            firstInit = true
        } catch (e: Throwable) {
            if (e.toString().contains("com.bug.zqq")) return
            if (e.toString().contains("com.google.android.webview")) return
            throw e
        }
    }
}