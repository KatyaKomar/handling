package by.task.komar.service.impl;

import by.task.komar.entity.Component;
import by.task.komar.entity.impl.CompositeType;
import by.task.komar.entity.impl.TextComposite;
import by.task.komar.exception.CompositeException;
import by.task.komar.service.TextService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextServiceImpl implements TextService {
    private static Logger logger = LogManager.getLogger();

    @Override
    public List<Component> sortParagraphs(TextComposite composite) throws CompositeException {
        if (composite == null) {
            logger.log(Level.ERROR, "Composite is null");
            throw new CompositeException("Composite is null");
        } else if (!composite.getType().equals(CompositeType.PARAGRAPH)) {
            logger.log(Level.ERROR, "Can't sort " + composite);
            throw new CompositeException("Can't sort " + composite);
        }
        List<Component> sortedParagraphs = composite.getList();
        sortedParagraphs.sort(new Comparator<Component>() {
            @Override
            public int compare(Component o1, Component o2) {
                return o1.size() - o2.size();
            }
        });
        return sortedParagraphs;
    }

    @Override
    public List<Component> findSentencesWithLongWord(TextComposite composite) throws CompositeException {
        if (composite == null) {
            logger.log(Level.ERROR, "Composite is null");
            throw new CompositeException("Composite is null");
        } else if (!composite.getType().equals(CompositeType.PARAGRAPH)) {
            logger.log(Level.ERROR, "Can't found sentences in " + composite);
            throw new CompositeException("Can't found sentences in " + composite);
        }
        int maxLength = 0;
        List<Component> sentencesWithWord = new ArrayList<>();
        List<Component> paragraphs = composite.getList();
        for (Component paragraph : paragraphs) {
            List<Component> sentences = paragraph.getList();
            for (Component sentence : sentences) {
                List<Component> lexemes = sentence.getList();
                for (Component lexeme : lexemes) {
                    List<Component> words = lexeme.getList();
                    for (Component word : words) {
                        if (word.getType().equals(CompositeType.LETTER)) {
                            if (word.size() > maxLength) {
                                maxLength = word.size();
                                sentencesWithWord.clear();
                                sentencesWithWord.add(sentence);
                            } else if (word.size() == maxLength && !sentencesWithWord.contains(sentence)) {
                                sentencesWithWord.add(sentence);
                            }
                        }
                    }
                }
            }
        }
        return sentencesWithWord;
    }

    @Override
    public List<Component> deleteSentencesWithShortWord(TextComposite composite, int minLength) throws CompositeException {
        if (composite == null) {
            logger.log(Level.ERROR, "Composite is null");
            throw new CompositeException("Composite is null");
        } else if (!composite.getType().equals(CompositeType.PARAGRAPH)) {
            logger.log(Level.ERROR, "Can't delete sentences in " + composite);
            throw new CompositeException("Can't delete sentences in " + composite);
        }
        List<Component> sentencesWithoutWord = new ArrayList<>();
        List<Component> paragraphs = composite.getList();
        for (Component paragraph : paragraphs) {
            List<Component> sentences = paragraph.getList();
            sentencesWithoutWord.addAll(sentences);
            for (Component sentence : sentences) {
                List<Component> lexemes = sentence.getList();
                for (Component lexeme : lexemes) {
                    List<Component> words = lexeme.getList();
                    for (Component word : words) {
                        if (word.getType().equals(CompositeType.LETTER)) {
                            if (word.size() < minLength) {
                                sentencesWithoutWord.remove(sentence);
                            }
                        }
                    }
                }
            }
        }
        return sentencesWithoutWord;
    }

    @Override
    public List<String> countRepeteWords(TextComposite composite) throws CompositeException {
        if (composite == null) {
            logger.log(Level.ERROR, "Composite is null");
            throw new CompositeException("Composite is null");
        } else if (!composite.getType().equals(CompositeType.PARAGRAPH)) {
            logger.log(Level.ERROR, "Can't find words in " + composite);
            throw new CompositeException("Can't find words in " + composite);
        }
        Map<String, Integer> textWords = new HashMap<>();
        List<Component> paragraphs = composite.getList();
        for (Component paragraph : paragraphs) {
            List<Component> sentences = paragraph.getList();
            for (Component sentence : sentences) {
                List<Component> lexemes = sentence.getList();
                for (Component lexeme : lexemes) {
                    List<Component> words = lexeme.getList();
                    for (Component word : words) {
                        if (word.getType().equals(CompositeType.LETTER)) {
                            int counter = 1;
                            String wordWithoutCase = word.toString().toLowerCase();
                            if (textWords.containsKey(wordWithoutCase)) {
                                counter = textWords.get(wordWithoutCase) + 1;
                            }
                            textWords.put(wordWithoutCase, counter);
                        }
                    }
                }
            }
        }
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> word : textWords.entrySet()) {
            if (word.getValue() > 1) {
                result.add(word.getKey());
            }
        }
        return result;
    }

    @Override
    public int countVowels(TextComposite composite) throws CompositeException {
        final String VOWEL_REGEX = "[aeiouyаеёиоуыэюя]";
        Pattern pattern = Pattern.compile(VOWEL_REGEX);
        Matcher matcher;
        if (composite == null) {
            logger.log(Level.ERROR, "Composite is null");
            throw new CompositeException("Composite is null");
        } else if (!composite.getType().equals(CompositeType.LEXEME)) {
            logger.log(Level.ERROR, "Can't count vowels in " + composite);
            throw new CompositeException("Can't count vowels in " + composite);
        }
        int counter = 0;
        List<Component> lexemes = composite.getList();
        for (Component lexeme : lexemes) {
            List<Component> words = lexeme.getList();
            for (Component word : words) {
                if (word.getType().equals(CompositeType.LETTER)) {
                    List<Component> letters = word.getList();
                    for (Component letter : letters) {
                        matcher = pattern.matcher(letter.toString().toLowerCase());
                        if (matcher.matches()) {
                            counter++;
                        }
                    }
                }
            }
        }
        return counter;
    }

    @Override
    public int countConsonants(TextComposite composite) throws CompositeException {
        final String CONSONANT_REGEX = "[bcdfghjklmnpqrstvwxzбвгджзйклмнпрстфхцчшщ]";
        Pattern pattern = Pattern.compile(CONSONANT_REGEX);
        Matcher matcher;
        if (composite == null) {
            logger.log(Level.ERROR, "Composite is null");
            throw new CompositeException("Composite is null");
        } else if (!composite.getType().equals(CompositeType.LEXEME)) {
            logger.log(Level.ERROR, "Can't count consonant in " + composite);
            throw new CompositeException("Can't count consonant in " + composite);
        }
        int counter = 0;
        List<Component> lexemes = composite.getList();
        for (Component lexeme : lexemes) {
            List<Component> words = lexeme.getList();
            for (Component word : words) {
                if (word.getType().equals(CompositeType.LETTER)) {
                    List<Component> letters = word.getList();
                    for (Component letter : letters) {
                        matcher = pattern.matcher(letter.toString().toLowerCase());
                        if (matcher.matches()) {
                            counter++;
                        }
                    }
                }
            }
        }
        return counter;
    }
}
