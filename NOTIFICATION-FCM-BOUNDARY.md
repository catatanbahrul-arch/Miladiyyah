# Notification / FCM Boundary

## Status

Fase 33 creates the application notification boundary only.

## Contract

`NotificationService` exposes:

- `notify(title, message)`

The contract does not import Firebase Messaging and does not expose
Firebase-specific types.

## Default

`NoOpNotificationService` is the safe default implementation.

It performs no network operation and requires no Firebase configuration.

## FCM

A future Firebase Cloud Messaging implementation may be added behind
`NotificationService`.

This phase intentionally does **not**:

- add Firebase Messaging dependency;
- add `google-services.json`;
- request or store server keys;
- store device tokens;
- register a production topic;
- send a production notification;
- modify startup/UI;
- change calendar data contracts.

## Activation rule

FCM remains an explicit future implementation/activation concern.
The calendar and UI layers must depend on the boundary, not Firebase APIs.
