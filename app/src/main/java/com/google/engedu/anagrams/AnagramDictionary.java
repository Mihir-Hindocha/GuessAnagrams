/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private List<String> wordList;
    private Set<String> wordSet;
    private Map<String, List<String>> lettersToWord;
    private Map<Integer, List<String>> sizeToWord;
    private int wordLength;

    public AnagramDictionary(Reader reader) throws IOException {
        wordList = new ArrayList<String>();
        wordSet = new HashSet<String>();
        lettersToWord = new HashMap<String, List<String>>();
        sizeToWord = new HashMap<Integer, List<String>>();
        wordLength = DEFAULT_WORD_LENGTH;

        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            /*
            adding words to hashmap from sorted words to corresponding anagram list
             */
            String sortedWord = sortLetters(word);
            if (lettersToWord.containsKey(sortedWord)) {
                lettersToWord.get(sortedWord).add(word);
            } else {
                List<String> anagramList = new ArrayList<String>();
                anagramList.add(word);
                lettersToWord.put(sortedWord, anagramList);
            }
            /*
            adding words to hashmap from word length to corresponding words list
             */
            int length = word.length();
            if (sizeToWord.containsKey(length)) {
                sizeToWord.get(length).add(word);
            } else {
                List<String> sizeWordList = new ArrayList<String>();
                sizeWordList.add(word);
                sizeToWord.put(length, sizeWordList);
            }
        }
    }

    public String sortLetters(String inputWord) {
        //create a character array
        char tempArray[] = inputWord.toCharArray();
        //sort tempArray
        Arrays.sort(tempArray);
        //return the sorted string
        return new String(tempArray);
    }

    /*
    provided word is a valid dictionary word
    word should not contain base as a substring
     */
    public boolean isGoodWord(String word, String base) {
        if (wordSet.contains(word) && word.indexOf(base) >= 0) {
            return true;
        }
        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        List<String> result = new ArrayList<String>();
        String sortedTargetWord = sortLetters(targetWord);
        if (lettersToWord.containsKey(sortedTargetWord)) {
            result.addAll(lettersToWord.get(sortedTargetWord));
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        List<String> anagrams = new ArrayList<String>();
        for (char i = 'a'; i <= 'z'; i++) {
            String newWord = i + word;
            anagrams.add(sortLetters(newWord));
        }
        for (String anagramValue : anagrams) {
            if (lettersToWord.containsKey(anagramValue)) {
                result.addAll(lettersToWord.get(anagramValue));
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        List<String > wordLengthList = new ArrayList<String>();
        wordLengthList = sizeToWord.get(wordLength);
        if(wordLength < MAX_WORD_LENGTH){
            wordLength++;
        }
        while(true){
            String result = wordLengthList.get(random.nextInt(wordLengthList.size()));
            if (getAnagramsWithOneMoreLetter(result).size() >= MIN_NUM_ANAGRAMS){
                return result;
            }
        }
    }

}
