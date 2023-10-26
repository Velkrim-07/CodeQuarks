package com.example.codequarks

data class DataEntry(val Topic : String? = null, val Desc : String? = null, val Citation : String? = null,
                     val Quiz : String? = null, val Type : Int? = null, val Option1 : String? = null,
                     val Prompt : String? = null, val Option2 : String? = null, val Option3 : String? = null,
                     val Option4 : String? = null)
/*
This data class is meant to incorporate all the information possible when reading or writing to the
database.
Topic - the overview of what the other information will go over. Just a grouping
Desc - Description of the topic. Will be used heavily to display to the user what the topic is about
Citation - Basic Citation to show where the information came from
Quiz - SubGrouping for all of the information in regards to quiz usage
Type - The type of quiz. 1 is just true or false, while 2 is your standard quiz format where there are
4 possible answers and 1 is correct. IMPORTANT - There will most likely be more types of quizzes to show
off that I can make more than just 2 types
Option1 - The first option of the quiz and the option that is always correct. There can be more than
one answer that is correct, but Option1 wil ALWAYS be correct
Prompt - The prompt that will be shown at the top of the screen to prompt the user with a question
or statement that can be answered to get the quiz right
Option2-4 Secondary options for the quizzes. They can either be right or wrong and this will be distinguished
with the 'Type' Parameter
 */
