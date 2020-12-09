import Data.List

-- function to remove an element from a list
remove element list = filter (\e -> e/=element) list

-- function to generate a list of possible dimensions in the given range
getAllDimensions x y xMax yMax
	| (x<xMax) = [((x+1),y)]++(getAllDimensions (x+1) y xMax yMax)
	| (y<yMax) = [(x,(y+1))]++(getAllDimensions x (y+1) xMax yMax)
	| otherwise = []

-- function to check if the kitchen is smaller than hall and bedroom
kitchenConstraint (k1,k2) (h1,h2) (b1,b2) = do
    if (k1<=h1 && k2<=h2 && k1<=b1 && k2<=b2)==True
        then True
    else False

-- function to check if bathroom is smaller than kitchen
bathroomConstraint (b1,b2) (k1,k2) = do
    if (b1<=k1 && b2<=k2) == True
        then True
    else False

-- function to calculate area given dimensions for each room and count for each room
getArea [(a1,a2),(b1,b2),(c1,c2),(d1,d2),(e1,e2),(f1,f2)] [x1,x2,x3,x4,x5,x6] = 
    ((x1*a1*a2)+(x2*b1*b2)+(x3*c1*c2)+(x4*d1*d2)+(x5*e1*e2)+(x6*f1*f2))

-- function to check if area is less than or equal to the given area
areaConstraint dimensions counts totalArea = do
    if ((getArea dimensions counts)<=totalArea)
        then True
    else False

-- function to check if the dimensions form a valid design given total area and counts for each room
isValid counts totalArea dimensions = do
    if ((kitchenConstraint (dimensions!!2) (dimensions!!1) (dimensions!!0)) && (bathroomConstraint (dimensions!!3) (dimensions!!2)) && (areaConstraint dimensions counts totalArea))==True
        then dimensions
    else [(0,0), (0,0), (0,0), (0,0), (0,0), (0,0)]

-- function to get the better dimensions from the given pair
getBetterDimensions dimensions1 dimensions2 counts = do
    if (getArea dimensions1 counts) > (getArea dimensions2 counts)
        then dimensions1
    else dimensions2

-- function to recursively change the dimensions of garden
changeGarden _ _ _ [] = [(0,0), (0,0), (0,0), (0,0), (0,0), (0,0)]
changeGarden counts totalArea currentDimensions (x:xs) = do
    let withCurrentGarden = isValid counts totalArea currentDimensions
    let withoutCurrentGarden = changeGarden counts totalArea ((take 5 currentDimensions)++[x]) xs
    getBetterDimensions (isValid counts totalArea withCurrentGarden) (isValid counts totalArea withoutCurrentGarden) counts

-- function to recursively change the dimensions of balcony
changeBalcony _ _ _ [] _ = [(0,0), (0,0), (0,0), (0,0), (0,0), (0,0)]
changeBalcony counts totalArea currentDimensions (x:xs) gardenDimensions = do
    let withCurrentBalcony = changeGarden counts totalArea currentDimensions gardenDimensions
    let withoutCurrentBalcony = changeBalcony counts totalArea ((take 4 currentDimensions)++[x]++(drop 5 currentDimensions)) xs gardenDimensions
    getBetterDimensions (isValid counts totalArea withCurrentBalcony) (isValid counts totalArea withoutCurrentBalcony) counts

-- function to recursively change the dimensions of bathroom
changeBathroom _ _ _ [] _ _ = [(0,0), (0,0), (0,0), (0,0), (0,0), (0,0)]
changeBathroom counts totalArea currentDimensions (x:xs) balconyDimensions gardenDimensions = do
    let withCurrentBathroom = changeBalcony counts totalArea currentDimensions balconyDimensions gardenDimensions
    let withoutCurrentBathroom = changeBathroom counts totalArea ((take 3 currentDimensions)++[x]++(drop 4 currentDimensions)) xs balconyDimensions gardenDimensions
    getBetterDimensions (isValid counts totalArea withCurrentBathroom) (isValid counts totalArea withCurrentBathroom) counts

-- function to recursively change the dimensions of kitchen
changeKitchen _ _ _ [] _ _ _ = [(0,0), (0,0), (0,0), (0,0), (0,0), (0,0)]
changeKitchen counts totalArea currentDimensions (x:xs) bathroomDimensions balconyDimensions gardenDimensions = do
    let withCurrentKitchen = changeBathroom counts totalArea currentDimensions bathroomDimensions balconyDimensions gardenDimensions
    let withoutCurrentKitchen = changeKitchen counts totalArea ((take 2 currentDimensions)++[x]++(drop 3 currentDimensions)) xs bathroomDimensions balconyDimensions gardenDimensions
    getBetterDimensions (isValid counts totalArea withCurrentKitchen) (isValid counts totalArea withoutCurrentKitchen) counts

