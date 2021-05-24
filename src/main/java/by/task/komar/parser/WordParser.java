package by.task.komar.parser;

import by.task.komar.entity.impl.CompositeType;
import by.task.komar.entity.impl.PunctuationLeaf;
import by.task.komar.entity.impl.TextComposite;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordParser extends AbstractParser {
    private final String PUNCTUATION_REGEX = "[.=,!?:;)(\\t\\n]";

    @Override
    public TextComposite parse(String data) {
        TextComposite wordComposite = new TextComposite(CompositeType.WORD);
        char[] lexeme = data.toCharArray();
        Pattern pattern = Pattern.compile(PUNCTUATION_REGEX);
        Matcher matcher;
        StringBuilder word = new StringBuilder();
        for (char ch : lexeme) {
            matcher = pattern.matcher(Character.toString(ch));
            if (matcher.matches()) {
                if (word.length()>0) {
                    TextComposite nextComposite = nextParser.parse(word.toString());
                    wordComposite.add(nextComposite);
                    word = new StringBuilder();
                }
                PunctuationLeaf leaf = new PunctuationLeaf(ch);
                wordComposite.add(leaf);
            } else{
                word.append(ch);
            }
        }
        if (word.length()>0) {
            TextComposite nextComposite = nextParser.parse(word.toString());
            wordComposite.add(nextComposite);
        }
        return wordComposite;
    }
}
