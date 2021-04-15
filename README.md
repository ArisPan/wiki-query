# Wiki Query
A desktop application that searches through a set of Wikipedia articles using Apache Lucene.


Given a static corpus (a directory of text files), we are able to create an inverted index and perform searches. Ten retrieved results are displayed per page and are sorted by their relevance to the search query. Search terms are highlighted in the results, plus, you are able to open and preview the file. Supports different kinds of queries like Term, Phrase, Wildcard, Prefix, Boolean and Span.\
The project is written in Java SE 14 and uses Apache Lucene 8.5.1. Application's GUI is implemented using Swing.

## Motive
With a heavy emphasis on GoF design patterns and implementing the MVC model, Wiki Query presents an exploration of software development architecture principles and their best practises. It also constitutes an introduction to Information Retrieval on a static corpus, indexing and query building.

## How to run
Currently, there is no Maven/Gradle automation for building the project. You can import the project in your IDE of choice and build it from there.
1. [Import with IntelliJ](https://www.jetbrains.com/help/idea/import-project-or-module-wizard.html)
2. [Import with Eclipse](https://help.eclipse.org/2019-09/index.jsp?topic=%2Forg.eclipse.platform.doc.user%2FgettingStarted%2Fqs-31a.htm&cp%3D0_1_0_7)

## Screenshots
![HomeScreen](https://user-images.githubusercontent.com/11043114/114861327-c7d36380-9df5-11eb-92db-daef6d6ce517.jpg)
![ResultsScreen1](https://user-images.githubusercontent.com/11043114/114861340-cc981780-9df5-11eb-8f02-57554d2a64af.jpg)
![ResultsScreen2](https://user-images.githubusercontent.com/11043114/114861354-d15ccb80-9df5-11eb-8f9e-df501d52c271.jpg)
