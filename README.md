# Gaming Fans Nadir
## Table of Contents
- [Ease of Use](#ease-of-use)
- [Implementation](#implementation)
    - [Anagram.java](#anagramjava)
        - [main()](#main)
        - [inputHandler()](#inputhandler)
        - [lineHander()](#linehandler)
        - [debugSupportClasses()](#debugsupportclasses)
    - [AnaObj.java](#anaobjjava)
        - [Constructor](#constructor)
        - [initMap()](#initmap)
        - [storeChars()](#storechars)
        - [check()](#check)
        - [take()](#take)
        - [charsLeft()](#charsleft)
    - [Dictionary](#dictionary)
        - [Constructor](#constructor-1)
        - [orderByLength()](#orderbylength)
        - [initTempHash()](#inittemphash)
        - [setIndeces()](#setindeces)
        - [getLengthStartingIndex()](#getlengthstartingindex)
        - [getWord()](#getword)
        - [getChars()](#getchars)
        - [dictionaryLength()](#dictionarylength)
- [Memory Use](#memory-use)
- [Introduction](#introduction)
- [Task](#task)
- [Standards](#standards)
- [Objectives](#objectives)

## Ease of Use
> instead of compiling all the classes its easier to just run ```./compile.sh``` in bash
## Implementation

### Anagram.java
#### main()
> takes in lines from std.in
>> adds lines to arraylist
>> hands that arraylist to ```inputHandler()```
>> ```inputHandler()``` returns array of strings with all the non-letters removed
>>> *note to self - check how this works with macrons and other flairs to letters*
>> splits the arraylist of strings around the empty line
>> adds Strings before empty line to words ArrayList
>> adds String after empty line to dictionary ArrayList
>> create an array of Objects from the ```AnaObj.java``` support class from every element in the words ArrayList.
>> creates the object dictionary from the dictionary ArrayList
>> clears both the rawWords, dictionary, and words ArrayLists to save memory.
#### inputHandler()
> takes in ArrayList
> in for loop:
>> calls ```lineHandler()``` and adds the output to temp variable
>> checks if line is empty, if so removes and goes back 1 index
>>> *note to self - this might not be necessary*
>> if temp isn't empty, it adds it to the output arraylist
> returns the output arraylist as a regular array.
#### lineHandler()
> takes in String handed to it by ```inputHandler()```
> creates new StringBuilder
> in for loop:
>> iterates through char at every index of the string
>> if the current char is a letter
>>> adds its lowercase version to the output StringBuilder.
> returns ```output.toString();```
#### debugSupportClasses()
> iterates through the words in the AnaObj object array
> iterates through words in dictionary
> iterates through indices of lengths of words in dictionary

### AnaObj.java
#### Constructor
> initialises a HashMap with keys from A-Z using ```initMap()``` where all values are 0
> stores chars in the HashMap from the input string using ```storeChars()```
#### initMap()
> for loop iterates through a-z
>> adds a key of the current char and sets the value to 0
#### storeChars()
> initialises a char array
> in for loop:
>> iterates through chars in array
>> stores amount of current char in temp value
>> adds the current char amt+1 back into the hashMap, then adds one char to the datafield ```amtChars```
#### check()
> returns the int value of the char handed to it
#### take()
> takes in char value or char and int values
> stores amount of char specified in temp int value
> removes either the amount specified in the input int or removes 1 from the char in hashMap
> then removes the amount removed from the amtChar int value.
#### charsLeft()
> returns the amtChars datafield.

### Dictionary
#### Constructor
> the constructor takes in an ArrayList of Strings
> it then runs the initialisation methods ```orderByLength()```, ```setIndeces()``` and ```initTempHash()```.
#### orderByLength()
> takes in ArrayList of Strings passed to it from constructor
> uses collections to sort the ArrayList by descending length
> then iterates through elements from input array and adds to lines datafield
#### initTempHash()
> initialises a template of how HashMaps for all the words will look to save computing time later.
#### setIndeces()
> takes in input String ArrayList
> in for loop:
>> checks that the current length is different to the last lines length
>>> if so, changes the temp value so that its length at the current index
>>> then puts the temp value as the key and the index number as the value in the indeces HashMap.
#### getLengthStartingIndex()
> if the passed through int value is not a key value in the indeces HashMap, return 0
> else return the value of the key of the passed through value.
#### getWord()
> returns the word at the specified index -> mainly useful for debugging
#### getChars()
> creates an output HashMap based on the template HashMap
> splits the word at the specified index value into a char array
> for loop iterates through char array:
>> inits a integer value j that equals the value of the char in the output array
>> puts the char value as the key and adds 1 to the value of j
#### dictionaryLength()
> returns the length of the dictionary

## Memory Use
```AnaObj.java``` support class that makes a HashMap of all characters in the anagram in the line.
In terms of memory use, this program isn't too bad, each line stored as an object will take about 3kb, 
```Dictionary.java``` A single instance of the dictionary will take up around 19.25mb of memory when stored in an arraylist.

## !unused
Contains items and methods used to understand the task.
```words.txt``` provided by [this](https://github.com/dwyl/english-words) git repository, given its use of [unlicense](https://unlicense.org/).


## Introduction
Two strings are [anagrams](https://en.wikipedia.org/wiki/Anagram) of one another if, ignoring capitalisation, punctuation and
whitespace they contain the same characters. For instance Finding Anagrams and
Gaming fan’s nadir are anagrams. Utilities such as [I rearrangement servant](https://wordsmith.org/anagram/) can
help find anagrams which arise most commonly in cryptic crossword puzzles but also
in other sorts of wordplay. The intent of this étude is to ask you to reproduce some of
the functionality of such utilities.
Specifically, given a dictionary and a string you’re going to be asked to find the “best”
anagram for the string from words in the dictionary. The definition of “better” (and
hence “best”) in this context is as follows.
• Anagrams using longer words earlier are better than ones using shorter words.
So any anagram that begins with a five-letter word is better than any anagram
that begins with a four-letter word.
• If two anagrams start with a word of the same length, then the one where that
word is earlier in alphabetical order is better. If they start with the same word,
you compare first the lengths of the second word (longer is better), or again by
alphabetical order etc.

## Task
Write a program that finds the best anagrams for each of a number of strings from a
given dictionary. For the purpose of the following description, a “word” is just a string
whose characters come from the range a-z (i.e., lower case letters). Input from stdin
will be of the following form:
• a series of lines consisting of words,
• an empty line (or one consisting only of whitespace),
• another series of words.
The first group of words in the input are the words for which we seek anagrams. The
second group (after the empty line) are the source dictionary which might consist of up
to 100,000 words.
Output should be to stdout. For each word from the first group (and in the same order
as they were input) a single line consisting of that word, a colon, a space, and then the
best anagram for that word from the dictionary. If there are no anagrams for the word
from the dictionary then the word, colon and space should still be printed.
For instance if the input is:
> apple
> appleapple
> frog
> app
> el
> leap
> pel
Then the output should be:
> apple: app el
> appleapple: leap app pel
> frog:

## Standards
For an achieved standard, the program must work correctly on valid input representing
words of length 12 or less and dictionaries of at most 1000 words.
Merit criteria include the ability to handle much larger dictionaries and words efficiently, gracefully dealing with poorly-formatted input (e.g., not all lower case), and
clearly written and well-structured code.
Excellence criteria include some significant extension to the functionality of the program, or an investigation of general properties of the problem.

## Objectives
1.2, 1.4, 2.2, 2.3, 2.7, 2.9, 3.3, 3.4, 3.5, 3.6, 4.3.
(Pair)