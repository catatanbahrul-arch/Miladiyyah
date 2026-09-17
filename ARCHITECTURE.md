# Miladiyyah Architecture - Fase 2B

## Tujuan
Fase ini menyiapkan struktur aplikasi agar UI tidak terikat langsung pada sumber data.

## Lapisan
- `ui/`: layar dan komponen Compose.
- `domain/`: kontrak dan model bisnis yang seharusnya bebas dari detail jaringan/storage.
- `data/remote/`: batas untuk REST API.
- `data/spreadsheet/`: batas untuk sumber spreadsheet/tabular.
- `data/files/`: batas untuk sumber file.
- `data/repository/`: tempat implementasi repository menggabungkan sumber data.

## Alur yang dituju
`UI -> Domain Repository -> Data Sources -> REST / Spreadsheet / File`

## Yang sengaja belum dipasang
- URL endpoint REST.
- API key / token / credential.
- ID spreadsheet, nama sheet, range final, dan schema final.
- Lokasi file produksi.
- Format JSON/CSV/Excel final.
- Library networking/database tambahan.

Semua detail di atas harus berasal dari spesifikasi sumber yang nyata, bukan ditebak dari nama proyek.

## Checkpoint
Fase 2B hanya membangun kerangka arsitektur. Implementasi provider nyata dilakukan pada fase integrasi setelah kontrak sumber data tersedia.
