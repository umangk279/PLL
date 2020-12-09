# Haskell (Functional Programming)

To run the files, execute the following commands:
1. $ ghci
2. Prelude> :load <filename>.hs (with appropriate filename)

## Problem 1

After loading the file, the first part of the problem ie. if the set is empty can be checked as: \
\*Main> isEmpty [] or \*Main> isEmpty [1,2]

Similarly, for second part of the problem ie. union can be checked as: \
\*Main> union [1,2,3] [1]

For the third part ie. intersection of sets can be checked as:
Main> intersect [1,2,3] [1]

For the fourth part it. subtraction of sets can be checked as:
Main> sub [1,2,3] [1]

for the fifth part ie. addition of sets can be checked as:
Main> add [1,2,3] [1]

## Problem 2

All the fixtures can be generated using: fixture "all" \
Fixture for a particular team can generated usinf: fixture x, where x is the name of the team. \
Details of the next match can be found using: nextMatch date time. \
Both fixture x and nextMatch require that the fixtures are first generated using fixture "all" command. \

## Problem 3

The design can be generated using: design <area> <number of bedrooms> <number of halls>. \
If not design is possible with the given input values and the constraints in the problem "Design not possible is printed". \
The output takes around 12-14 seconds on average to display the output. \

Sample inputs:

Input: design 1000 2 3
Output: 
Bedroom: 2 (15 x 11)
Hall: 3 (15 x 10)
Kitchen: 1 (7 x 5)
Bathroom: 3 (4 x 5)
Balcony: 1 (5 x 5)
Garden: 1 (10 x 10)
Unused Space: 0

Input: design 2000 3 3
Output: 
Bedroom: 3 (15 x 14)
Hall: 3 (20 x 14)
Kitchen: 1 (15 x 12)
Bathroom: 4 (4 x 5)
Balcony: 1 (10 x 9)
Garden: 1 (18 x 10)
Unused Space: 0
