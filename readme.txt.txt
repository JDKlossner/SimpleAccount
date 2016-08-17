Jordan Klossner
A04107313

~~~~~~~SimpleAcc~~~~~~~~~
Run Jar Executable including a commandline argument of your input file. Ex. SimpleAcc.jar input.txt

input file should be formatted as such: note: Past the first two lines(which can be empty but should exist), there is only one space between every character.
    name          | id     |   amount         
--------------------------------------        When saved it will not show 2 decimal places if it does not have them. $0.50 -> $0.5
Daniel Nguyen | 234 | $180.21                 However it will not round the account balances when saved.
Terry Crews | 351 | $633.56                   Also IDs will lose any zeros at the start of the id. 078910 -> 78910
John Smith | 451 | $3000.78
Manu Ginobli | 461 | $3000.02
Bob Barker | 481 | $3000.35

Names must contain only letters and a space.
IDs must contain only numbers.
Amount/Balance can only contain numbers and a decimal place.

I could not get seperate views of the same account to update any others when any change is made to balance, only once the un-updated view makes an action such as depositing or withdrawing will it show the correct and updated balance from its own and of those before.