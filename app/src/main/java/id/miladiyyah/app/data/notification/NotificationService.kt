package id.miladiyyah.app.data.notification

/**
 * Boundary for application notifications.
 *
 * This contract deliberately does not depend on Firebase Messaging.
 * A future FCM implementation can live behind this interface without
 * coupling the calendar/domain/UI layers to Firebase APIs.
 */
interface NotificationService {

    fun notify(
        title: String,
        message: String,
    )
}
