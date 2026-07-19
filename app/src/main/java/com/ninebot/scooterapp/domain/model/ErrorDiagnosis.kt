package com.ninebot.scooterapp.domain.model

import kotlinx.serialization.Serializable

/**
 * Data class representing error codes and diagnostics.
 * Maps Ninebot protocol error codes to user-friendly explanations.
 * 
 * @param code Numeric error code from scooter
 * @param name Error name (e.g., "BATTERY_LOW")
 * @param description User-friendly description
 * @param severity Error severity level
 * @param recommendedAction Suggested action to resolve
 * @param timestamp When error occurred
 */
@Serializable
data class ErrorDiagnosis(
    val code: Int,
    val name: String,
    val description: String,
    val severity: ErrorSeverity = ErrorSeverity.INFO,
    val recommendedAction: String = "",
    val timestamp: Long = System.currentTimeMillis()
) {
    companion object {
        /**
         * Maps error codes to known Ninebot error definitions.
         * Based on reverse-engineered Ninebot protocol documentation.
         */
        fun fromCode(errorCode: Int): ErrorDiagnosis {
            return when (errorCode) {
                // Power & Battery Errors
                1 -> ErrorDiagnosis(
                    1, "BATTERY_UNDERVOLTAGE",
                    "Battery voltage is too low",
                    ErrorSeverity.CRITICAL,
                    "Charge the scooter immediately"
                )
                2 -> ErrorDiagnosis(
                    2, "BATTERY_OVERVOLTAGE",
                    "Battery voltage exceeded safe limits",
                    ErrorSeverity.CRITICAL,
                    "Stop using immediately and check battery"
                )
                3 -> ErrorDiagnosis(
                    3, "BATTERY_TEMPERATURE_HIGH",
                    "Battery temperature is too high",
                    ErrorSeverity.WARNING,
                    "Let battery cool down before riding"
                )
                4 -> ErrorDiagnosis(
                    4, "BATTERY_CELL_IMBALANCE",
                    "Battery cells are not balanced",
                    ErrorSeverity.WARNING,
                    "Contact support or perform factory reset"
                )

                // Motor Errors
                5 -> ErrorDiagnosis(
                    5, "MOTOR_OVERCURRENT",
                    "Motor current exceeded limits",
                    ErrorSeverity.CRITICAL,
                    "Stop riding and check motor controller"
                )
                6 -> ErrorDiagnosis(
                    6, "MOTOR_TEMPERATURE_HIGH",
                    "Motor temperature is too high",
                    ErrorSeverity.WARNING,
                    "Let motor cool down before riding"
                )
                7 -> ErrorDiagnosis(
                    7, "MOTOR_FAILURE",
                    "Motor has encountered a critical failure",
                    ErrorSeverity.CRITICAL,
                    "Contact support for service"
                )

                // Controller Errors
                8 -> ErrorDiagnosis(
                    8, "CONTROLLER_ERROR",
                    "Motor controller malfunction",
                    ErrorSeverity.CRITICAL,
                    "Restart the scooter or contact support"
                )
                9 -> ErrorDiagnosis(
                    9, "HALL_SENSOR_ERROR",
                    "Motor hall sensors not responding",
                    ErrorSeverity.CRITICAL,
                    "Contact support for sensor replacement"
                )

                // Brake System Errors
                10 -> ErrorDiagnosis(
                    10, "BRAKE_SENSOR_ERROR",
                    "Brake sensor malfunction",
                    ErrorSeverity.WARNING,
                    "Check brake cable connections"
                )
                11 -> ErrorDiagnosis(
                    11, "BRAKE_FAILURE",
                    "Brake system has failed",
                    ErrorSeverity.CRITICAL,
                    "Do not ride - contact support immediately"
                )

                // Electrical Errors
                12 -> ErrorDiagnosis(
                    12, "POWER_LOSS",
                    "Unexpected power loss detected",
                    ErrorSeverity.WARNING,
                    "Check electrical connections"
                )
                13 -> ErrorDiagnosis(
                    13, "CHARGING_ERROR",
                    "Charging port malfunction",
                    ErrorSeverity.WARNING,
                    "Clean charging contacts and try again"
                )

                // Communication Errors
                14 -> ErrorDiagnosis(
                    14, "BLE_COMMUNICATION_ERROR",
                    "Bluetooth communication lost",
                    ErrorSeverity.INFO,
                    "Move closer to the scooter and reconnect"
                )
                15 -> ErrorDiagnosis(
                    15, "FIRMWARE_CORRUPTED",
                    "Firmware data is corrupted",
                    ErrorSeverity.CRITICAL,
                    "Perform firmware update immediately"
                )

                // Mechanical Errors
                16 -> ErrorDiagnosis(
                    16, "WHEEL_SPEED_SENSOR_ERROR",
                    "Wheel speed sensor malfunction",
                    ErrorSeverity.WARNING,
                    "Check sensor alignment and connections"
                )
                17 -> ErrorDiagnosis(
                    17, "GYROSCOPE_ERROR",
                    "Gyroscope sensor failure",
                    ErrorSeverity.WARNING,
                    "Calibrate gyroscope or contact support"
                )

                // Unknown Error
                else -> ErrorDiagnosis(
                    errorCode, "UNKNOWN_ERROR",
                    "Unknown error code: $errorCode",
                    ErrorSeverity.WARNING,
                    "Contact support with error code"
                )
            }
        }
    }
}

/**
 * Enum representing error severity levels.
 */
enum class ErrorSeverity {
    INFO,
    WARNING,
    CRITICAL
}
