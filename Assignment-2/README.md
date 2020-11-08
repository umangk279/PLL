# Prolog

Code files can be run using SWI-Prolog.
To run the files, execute the following commands:
1. $ swipl
2. ?- consult('<filename>.pl'). with appropriate filename.

## Problem 1

After consulting the file, the first part of the problem ie. the relationship UNCLE can be checked as: \
?- uncle(katappa,avantika). or ?- uncle(A, B). 

Similarly, for second part of the problem ie. the relationship HALFSISTER can be checked as: \
?- halfsister(A, B). or ?- halfsister(A, shivkami).

## Problem 2

The file contains bus details in the format: bus(Number, Origin, Destination Place, Departure Time, Arrival Time, Distance, Cost) and implements the rule for finding optimum routes on the basis of distance, cost and time..

The optimized routes between two places based on distance, cost and time can be obtained by running: ?- route('A','B'). where A and B are the required city names present in the bus details and three optimum routes are returned.

Sample queries with clearly show different optimal paths oin the basis of different factors are:

?- route('Bathinda','Ludhiana'). \
?- route('Bathinda','Moga').

## Problem 3

The files the data about distance between each pair of gates which can be directly visited as given in the assignment.

A. All the possible paths for a prisoner to escape from the jail can be found using:
?- get_all_paths. \
This returns a list of all the possible paths along with distance for each path.

B. Optimal path for the prisoner to escape along which prison has to
cover minimum distance to escape from the jail can be found using: ?- optimal. \
This returns the optimal path along with distance along this path.

C. Given a path, to check if it is valid or not, run ?- valid(Path) where Path is a list denoting a path that needs to be validated. \
Program returns true if the path is valid else false is returned. \
Sample queries are: \
?- valid(['G4','G6','G5','G8','G7','G12','G10','G11','G15','G13','G14','G17']). \
?- valid(['G1','G7','G12','G10','G11','G15','G13','G14','G17']). \
?- valid(['G6','G7','G12','G10','G11','G15','G13','G14','G17']).
