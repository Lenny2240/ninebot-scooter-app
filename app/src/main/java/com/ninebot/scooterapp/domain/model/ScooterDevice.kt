package com.ninebot.scooterapp.domain.model

import kotlinx.serialization.Serializable

/**
 * Data class representing a Ninebot E-Scooter device.
 * Supports multiple models through flexible property mapping.
 * 
 * @param address Bluetooth MAC address (e.g., "00:1A:7D:DA:71:13")
 * @param name Display name from Bluetooth advertisement
 * @param modelName Detected model identifier (e.g., "ES4", "MAX", "G2")
 * @param rssi Signal strength indicator (-100 to 0 dBm)
 * @param isConnected Current connection status
 * @param lastSeen Timestamp of last Bluetooth advertisement
 */
@Serializable
data class ScooterDevice(
    val address: String,
    val name: String,
    val modelName: String,
    val rssi: Int,
    val isConnected: Boolean = false,
    val lastSeen: Long = System.currentTimeMillis()
) {
    /**
     * Returns true if this device is a known Ninebot model.
     * Supported models: ES1, ES2, ES4, MAX, PRO, G2, G30, Z10
     */
    fun isSupported(): Boolean = 
        SupportedNinebotModels.values().any { it.modelName == modelName }
}

/**
 * Enum class for supported Ninebot E-Scooter models.
 * Each model has specific capabilities and protocol versions.
 */
enum class SupportedNinebotModels(
    val modelName: String,
    val displayName: String,
    val releaseYear: Int,
    val maxSpeed: Int, // km/h
    val maxRange: Int, // km
    val maxPower: Int // W
) {
    ES1("ES1", "Xiaomi Mijia ES1", 2017, 25, 30, 250),
    ES2("ES2", "Xiaomi Mijia ES2", 2018, 25, 40, 250),
    ES4("ES4", "Xiaomi Mijia ES4", 2019, 25, 45, 500),
    MAX("MAX", "Ninebot Max", 2019, 25, 65, 800),
    PRO("PRO", "Ninebot Pro", 2020, 25, 50, 1000),
    G2("G2", "Ninebot G2", 2020, 25, 55, 800),
    G30("G30", "Ninebot G30", 2021, 25, 65, 1000),
    Z10("Z10", "Ninebot Z10", 2022, 25, 80, 1200);

    companion object {
        /**
         * Detects model from Bluetooth device name.
         * Uses pattern matching on BLE advertisement name.
         */
        fun fromDeviceName(deviceName: String): SupportedNinebotModels? {
            return values().firstOrNull { model ->
                deviceName.contains(model.modelName, ignoreCase = true) ||
                deviceName.contains(model.displayName, ignoreCase = true)
            }
        }
    }
}
