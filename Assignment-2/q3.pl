% Graph edges with weights as specified in assignment
edge('G1','G5',4).
edge('G2','G5',6).
edge('G3','G5',8).
edge('G4','G5',9).
edge('G1','G6',10).
edge('G2','G6',9).
edge('G3','G6',3).
edge('G4','G6',5).
edge('G5','G7',3).
edge('G5','G10',4).
edge('G5','G11',6).
edge('G5','G12',7).
edge('G5','G6',7).
edge('G5','G8',9).
edge('G6','G8',2).
edge('G6','G12',3).
edge('G6','G11',5).
edge('G6','G10',9).
edge('G6','G7',10).
edge('G7','G10',2).
edge('G7','G11',5).
edge('G7','G12',7).
edge('G7','G8',10).
edge('G8','G9',3).
edge('G8','G12',3).
edge('G8','G11',4).
edge('G8','G10',8).
edge('G10','G15',5).
edge('G10','G11',2).
edge('G10','G12',5).
edge('G11','G15',4).
edge('G11','G13',5).
edge('G11','G12',4).
edge('G12','G13',7).
edge('G12','G14',8).
edge('G15','G13',3).
edge('G13','G14',4).
edge('G14','G17',5).
edge('G14','G18',4).
edge('G17','G18',8).

% entry points for prisoners
entry('G1').
entry('G2').
entry('G3').
entry('G4').

% exit point for prisoners
exit('G17').

% rule to check if list is empty
is_empty([]).

%dynamic rule to store optimal path
:- dynamic(optimal_path/2).

% inital fact for optimal path with high value
optimal_path([],10000).

% connected rule to ensure backward path
connected(X,Y,W):- edge(X,Y,W).
connected(X,Y,W):- edge(Y,X,W).

% Rules to print a path
print([]).
print(Path):-
	Path = [Head|Tail],
	is_empty(Tail),
	writef('%w',[Head]).
print(Path):-
	Path = [Head|Tail],
	writef('%w -> ', [Head]),
	print(Tail).

% Rules to print a list
print_list([]).
print_list(X) :- X = [A|B],
				 A = [Path,Dist],
				 writef('Path: %w with distance = %w\n', [Path,Dist]),
				 print_list(B).

% Rule to update the optimal path
update(PathRev, Dist):-
	reverse(PathRev, Path),
	optimal_path(_,Y),
	Y > Dist,
	retractall(optimal_path(_,_)),
	assertz(optimal_path(Path,Dist)).
update(_,_).

% Rule to find a path between two locations
path(From, To, Path, Dist) :- 
	path(From, To, [From], Q, Dist),
	reverse(Q,Path),
	update(Q, Dist).

% Rule to consider the case of points which are not directly connected
path(From,To, Visited, Path, Dist) :-
	connected(From, Stop, Weight),
	Stop \== To,
	\+member(Stop,Visited),
	path(Stop, To, [Stop|Visited], Path, Dist1),
	Dist is Weight+Dist1.

% Rule to consider the case of points which are directly connected
path(From, To, Visited, [To|Visited], Dist):- 
	connected(From,To,Dist).

% Utility rule to fetch all possible paths between two locations
get_all_paths_util(From, To, Path, Dist,Print) :-
	findall([Path,Dist], path(From,To, Path, Dist), List),
	not(is_empty(List))
	-> (
	  (Print==1) -> print_list(List)
	  %; (print_list(List))
	  )
	; writef('No path exits from %w to %w', [From, To])
	.

% Rule to get all path between entry and exit points
% Fail is added to ensure all the entry and exit points are considered
get_all_paths():- entry(X), exit(Y), get_all_paths_util(X,Y,_,_,1), fail.

% Rule to fetch optimal path and print it
optimal() :- 
	writef("Optimal path: \n"),
	entry(X), exit(Y), get_all_paths_util(X,Y,_,_,0), fail.
optimal() :- 
	optimal_path(Path,Dist),
	writef('Path: '),
	print(Path),
	writef(' with distance = %w\n',[Dist]), !.

% Utility Rule to check validity of path
valid_util(Path):-
	Path = [Head|Tail],
	is_empty(Tail),
	exit(Head).

valid_util(Path):-
	Path = [Node1,Node2|Tail],
	connected(Node1,Node2,_),
	valid_util([Node2|Tail]).

% Rule to check validity of the given path
valid(Path):-
	Path = [Head|_],
	entry(Head),
	valid_util(Path).