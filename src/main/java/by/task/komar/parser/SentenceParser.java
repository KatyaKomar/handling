package by.task.komar.parser;

import by.task.komar.entity.impl.CompositeType;
import by.task.komar.entity.impl.TextComposite;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceParser extends AbstractParser {
    private final String SENTENCE_REGEX = "([А-ЯA-Z]((!=|.toString)|[^?!.(]|\\([^)]*\\))*[.?!]{1,3})";

    @Override
    public TextComposite parse(String data) {
        TextComposite sentenceComposite = new TextComposite(CompositeType.SENTENCE);
        Pattern pattern = Pattern.compile(SENTENCE_REGEX);
        Matcher matcher = pattern.matcher(data);
        List<String> sentences = new ArrayList<>();
        while (matcher.find()) {
            sentences.add(matcher.group());
        }
        for (String sentence : sentences) {
            TextComposite nextComposite = nextParser.parse(sentence);
            sentenceComposite.add(nextComposite);
        }
        return sentenceComposite;
    }
}
