package by.task.komar.parser;

import by.task.komar.entity.impl.CompositeType;
import by.task.komar.entity.impl.TextComposite;

public class ParagraphParser extends AbstractParser {
    private final String PARAGRAPH_REGEX = "[\\n\\t]+";

    @Override
    public TextComposite parse(String data) {
        TextComposite paragraphComposite = new TextComposite(CompositeType.PARAGRAPH);
        String[] paragraphs = data.split(PARAGRAPH_REGEX);
        for (String paragraph : paragraphs) {
            TextComposite nextComposite = nextParser.parse(paragraph);
            paragraphComposite.add(nextComposite);
        }
        return paragraphComposite;
    }
}
