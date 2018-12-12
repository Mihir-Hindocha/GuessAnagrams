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
    private List<String> wordList = new ArrayList<String>();
    private Set<String> wordSet = new HashSet<String>();
    private Map<String, List<String>> lettersToWord = new HashMap<String, List<String>>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String sortedWord = sortLetters(word);
            if (lettersToWord.containsKey(sortedWord)) {
                lettersToWord.get(sortedWord).add(word);
            } else {
                List<String> anagramList = new ArrayList<String>();
                anagramList.add(word);
                lettersToWord.put(sortedWord, anagramList);
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

    public boolean isGoodWord(String word, String base) {
        return true;
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
        return result;
    }

    public String pickGoodStarterWord() {
        return "stop";
    }
}
