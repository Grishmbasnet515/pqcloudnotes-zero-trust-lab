package com.student.pqcloudnotes.data

import com.student.pqcloudnotes.data.model.CryptoSuite
import com.student.pqcloudnotes.security.DevicePosture

object AppConfigStore {
    @Volatile
    var suite: CryptoSuite = CryptoSuite.CLASSICAL
        private set

    @Volatile
    var keyVersion: Int = 1
        private set

    @Volatile
    var deviceRisk: String = "LOW"
        private set

    @Volatile
    var devicePosture: DevicePosture = DevicePosture()
        private set

    fun updateSuite(value: CryptoSuite) {
        suite = value
    }

    fun incrementKeyVersion() {
        keyVersion += 1
    }

    fun updateRisk(value: String) {
        deviceRisk = value
    }

    fun updatePosture(posture: DevicePosture) {
        devicePosture = posture
    }
}
