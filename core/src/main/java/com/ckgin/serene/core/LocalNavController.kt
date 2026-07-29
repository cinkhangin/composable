package com.ckgin.serene.core

import androidx.compose.runtime.compositionLocalOf
import com.ckgin.serene.core.component.SereneComponent


val LocalComponents = compositionLocalOf<Map<String, SereneComponent>> {
    emptyMap()
}

object LocalComponentProvider {

    private val hashMap = hashMapOf<String, SereneComponent>()

    fun provideComponentMap(): Map<String, SereneComponent> {
        return hashMap.toMap()
    }

    fun addComponent(component: List<SereneComponent>) {
        hashMap.putAll(component.associateBy { it.id })
    }
}

fun componentBuilder(
    provide: () -> Unit
): Map<String, SereneComponent> {
    provide()
    return LocalComponentProvider.provideComponentMap()
}

operator fun List<SereneComponent>.unaryPlus() {
    LocalComponentProvider.addComponent(this)
}