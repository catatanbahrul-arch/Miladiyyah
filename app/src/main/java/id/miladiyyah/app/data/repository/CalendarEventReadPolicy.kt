package id.miladiyyah.app.data.repository

/**
 * Read strategy for general calendar data.
 *
 * This is a policy boundary only. It does not perform I/O itself.
 * Actual repository wiring remains in the existing repository/cache
 * boundaries.
 */
enum class CalendarEventReadPolicy {

    /**
     * Ask the remote/source repository first. If that source fails,
     * an already-composed local cache boundary may provide fallback data.
     */
    REMOTE_FIRST_WITH_LOCAL_FALLBACK,

    /**
     * Read only from the local cache boundary.
     * No network operation is implied by this policy.
     */
    LOCAL_CACHE_ONLY,

    /**
     * Ask the source repository only. No local fallback is implied.
     */
    REMOTE_ONLY,
}
