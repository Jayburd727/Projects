using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;
using System.IO;
using System;

[RequireComponent(typeof(TextMeshProUGUI))]
public class TextLocalizerUI : MonoBehaviour
{
    TextMeshProUGUI textField;
    private string csvFilePath = "Assets/Resources/DialogueSheet.csv"; // Path to the .csv file
    public string key;
    static Dictionary<string, string[]> localizationData; // Use string array to store multiple languages
    static List<string> keys;
    private int currentIndex = 0;
    public GameObject Continuebutton;
    public GameObject Exitbutton;

    // Language selection flags
    private bool useFirstLanguage = true;

    void Start()
    {
        KeyChanged();
    }

    public void UpdateKey(string newKey)
    {
        key = newKey;
    }


    public void KeyChanged()
    {
        textField = GetComponent<TextMeshProUGUI>();
        LoadLocalizationData();

        // Load the language preference from PlayerPrefs
        useFirstLanguage = PlayerPrefs.GetInt("LanguagePreference", 1) == 1;

        UpdateTextField();
    }

    void LoadLocalizationData()
    {
        // Use Resources.Load to load the CSV file
        TextAsset csvFile = Resources.Load<TextAsset>("DialogueSheet");

        if (csvFile != null)
        {
            string[] lines = csvFile.text.Split('\n');
            localizationData = new Dictionary<string, string[]>();
            keys = new List<string>();

            for (int i = 0; i < lines.Length; i++)
            {
                string[] row = lines[i].Split(';');

                if (row.Length >= 3) // Expecting at least three parts for English, Spanish, and Key
                {
                    string csvKey = row[0].Trim();

                    string englishText = row[1].Trim(); // Trim spaces first
                    string spanishText = row[2].Trim(); // Trim spaces first

                    // Print raw values
                   // Debug.Log($"Raw: English - {englishText}, Spanish - {spanishText}");

                    // Remove quotes if they exist at the start and end of the string
                    if (englishText.StartsWith("\"") && englishText.EndsWith("\"") && englishText.Length > 1)
                    {
                        englishText = englishText.Substring(1, englishText.Length - 2);
                    }

                    if (spanishText.StartsWith("\"") && spanishText.EndsWith("\"") && spanishText.Length > 1)
                    {
                        spanishText = spanishText.Substring(1, spanishText.Length - 2);
                    }

                    // Print values after processing
                   // Debug.Log($"Processed: English - {englishText}, Spanish - {spanishText}");

                    string[] csvValues = new string[] { englishText, spanishText };
                    localizationData[csvKey] = csvValues;
                    keys.Add(csvKey);
                }
            }

            if (keys.Contains(key))
            {
                currentIndex = keys.IndexOf(key);
            }
        }
        else
        {
            Debug.LogError("Localization data file not found!");
        }
    }


    void UpdateTextField()
    {
        if (localizationData.ContainsKey(keys[currentIndex]))
        {
            string[] values = localizationData[keys[currentIndex]];

            // Language selection based on the flag "useFirstLanguage"
            string localizedValue = useFirstLanguage ? values[0] : values[1];


            textField.text = localizedValue;
        }
        else
        {
            textField.text = "KEY_NOT_FOUND";
        }
    }

    public void NextButtonClicked()
    {
        currentIndex++;
        if (currentIndex >= keys.Count)
        {
            currentIndex = 0;
        }
       // Debug.Log("Key Index: " + currentIndex);    // DebugLog For The Key CurrentIndex
        key = keys[currentIndex];

        UpdateTextField();

        if (key.EndsWith("E\""))
        {
            Continuebutton.SetActive(false);
            Exitbutton.SetActive(true);
        }
        else
        {
            Continuebutton.SetActive(true);
            Exitbutton.SetActive(false);
        }
    }

    // Function to update all localized texts with the selected language
    static void UpdateAllLocalizedTexts(bool useFirstLanguage)
    {
        foreach (var key in keys)
        {
            if (localizationData.TryGetValue(key, out string[] values))
            {
                string localizedValue = useFirstLanguage ? values[0] : values[1];
                localizationData[key] = new string[] { localizedValue, useFirstLanguage ? values[1] : values[0] };
            }
        }




        // Update all instances of TextLocalizerUI
        TextLocalizerUI[] allInstances = FindObjectsOfType<TextLocalizerUI>(); // Passing true includes inactive objects
        foreach (var instance in allInstances)
        {
            instance.UpdateTextField();
        }
    }

    // English button click event
    public void EnglishButtonClicked()
    {
        useFirstLanguage = true; // Set the flag to true to use the English row
        UpdateAllLocalizedTexts(useFirstLanguage);
        //Debug.Log("DO I GET HERE ENG1 " + useFirstLanguage);
        PlayerPrefs.SetInt("LanguagePreference", useFirstLanguage ? 0 : 1); // Save the language preference in PlayerPrefs (0 for English, 1 for Spanish)
       // Debug.Log("DO I GET HERE ENG2 " + useFirstLanguage);
    }

    // Spanish button click event
    public void SpanishButtonClicked()
    {
        useFirstLanguage = false; // Set the flag to false to use the Spanish row
        UpdateAllLocalizedTexts(useFirstLanguage);
        PlayerPrefs.SetInt("LanguagePreference", useFirstLanguage ? 0 : 1); // Save the language preference in PlayerPrefs (0 for English, 1 for Spanish)
     
    }
}
