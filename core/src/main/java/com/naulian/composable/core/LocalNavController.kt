package com.naulian.composable.core

import androidx.compose.runtime.compositionLocalOf
import com.naulian.composable.core.component.ComposableComponent


val LocalComponents = compositionLocalOf<Map<String, ComposableComponent>> {
    emptyMap()
}

object LocalComponentProvider {

    private val hashMap = hashMapOf<String, ComposableComponent>()

    fun provideComponentMap(): Map<String, ComposableComponent> {
        return hashMap.toMap()
    }

    fun addComponent(component: List<ComposableComponent>) {
        hashMap.putAll(component.associateBy { it.id })
    }
}

fun componentBuilder(
    provide: () -> Unit
): Map<String, ComposableComponent> {
    provide()
    return LocalComponentProvider.provideComponentMap()
}

operator fun List<ComposableComponent>.unaryPlus() {
    LocalComponentProvider.addComponent(this)
}