package com.ninebot.scooterapp.domain.model

import kotlinx.serialization.Serializable

/**
 * Data class representing firmware information.
 * Tracks current version and available updates.
 * 
 * @param version Current firmware version (e.g., "2.8.5")
 * @param buildDate Firmware build timestamp
 * @param hardwareVersion Hardware version code
 * @param protocolVersion BLE protocol version
 * @param latestVersion Latest available firmware version
 * @param isUpdateAvailable Whether an update is available
 * @param changeLog Release notes for new version
 */
@Serializable
data class FirmwareInfo(
    val version: String = "1.0.0",
    val buildDate: Long = 0,
    val hardwareVersion: String = "",
    val protocolVersion: Int = 1,
    val latestVersion: String? = null,
    val isUpdateAvailable: Boolean = false,
    val changeLog: String = ""
) {
    /**
     * Compares current version with latest version.
     * Returns true if an update is available.
     */
    fun hasUpdate(): Boolean {
        if (latestVersion == null) return false
        return compareVersions(version, latestVersion) < 0
    }

    /**
     * Compares two semantic version strings.
     * Returns: -1 if v1 < v2, 0 if equal, 1 if v1 > v2
     */
    private fun compareVersions(v1: String, v2: String): Int {
        val parts1 = v1.split(".").map { it.toIntOrNull() ?: 0 }
        val parts2 = v2.split(".").map { it.toIntOrNull() ?: 0 }
        
        for (i in 0 until maxOf(parts1.size, parts2.size)) {
            val p1 = parts1.getOrNull(i) ?: 0
            val p2 = parts2.getOrNull(i) ?: 0
            if (p1 < p2) return -1
            if (p1 > p2) return 1
        }
        return 0
    }
}
