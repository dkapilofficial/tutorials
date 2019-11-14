Feature: Book Store

  Scenario: Correct non-zero number of books found by author by list
  	Given I have the following books in the store by list
  	  | The Devil in the White City          | Erik Larson      |
  	  | The Lion, the Witch and the Wardrobe | C.S. Lewis       |
  	  | In the Garden of Beasts              | Erik Larson      |
  	  | Jane Eyre                            | Charlotte Bronte |
  	  | Frankenstein                         | Mary Shelley     |
  	When I search for books by author Erik Larson
  	Then I find 2 books
  	
  Scenario: Correct non-zero number of books found by author by map
  	Given I have the following books in the store by map
  	  | title                                | author           |
  	  | The Devil in the White City          | Erik Larson      |
  	  | The Lion, the Witch and the Wardrobe | C.S. Lewis       |
  	  | In the Garden of Beasts              | Erik Larson      |
  	  | Jane Eyre                            | Charlotte Bronte |
  	  | Frankenstein                         | Mary Shelley     |
  	When I search for books by author Erik Larson
  	Then I find 2 books
  	
  Scenario: Correct non-zero number of books found by author with custom table parsing
  	Given I have the following books in the store with custom table parsing
  	  | title                                | author           |
  	  | The Devil in the White City          | Erik Larson      |
 	  | The Lion, the Witch and the Wardrobe | C.S. Lewis       |
  	  | In the Garden of Beasts              | Erik Larson      |
  	  | Jane Eyre                            | Charlotte Bronte |
  	  | Frankenstein                         | Mary Shelley     |
  	When I search for books by author Erik Larson
  	Then I find 2 books