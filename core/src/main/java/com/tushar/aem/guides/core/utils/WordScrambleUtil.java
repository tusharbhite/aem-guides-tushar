package com.tushar.aem.guides.core.utils;

import java.util.List;

public class WordScrambleUtil {

    String mainWord;
    List<String> meaningfulAnagrams;
    
    public WordScrambleUtil(String mainWord,List<String> meaningfulAnagrams ){
        this.mainWord=mainWord;
        this.meaningfulAnagrams=meaningfulAnagrams;
    }

    public String getMainWord() {
        return mainWord;
    }
    public List<String> getMeaningfulAnagrams() {
        return meaningfulAnagrams;
    }

}
