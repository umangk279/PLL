% Bus Schedule in the following format:
% Bus (Number, Origin, Destination Place, Departure Time, Arrival Time, Distance, Cost)
bus(246, 'Bathinda', 'Ludhiana', 10, 17, 30, 550).
bus(415, 'Bathinda', 'Jalandhar', 16, 18.5, 21, 75).
bus(074, 'Bathinda', 'Moga', 12.5, 15.5, 12, 60).
bus(512, 'Bathinda', 'Faridkot', 9, 11.5, 19, 36).
bus(470, 'Faridkot', 'Moga', 11, 12, 10, 22).
bus(123, 'Moga', 'Jalandhar', 19.5, 22.5, 45, 74).
bus(321, 'Patiala', 'Moga', 18, 19.5, 48, 100).
bus(194, 'Jalandhar', 'Ludhiana', 14, 17.4, 94, 250).
bus(014, 'Amritsar', 'Ludhiana', 10, 12, 36, 70).
bus(105, 'Jalandhar', 'Amritsar', 16.7, 19.4, 64, 125).
bus(811, 'Patiala', 'Amritsar', 20, 21.5, 53, 115).
bus(167, 'Jalandhar', 'Patiala', 19, 20, 15, 45).

% Rule to check if a list is empty
is_empty([]).

% utility rule to print lists
show_list([]).
show_list(X):- 
	X = [A|B],
	A = [C,D],
	writef('Dist: %w Path: %w\n', [C,D]),
	show_list(B).

% Rule to print optimal output
print_optimal(List) :- 
	List = [Optimal|_],
	Optimal = [_,[Path, Dist, Cost, Time]],
	writef('Path: %w\n', [Path]),
	writef('Distance=%w Cost=%w Time=%w\n',[Dist,Cost,Time]),
	show_list([]).

% Rule to find path between two locations
path(From, To, Path, Dist, Cost, Time):- 
	path(From, To, [From], Q, Dist, Cost, Time,0,_),
	reverse(Q, Path).

% Rule to consider the case of points which are not directly connected
path(From,To,Visited,Path,Dist,Cost,Time,OldArrival,Waiting) :- 
	bus(_,From,Stop,T1,T2,D,C),
	Stop \== To,
	\+member(Stop,Visited),
	((OldArrival\=0, T1>=OldArrival) -> (Waiting is T1-OldArrival)
	; (OldArrival\=0, T1<OldArrival) -> (Waiting is 24+T1-OldArrival)
	; (OldArrival==0) -> (Waiting is 0)),
	path(Stop,To,[Stop|Visited],Path,Dist1,Cost1,Time1,T2,_),
	Dist is D+Dist1,
	Cost is C+Cost1,
	Time is Time1+T2-T1+Waiting.

% Rule to consider the case of points which are directly connected
path(From,To,Visited,[To|Visited],Dist,Cost,Time,OldArrival,Waiting) :- 
	bus(_,From,To,T1,T2,Dist,Cost),
	((OldArrival\=0, T1>=OldArrival) -> (Waiting is T1-OldArrival)
	; (OldArrival\=0, T1<OldArrival) -> (Waiting is 24+T1-OldArrival)
	; (OldArrival==0) -> (Waiting is 0)),
	Time is T2-T1+Waiting.

% Utility function to find all routes between two locations and print the optimal path 
route_util(From,To,Path,Dist) :-
	% finding optimal distance path
	findall([Dist,[Path,Dist,Cost,Time]], path(From, To, Path, Dist, Cost, Time), List_Dist),
	not(is_empty(List_Dist)) 
	-> (
	  %writef('Route from %w to %w exists\n', [From, To]),
	  sort(List_Dist,  Sorted_Dist),
	  % show_list(Sorted_Dist),
	  % show_list(List_Dist),
	  writef('Optimum Distance:\n'),
	  print_optimal(Sorted_Dist)
	  ),
	% finding optimal cost path
	findall([Cost,[Path,Dist,Cost,Time]], path(From, To, Path, Dist, Cost, Time), List_Cost),
	not(is_empty(List_Cost)) 
	-> (
	  %writef('Route from %w to %w exists\n', [From, To]),
	  sort(List_Cost,  Sorted_Cost),
	  %show_list(Sorted_Cost)
	  writef('Optimum Cost:\n'),
	  print_optimal(Sorted_Cost)
	  ),
	% finding optimal time path
	findall([Time,[Path,Dist,Cost,Time]], path(From, To, Path, Dist, Cost, Time), List_Time),
	not(is_empty(List_Time)) 
	-> (
	  %writef('Route from %w to %w exists\n', [From, To]),
	  sort(List_Time,  Sorted_Time),
	  %show_list(Sorted_Time)
	  writef('Optimum Time:\n'),
	  print_optimal(Sorted_Time)
	  )
	; writef('There is no route from %w to %w\n', [From, To])
	.

% Rule to find routes between given locations such that distance is optimal, 
% cost is optimal and time is optimal
route(From,To):- route_util(From,To,_,_).
