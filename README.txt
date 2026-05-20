# Save File Editor — Android APK

Supports: Vampire Survivors (.sav) · Teaching Feeling (.sav) · .mdrgslot · any JSON save

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
METHOD 1 — GitHub Actions (NO Android Studio needed)
           Build the APK online for FREE in ~3 minutes
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

STEP 1 — Create a free GitHub account
   → https://github.com/signup

STEP 2 — Create a new repository
   → Click the + button → New repository
   → Name it anything (e.g. "save-editor")
   → Set to Public
   → Click "Create repository"

STEP 3 — Upload this project
   → Click "uploading an existing file" on the repo page
   → Drag ALL the files from this ZIP into the uploader
   → Important: keep the folder structure (.github/workflows/build.yml must exist)
   → Click "Commit changes"

STEP 4 — Wait for the build (~3 minutes)
   → Go to the "Actions" tab on your repo
   → You'll see "Build APK" running automatically
   → Wait for the green ✓ checkmark

STEP 5 — Download your APK
   → Click on the completed "Build APK" workflow run
   → Scroll down to "Artifacts"
   → Click "SaveEditor-APK" to download
   → Extract the zip → install app-debug.apk on your phone

   First install: go to Settings → Install unknown apps → allow your browser/file manager

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
METHOD 2 — Android Studio (build locally)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Download Android Studio → https://developer.android.com/studio
2. Open → File → Open → select this folder
3. Wait for Gradle sync (~2 min first time)
4. Build → Build APK(s) → app-debug.apk

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
FEATURES
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
• Upload any JSON save file
• Values tab  — edit scalars, booleans, numbers
• Lists tab   — add/remove items from arrays
• Tables tab  — edit dict/map entries
• 🥚 Egg Stats (Vampire Survivors) — edit per-character egg bonuses
  - Edit by egg count or raw value
  - Add any character including future ones
  - Reset individual stats to default
• 🔐 Fix Checksum — recalculates VS checksum so game accepts edits
• TyranoScript saves — auto URL-decode/encode
• 💾 Save — same filename
• ⬇ Save As — choose new filename
