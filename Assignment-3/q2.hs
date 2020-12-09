import Data.IORef
import Data.List
import Data.Maybe
import System.IO.Unsafe
import Control.Monad
import System.Random

-- global variable to check if the fixtures have been generated
flag :: IORef Bool
flag = unsafePerformIO $ newIORef False

-- global variable to store the index of permutation of list 
curIndex :: IORef Int
curIndex = unsafePerformIO $ newIORef 0

-- list containing acronyms for registered teams
departmentTeams = ["BS", "CM", "CH", "CV", "CS", "DS", "EE", "HU", "MA", "ME", "PH", "ST"]

-- get a shuffled list at current index
teams index = permutations departmentTeams !! index

-- function to generate and store a random integer
getRandomIndex = do
	g <- newStdGen
	writeIORef curIndex ((take 1 $ (randomRs (0::Int, 100) g))!!0)

-- list containing all possible schedules
schedule = [(1,"9:30"), (1,"7:30"), (2,"9:30"), (2,"7:30"), (3,"9:30"), (3,"7:30")]

-- function to print a match at a given index
printMatch matches matchIndex = 
	putStrLn ((fst $ (matches!!matchIndex))++" vs "++(snd $ (matches!!matchIndex))++" "++(show (fst $ (schedule!!matchIndex)))++"-12-2020 "++(snd $ (schedule!!matchIndex)))

-- function to print all the matches
printAllMatches [] [] = putStr ""
printAllMatches (x:xs) (y:ys) = do
	putStrLn ((fst $ x)++" vs "++(snd $ x)++" "++(show (fst $ y))++"-12-2020 "++(snd $ y))
	printAllMatches xs ys

-- Utility function to the fixture of a particular team
fixtureUtil x = do
	listIndex <- readIORef curIndex
	let teamList = teams listIndex
	let matches = [((teamList !! i), (teamList !! (i+6))) | i <- [0,1..5]]
	let matchIndex =  (fromJust (elemIndex x teamList)) `rem` 6
	printMatch matches matchIndex

-- Function to generate the fixture for all the teams
fixture "all" = do
	getRandomIndex
	index <- readIORef curIndex
	writeIORef flag True
	let teamList = teams index
	let matches = [ ((teamList !! i), (teamList !! (i+6))) | i <- [0,1..5]]
	printAllMatches matches schedule

-- Function to fetch fixture for a particular team
fixture x = do
	flagvar <- readIORef flag
	if flagvar == False
		then putStrLn "No fixtures generated. Run fixture \"all\""
	else if (x `elem` departmentTeams) == False 
		then putStrLn "Entered team name does not exist."
	else fixtureUtil x

-- Utility function to fetch a match after given date and time
nextMatchUtil date time = do
	listIndex <- readIORef curIndex
	let teamList = teams listIndex
	let matches = [((teamList !! i), (teamList !! (i+6))) | i <- [0,1..5]]
	if date==1 && time <= 9.5
		then printMatch matches 0
	else if date==1 && time <= 19.50
		then printMatch matches 1
	else if date==1
		then printMatch matches 2
	else if date==2 && time <= 9.50
		then printMatch matches 2
	else if date==2 && time <= 19.50
		then printMatch matches 3
	else if date==2
		then printMatch matches 4
	else if date==3 && time <= 9.50
		then printMatch matches 4
	else if date==3 && time <= 19.50
		then printMatch matches 5
	else putStrLn "No more matches after this date and time"	

-- function to get next match after a given date and time
nextMatch date time = do
	flagvar <- readIORef flag
	if flagvar == False
		then putStrLn "No fixtures generated. Run fixture \"all\""
	else if date<1 || date>31
		then putStrLn "Entered date not valid"
	else if time<0 || time>23.99
		then putStrLn "Entered time not valid"
	else if date>3
		then putStrLn "No more matches after this date and time"
	else nextMatchUtil date time