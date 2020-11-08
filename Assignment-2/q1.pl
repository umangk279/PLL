% facts given in assignment
parent(jatin,avantika).
parent(jolly,jatin).
parent(jolly,katappa).
parent(manisha,avantika).
parent(manisha,shivkami).
parent(bahubali,shivkami).
male(katappa).
male(jolly).
male(bahubali).
female(shivkami).
female(avantika).

% Utility Rule to check if X and Y are siblings
siblings(X,Y) :- parent(Z,X), parent(Z,Y), X\=Y.

% Rule to check if X is uncle of Y
uncle(X,Y) :- male(X),parent(Z,Y),siblings(X,Z).

% Utility rule to check if X and Y hav a common parent
other_parent(X,Y,Z) :- parent(A,X), parent(A,Y), A\=Z.

% Rule to check if X is half sister of Y.
halfsister(X,Y) :- female(X),parent(Z,X),parent(Z,Y),not(other_parent(X,Y,Z)).
