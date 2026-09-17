# Miladiyyah — Final Release Readiness

## Current milestone

Fase 36 performs a final integrity/build audit of the existing
Miladiyyah foundation through Fase 35.

## What is verified

- Gregorian calendar engine and UI foundation remain present.
- Local Hijri foundation remains present.
- General Calendar repository/source-selection boundaries remain present.
- Room cache boundaries remain present.
- Firebase runtime/firestore infrastructure remains isolated.
- NotificationService boundary remains provider-neutral.
- ProductionDataProviderReadiness remains provider-neutral.
- Debug Kotlin compilation is tested.
- Debug APK assembly is tested.
- No production credentials are introduced by Fase 36.
- No new provider/network activation is introduced by Fase 36.
- No CalendarScreen/MainActivity activation is introduced by Fase 36.

## Production data status

Production source artifacts remain a separate activation gate.

Examples of information that must be real and validated before production
activation include:

- official data source;
- schema;
- endpoint or Firebase configuration;
- access/security rules;
- mapping validation;
- freshness/sync rules.

Fase 36 does not invent or fill those missing production artifacts.

## Release interpretation

A passing Fase 36 audit means the current Android foundation builds cleanly
and the architectural boundaries remain intact.

It does not claim that every future production data provider is already live.

## Git

Fase 36 itself does not perform `git add`, `git commit`, or `git push`.
