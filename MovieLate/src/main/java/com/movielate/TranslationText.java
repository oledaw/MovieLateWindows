package com.movielate;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate.TranslateOption;

public class TranslationText {
	
	static String onTranslate(String orginalSubtitlesText) {
		
		String translatedSubtitilesText;
		 // Instantiates a client
	    Translate translate = TranslateOptions.getDefaultInstance().getService();


	    Translation translation =
	        translate.translate(
	            orginalSubtitlesText,
	            TranslateOption.sourceLanguage("en"),
	            TranslateOption.targetLanguage("pl"));


	    System.out.printf("Translation: %s%n", translation.getTranslatedText());
	    translatedSubtitilesText = translation.getTranslatedText();
		return translatedSubtitilesText;
	}

}
