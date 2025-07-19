# Project Setup Guide

Follow these steps to set up the project properly:

## 1. Download from Sketchub
- Download the project files from [Sketchub](https://www.sketchub.in/)
- Make sure you have [Sketchware Pro (Nightly)](https://github.com/Sketchware-Pro/Sketchware-Pro) installed

## 2. Restore in Sketchware Pro (Nightly)
1. Open Sketchware Pro (Nightly)
2. Go to `Projects` → `Restore`
3. Select the downloaded `.swb` file
4. Wait for the restoration process to complete

## 3. Configure Your Credentials
Replace these credentials with your own:

### Image Upload Services
- **ImgBB API**:
  - Edit `UploadImage.java` and replace the API key
  - Get your API key from [ImgBB](https://imgbb.com/)

- **Cloudinary**:
  - Edit `FasterCloudinary.java` with your Cloudinary credentials
  - Sign up at [Cloudinary](https://cloudinary.com/)

### Firebase Configuration
1. Replace all Firebase credentials in:
   - `google-services.json`
   - Firebase-related Java files
2. Set up your Firebase project at [Firebase Console](https://console.firebase.google.com/)

## Troubleshooting
If you encounter any problems:
1. Contact me on Synapse (I'm the admin there)
2. Get Synapse from: [https://dl-synapse.pages.dev/](https://dl-synapse.pages.dev/)
3. Look for the admin badge in the Synapse app to message me directly

## Notes
- Make sure all API keys and sensitive information are kept private
- Never commit your actual credentials to version control
