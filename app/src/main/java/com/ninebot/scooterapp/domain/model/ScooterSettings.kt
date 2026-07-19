package com.ninebot.scooterapp.domain.model

import kotlinx.serialization.Serializable

/**
 * Data class representing customizable scooter settings.
 * Only includes settings that are legal and safe to modify.
 * 
 * @param recoveryStrength Regenerative braking strength (0-255)
 * @param ridingMode Riding mode (ECO, NORMAL, SPORT)
 * @param cruiseControl Cruise control enabled
 * @param cruiseControlSpeed Set cruise control speed (km/h)
 * @param maxSpeed Speed limiter (km/h)
 * @param lightMode Front light mode (OFF, ON, AUTO, BLINK)
 * @param tailLightBrightness Tail light brightness (0-100)
 * @param alarmEnabled Security alarm enabled
 * @param screenBrightness Dashboard screen brightness (0-100)
 */
@Serializable
data class ScooterSettings(
    val recoveryStrength: Int = 50,
    val ridingMode: RidingMode = RidingMode.NORMAL,
    val cruiseControl: Boolean = false,
    val cruiseControlSpeed: Int = 20,
    val maxSpeed: Int = 25,
    val lightMode: LightMode = LightMode.AUTO,
    val tailLightBrightness: Int = 100,
    val alarmEnabled: Boolean = true,
    val screenBrightness: Int = 100
) {
    /**
     * Validates settings for legal and safety compliance.
     * Returns false if settings violate regulations.
     */
    fun isLegal(): Boolean {
        // Speed limit compliance (varies by region, default EU: 25 km/h)
        return maxSpeed <= 25 &&
               cruiseControlSpeed <= 25 &&
               recoveryStrength in 0..255 &&
               screenBrightness in 0..100
    }
}

/**
 * Enum representing riding modes.
 */
enum class RidingMode {
    ECO,      // Energy saving mode
    NORMAL,   // Balanced mode
    SPORT     // Maximum power mode
}

/**
 * Enum representing light modes.
 */
enum class LightMode {
    OFF,      // Lights off
    ON,       // Always on
    AUTO,     // Automatic based on ambient light
    BLINK     // Blinking mode
}
