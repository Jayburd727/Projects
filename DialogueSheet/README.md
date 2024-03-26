# TextLocalizerUI

This script is designed to localize text in a Unity UI using data from a CSV file. It allows for easy switching between languages and updating text elements accordingly.

## How to Use

1. **Attach Script**: Attach the `TextLocalizerUI.cs` script to a GameObject with a TextMeshProUGUI component.

2. **CSV File Setup**: Ensure you have a CSV file containing the localization data. The file should have at least three columns: Key, English, and Spanish.

3. **TextMeshProUGUI Component**: Ensure that the GameObject has a TextMeshProUGUI component attached to it. This script requires this component to function properly.

4. **Language Selection**: Use the provided buttons (English and Spanish) to switch between languages. The script automatically saves the language preference using PlayerPrefs.

## Features

- **Dynamic Text Localization**: Text is dynamically localized based on the selected language.
- **CSV File Support**: Localization data is read from a CSV file, making it easy to update and manage translations.
- **Language Selection**: Users can switch between languages using provided buttons.
- **Easy Integration**: Simply attach the script to a GameObject with TextMeshProUGUI to enable localization.

## Dependencies

- **Unity**: This script is designed to work with Unity game engine.
- **TextMeshPro**: Requires TextMeshProUGUI component for displaying text.

## Code Overview

- **`Start()`**: Called when the script is initialized. Initializes the text field and loads localization data.
- **`UpdateKey(string newKey)`**: Updates the localization key dynamically.
- **`KeyChanged()`**: Called when the key is changed. Updates the text field with localized text.
- **`LoadLocalizationData()`**: Loads localization data from the CSV file.
- **`UpdateTextField()`**: Updates the text field with the localized text.
- **`NextButtonClicked()`**: Called when the next button is clicked. Updates the text field with the next key's localized text.
- **`EnglishButtonClicked()`**: Called when the English button is clicked. Switches the language to English.
- **`SpanishButtonClicked()`**: Called when the Spanish button is clicked. Switches the language to Spanish.
