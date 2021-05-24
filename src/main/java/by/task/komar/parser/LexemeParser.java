package by.task.komar.parser;

import by.task.komar.entity.impl.CompositeType;
import by.task.komar.entity.impl.TextComposite;

public class LexemeParser extends AbstractParser {
    private final String LEXEME_REGEX = "[\\s]+";

    @Override
    public TextComposite parse(String data) {
        TextComposite lexemeComposite = new TextComposite(CompositeType.LEXEME);
        String[] lexemes = data.split(LEXEME_REGEX);
        for (String lexeme : lexemes) {
            TextComposite nextComposite = nextParser.parse(lexeme);
            lexemeComposite.add(nextComposite);
        }
        return lexemeComposite;
    }
}
