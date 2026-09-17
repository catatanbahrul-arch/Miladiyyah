package id.miladiyyah.app.data.repository

/**
 * Readiness state for a production data provider.
 *
 * This contract is intentionally provider-neutral. It records whether
 * the prerequisites for activation have been validated without storing
 * credentials or performing network operations.
 */
enum class ProductionDataProviderReadiness {

    /**
     * Provider implementation/source is not yet available.
     */
    NOT_AVAILABLE,

    /**
     * Provider implementation exists, but production configuration or
     * schema verification is still incomplete.
     */
    CONFIGURATION_PENDING,

    /**
     * Provider configuration/schema/source has been validated and is
     * eligible for an explicit activation step by the application.
     */
    READY_FOR_EXPLICIT_ACTIVATION,
}
