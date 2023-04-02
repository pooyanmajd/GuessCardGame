Guess Card Game App
==================

**This application is a work in progress 🚧 showcase for playing a simple card game**

# Features

**This application uses Clean Architecture design pattern where logics are separated in use-cases**

# Logic

Upon retrieving the response, the array of cards must be shuffled, and the first one displayed to the user. The user must then be able to guess if the value of the card that follows it will be higher or lower. If the user’s guess is correct, they must be presented with the next card, and the option to continue guessing. Should they guess incorrectly, their total number of correct guesses should be shown, along with the option to start again. User has 3 lives to play.

# Rule

Aces are low, Kings are high.
If next card is equal to previous card then user will continue without gaining a correct guess point.

# License

**Guess Card Game App** is distributed under the terms of the Apache License (Version 2.0).