-- function to recursively change the dimensions of hall
changeHall _ _ _ [] _ _ _ _ = [(0,0), (0,0), (0,0), (0,0), (0,0), (0,0)]
changeHall counts totalArea currentDimensions (x:xs) kitchenDimensions bathroomDimensions balconyDimensions gardenDimensions = do
    let withCurrentHall = changeKitchen counts totalArea currentDimensions kitchenDimensions bathroomDimensions balconyDimensions gardenDimensions
    let withoutCurrentHall = changeHall counts totalArea ((take 1 currentDimensions)++[x]++(drop 2 currentDimensions)) xs kitchenDimensions bathroomDimensions balconyDimensions gardenDimensions
    getBetterDimensions (isValid counts totalArea withCurrentHall) (isValid counts totalArea withoutCurrentHall) counts

-- function to recursively get the best dimensions for each room 
getBestDimensions _ _ _ [] _ _ _ _ _ = [(0,0), (0,0), (0,0), (0,0), (0,0), (0,0)]
getBestDimensions counts totalArea currentDimensions (x:xs) hallDimensions kitchenDimensions bathroomDimensions balconyDimensions gardenDimensions = do
    let withCurrentBedroom = changeHall counts totalArea currentDimensions hallDimensions kitchenDimensions bathroomDimensions balconyDimensions gardenDimensions
    let withoutCurrentBedroom = getBestDimensions counts totalArea ([x]++(drop 1 currentDimensions)) xs hallDimensions kitchenDimensions bathroomDimensions balconyDimensions gardenDimensions
    getBetterDimensions (isValid counts totalArea withCurrentBedroom) (isValid counts totalArea withoutCurrentBedroom) counts

-- Utility function to print the design given dimensions and count for each room
printDesign [(a1,a2),(b1,b2),(c1,c2),(d1,d2),(e1,e2),(f1,f2)] [x1,x2,x3,x4,x5,x6] unusedArea = do

    putStrLn ("Bedroom: "++show(x1)++" ("++show(a1)++" x "++show(a2)++")")
    putStrLn ("Hall: "++show(x2)++" ("++show(b1)++" x "++show(b2)++")")
    putStrLn ("Kitchen: "++show(x3)++" ("++show(c1)++" x "++show(c2)++")")
    putStrLn ("Bathroom: "++show(x4)++" ("++show(d1)++" x "++show(d2)++")")
    putStrLn ("Balcony: "++show(x5)++" ("++show(e1)++" x "++show(e2)++")")
    putStrLn ("Garden: "++show(x6)++" ("++show(f1)++" x "++show(f2)++")")
    putStrLn ("Unused Space: "++show(unusedArea))

-- main function responsible for printng the best design given total area, number of bedrooms and number of halls
design totalArea noBedroom noHall = do
    -- calculate the number of other rooms
    let noGarden = 1
    let noBalcony = 1
    let noBathroom = noBedroom + 1
    let noKitchen = ceiling(fromIntegral noBedroom/3) :: Integer
    let counts = [noBedroom, noHall, noKitchen, noBathroom, noBalcony, noGarden]
    let initial_dimensions = [(10,10),(15,10),(7,5),(4,5),(5,5),(10,10)]

    -- get possible dimensions for each room given the range
    let bedroomDimensions = [(10,10)]++(getAllDimensions 10 10 15 15)
    let hallDimensions = [(15,10)]++(getAllDimensions 15 10 20 15)
    let kitchenDimensions = [(7,5)]++(getAllDimensions 7 5 15 13)
    let bathroomDimensions = [(4,5)]++(getAllDimensions 4 5 8 9)
    let balconyDimensions = [(5,5)]++(getAllDimensions 5 5 10 10)
    let gardenDimensions = [(10,10)]++(getAllDimensions 10 10 20 20)

    -- get the best dimensions
    let dimensions = getBestDimensions counts totalArea initial_dimensions bedroomDimensions hallDimensions kitchenDimensions bathroomDimensions balconyDimensions gardenDimensions
    -- calculate the unused area
    let unusedArea = totalArea - (getArea dimensions counts)

    -- print the found design
    if (getArea dimensions counts)==0
        then putStrLn "Design is not possible"
    else printDesign dimensions counts unusedArea

