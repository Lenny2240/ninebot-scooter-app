package com.ninebot.scooterapp.domain.model

import kotlinx.serialization.Serializable

/**
 * Data class representing battery/accumulator information.
 * Provides comprehensive battery health and performance metrics.
 * 
 * @param percentage State of Charge (0-100%)
 * @param voltage Total battery voltage in volts
 * @param current Current draw in amperes (positive: discharge, negative: charge)
 * @param temperature Battery temperature in Celsius
 * @param cellCount Number of battery cells
 * @param cellVoltages Individual cell voltages
 * @param cycles Total charge/discharge cycles
 * @param health Battery health percentage (0-100%)
 * @param capacity Remaining capacity in mAh
 * @param maxCapacity Original capacity in mAh
 * @param estimatedRange Remaining range in km
 */
@Serializable
data class BatteryInfo(
    val percentage: Int = 0,
    val voltage: Double = 0.0,
    val current: Double = 0.0,
    val temperature: Double = 0.0,
    val cellCount: Int = 0,
    val cellVoltages: List<Double> = emptyList(),
    val cycles: Int = 0,
    val health: Int = 100,
    val capacity: Int = 0,
    val maxCapacity: Int = 0,
    val estimatedRange: Int = 0
) {
    /**
     * Determines battery health status.
     * - Good: > 80%
     * - Warning: 60-80%
     * - Critical: < 60%
     */
    fun getHealthStatus(): BatteryHealthStatus = when {
        health > 80 -> BatteryHealthStatus.GOOD
        health > 60 -> BatteryHealthStatus.WARNING
        else -> BatteryHealthStatus.CRITICAL
    }

    /**
     * Calculates estimated time to full charge at current charging rate.
     * Returns null if not currently charging.
     */
    fun estimateChargeTime(): Int? {
        if (current >= 0) return null // Not charging
        val chargeCurrent = kotlin.math.abs(current)
        if (chargeCurrent == 0.0) return null
        
        val remainingCapacity = maxCapacity - capacity
        return (remainingCapacity / chargeCurrent / 1000).toInt() // in hours
    }

    /**
     * Validates battery data for anomalies.
     */
    fun isValid(): Boolean {
        return percentage in 0..100 &&
               voltage > 0 &&
               temperature in -20..80 &&
               cellVoltages.isNotEmpty() &&
               health in 0..100 &&
               cycles >= 0
    }
}

/**
 * Enum representing battery health status categories.
 */
enum class BatteryHealthStatus {
    GOOD,
    WARNING,
    CRITICAL
}
