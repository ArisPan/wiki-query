package model;

public class InputParser {

	private String inputText;
	
	public InputParser(String inputText) {

		this.inputText = inputText.strip();
	}
	
	public String parse() {
		
		if (!inputText.contains(" "))
			return "TermQuery";
		else if (inputText.contains("*") || inputText.contains("?"))
			return "WildCardQuery";

		return "PhraseQuery";	
	}
}