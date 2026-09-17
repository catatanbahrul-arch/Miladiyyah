package id.miladiyyah.app.data.notification

/**
 * Safe default implementation.
 *
 * It intentionally performs no external operation. This keeps the
 * application buildable before Firebase Messaging is configured.
 */
object NoOpNotificationService : NotificationService {

    override fun notify(
        title: String,
        message: String,
    ) {
        // Intentionally empty.
    }
}
