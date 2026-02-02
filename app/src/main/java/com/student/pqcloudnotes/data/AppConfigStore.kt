package com.student.pqcloudnotes.data

import com.student.pqcloudnotes.data.model.CryptoSuite

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

    fun updateSuite(value: CryptoSuite) {
        suite = value
    }

    fun incrementKeyVersion() {
        keyVersion += 1
    }

    fun updateRisk(value: String) {
        deviceRisk = value
    }
}
