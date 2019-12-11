package com.bugsnag.android

import java.util.concurrent.ConcurrentLinkedQueue

internal data class CallbackState(
    val onErrorTasks: MutableCollection<OnError> = ConcurrentLinkedQueue<OnError>(),
    val onBreadcrumbTasks: MutableCollection<OnBreadcrumb> = ConcurrentLinkedQueue<OnBreadcrumb>(),
    val onSessionTasks: MutableCollection<OnSession> = ConcurrentLinkedQueue()
): CallbackAware {

    override fun addOnError(onError: OnError) {
        onErrorTasks.add(onError)
    }

    override fun removeOnError(onError: OnError) {
        onErrorTasks.remove(onError)
    }

    override fun addOnBreadcrumb(onBreadcrumb: OnBreadcrumb) {
        onBreadcrumbTasks.add(onBreadcrumb)
    }

    override fun removeOnBreadcrumb(onBreadcrumb: OnBreadcrumb) {
        onBreadcrumbTasks.remove(onBreadcrumb)
    }

    override fun addOnSession(onSession: OnSession) {
        onSessionTasks.add(onSession)
    }

    override fun removeOnSession(onSession: OnSession) {
        onSessionTasks.remove(onSession)
    }


    fun runOnErrorTasks(event: Event, logger: Logger): Boolean {
        onErrorTasks.forEach {
            try {
                if (!it.run(event)) {
                    return false
                }
            } catch (ex: Throwable) {
                logger.w("OnBreadcrumb threw an Exception", ex)
            }
        }
        return true
    }

    fun runOnBreadcrumbTasks(breadcrumb: Breadcrumb, logger: Logger): Boolean {
        onBreadcrumbTasks.forEach {
            try {
                if (!it.run(breadcrumb)) {
                    return false
                }
            } catch (ex: Throwable) {
                logger.w("OnBreadcrumb threw an Exception", ex)
            }
        }
        return true
    }

    fun runOnSessionTasks(sessionPayload: SessionPayload, logger: Logger): Boolean {
        onSessionTasks.forEach {
            try {
                if (!it.run(sessionPayload)) {
                    return false
                }
            } catch (ex: Throwable) {
                logger.w("OnSession threw an Exception", ex)
            }
        }
        return true
    }

    fun copy() = this.copy(
        onErrorTasks = onErrorTasks,
        onBreadcrumbTasks = onBreadcrumbTasks,
        onSessionTasks = onSessionTasks
    )
}