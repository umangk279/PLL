package q1.sockMatchingRobot;

public class MatchingMachine {

    // Booleans to store if one sock of that particular sock has already reached matching machine
    private boolean hasSeenWhite;
    private boolean hasSeenBlack;
    private boolean hasSeenBlue;
    private boolean hasSeenGrey;

    // Shelf manager associated with the matching machine/system
    private ShelfManager shelfManager;

    // Locks for 4 booleans
    private Object locks[] = {new Object(), new Object(), new Object(), new Object()};

    // Constructor
    MatchingMachine(ShelfManager sm) {
        hasSeenWhite = false;
        hasSeenBlack = false;
        hasSeenBlue = false;
        hasSeenGrey = false;
        shelfManager = sm;
    }

    // Function to handle the incoming sock to matching machine
    void handleSock(int color) {

        // Printing that the matching machine has received the sock
        System.out.println("Matching machine got sock " + Constants.colorMap[color]);

        switch (color) {
            case Constants.WHITE_SOCKS:
                // Acquiring the lock for boolean
                synchronized (locks[color]) {
                    if (hasSeenWhite) {
                        // Pair is complete and should be passed to shelf manager
                        shelfManager.handleSockPair(color);
                        hasSeenWhite = false;
                    } else {
                        // One sock for a pair received
                        hasSeenWhite = true;
                    }
                    break;
                }
            case Constants.BLACK_SOCKS:
                // Acquiring the lock for boolean
                synchronized (locks[color]) {
                    if (hasSeenBlack) {
                        // Pair is complete and should be passed to shelf manager
                        shelfManager.handleSockPair(color);
                        hasSeenBlack = false;
                    } else {
                        // One sock for a pair received
                        hasSeenBlack = true;
                    }
                    break;
                }
            case Constants.BLUE_SOCKS:
                // Acquiring the lock for boolean
                synchronized (locks[color]) {
                    if (hasSeenBlue) {
                        // Pair is complete and should be passed to shelf manager
                        shelfManager.handleSockPair(color);
                        hasSeenBlue = false;
                    } else {
                        // One sock for a pair received
                        hasSeenBlue = true;
                    }
                    break;
                }
            case Constants.GREY_SOCKS:
                // Acquiring the lock for boolean
                synchronized (locks[color]) {
                    if (hasSeenGrey) {
                        // Pair is complete and should be passed to shelf manager
                        shelfManager.handleSockPair(color);
                        hasSeenGrey = false;
                    } else {
                        // One sock for a pair received
                        hasSeenGrey = true;
                    }
                    break;
                }
        }
    }
}
