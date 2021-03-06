package com.bugsnag.android.ndk

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.*

@RunWith(Parameterized::class)
internal class UserSerializationTest {

    companion object {
        init {
            System.loadLibrary("bugsnag-ndk")
            System.loadLibrary("bugsnag-ndk-test")
        }

        @JvmStatic
        @Parameters
        fun testCases() = (0..1)
    }

    external fun run(testCase: Int, expectedJson: String): Int

    @Parameter
    lateinit var testCase: Number

    @Test
    fun testPassesNativeSuite() {
        val expectedJson = loadJson("user_serialization_$testCase.json")
        verifyNativeRun(run(testCase.toInt(), expectedJson))
    }
}
