package by.task.komar.service;

import by.task.komar.entity.Component;
import by.task.komar.entity.impl.TextComposite;
import by.task.komar.exception.CompositeException;

import java.util.List;

public interface TextService {
    List<Component> sortParagraphs(TextComposite composite) throws CompositeException;

    List<Component> findSentencesWithLongWord(TextComposite composite) throws CompositeException;

    List<Component> deleteSentencesWithShortWord(TextComposite composite, int minLength) throws CompositeException;

    List<String> countRepeteWords(TextComposite composite) throws CompositeException;

    int countVowels(TextComposite composite) throws CompositeException;

    int countConsonants(TextComposite composite) throws CompositeException;
}
