package com.ninebot.scooterapp.domain.model

import kotlinx.serialization.Serializable

/**
 * Data class representing real-time scooter motion data.
 * Includes speed, distance, and driving statistics.
 * 
 * @param speed Current speed in km/h
 * @param distance Total distance traveled in km
 * @param rideTime Current ride duration in seconds
 * @param maxSpeed Maximum speed reached in current session (km/h)
 * @param averageSpeed Average speed of current session (km/h)
 * @param powerConsumption Power consumption in watts
 * @param motorTemperature Motor temperature in Celsius
 * @param isMoving Whether the scooter is currently moving
 * @param odometer Total distance in km (lifetime)
 */
@Serializable
data class MotionData(
    val speed: Double = 0.0,
    val distance: Double = 0.0,
    val rideTime: Int = 0,
    val maxSpeed: Double = 0.0,
    val averageSpeed: Double = 0.0,
    val powerConsumption: Double = 0.0,
    val motorTemperature: Double = 0.0,
    val isMoving: Boolean = false,
    val odometer: Double = 0.0
) {
    /**
     * Calculates energy consumption in Wh for current session.
     */
    fun calculateSessionEnergyConsumption(): Double {
        if (rideTime == 0) return 0.0
        val hours = rideTime / 3600.0
        return powerConsumption * hours
    }

    /**
     * Calculates efficiency in Wh/km.
     */
    fun getEfficiency(): Double {
        if (distance == 0.0) return 0.0
        return calculateSessionEnergyConsumption() / distance
    }

    /**
     * Converts ride time to formatted string (HH:MM:SS).
     */
    fun formatRideTime(): String {
        val hours = rideTime / 3600
        val minutes = (rideTime % 3600) / 60
        val seconds = rideTime % 60
        return "%02d:%02d:%02d".format(hours, minutes, seconds)
    }
}
