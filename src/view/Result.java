package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class Result {

	private JPanel result;
	private String articleBody;
	
	public Result(String title, String articleBody) {
		
		this.result = new JPanel();
		
		this.result.setLayout(new BoxLayout(result, BoxLayout.PAGE_AXIS));
		result.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createTitledBorder(null, title,
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION,
									new Font("Arial", Font.PLAIN, 18),
									new Color(0, 0, 0)),
							BorderFactory.createEmptyBorder(5,5,5,5)));

		result.setMinimumSize(new Dimension(1000, 150));
		result.setPreferredSize(new Dimension(1000, 150));
		result.setMaximumSize(new Dimension(1000, 150));

		this.articleBody = articleBody;
		
		initialize();
	}
	
	public void initialize() {
		
		JEditorPane editorPane = new JEditorPane(new HTMLEditorKit().getContentType(), articleBody);

		// Add a CSS rule to force body tags to use the selected font
        // instead of default value.
		String bodyRule = "body { font-family: " + "Arial" + "; " +
        		"font-size: " + 16 + "pt; }";
        ((HTMLDocument)editorPane.getDocument()).getStyleSheet().addRule(bodyRule);
        
        editorPane.setOpaque(false);
		editorPane.setEditable(false);
		editorPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		editorPane.setPreferredSize(new Dimension(50, 130));

		result.add(editorPane);
	}
	
	public JPanel getResult() {
		return this.result;
	}
}
