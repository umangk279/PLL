import Data.List hiding (union, intersect)

-- function to check if a list is empty or not
isEmpty [] = True
isEmpty x = False 

-- function to find union of two sets
union :: Ord a => [a] -> [a] -> [a]
union [] [] = []
union [] y = y
union x [] = x

union x (y:ys) = if y `elem` x
					then (union x ys)
				else (union (x++[y]) ys)

-- function to find intersection of two sets
intersect :: Ord a => [a] -> [a] -> [a]
intersect [] [] = []
intersect [] y = []
intersect x [] = x

intersect (x:xs) y = if x `elem` y
						then [x]++(intersect xs y)
					else intersect xs y

-- function to sort a given list
sortList :: Ord a => [a] -> [a]
sortList x = sort x

-- function to remove duplicates from a list
removeDuplicates :: Ord a => [a] -> [a]
removeDuplicates [] = []
removeDuplicates (x:xs) = if x `elem` xs
							then removeDuplicates xs
						else [x] ++ (removeDuplicates xs)

-- function to add two sets
add :: (Ord a, Num a) => [a] -> [a] -> [a]
add [] [] = []
add [] y = []
add x [] = []

add (x:xs) (y:ys) = removeDuplicates (sortList ([x+y]++(add xs ys)++(add (x:xs) ys)++(add xs (y:ys))))

-- function to subtract one set from another
sub :: Ord a => [a] -> [a] -> [a]
sub [] [] = []
sub [] y = []
sub x [] = x

sub (x:xs) y = if x `elem` y
						then sub xs y
					else [x] ++ (sub xs y)
