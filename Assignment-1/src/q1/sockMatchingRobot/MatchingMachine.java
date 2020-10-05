package q1.sockMatchingRobot;

public class MatchingMachine {

    private boolean hasSeenWhite;
    private boolean hasSeenBlack;
    private boolean hasSeenBlue;
    private boolean hasSeenGrey;
    private ShelfManager shelfManager;
    private Object locks[] = {new Object(), new Object(), new Object(), new Object()};

    MatchingMachine(ShelfManager sm) {
        hasSeenWhite = false;
        hasSeenBlack = false;
        hasSeenBlue = false;
        hasSeenGrey = false;
        shelfManager = sm;
    }

    void handleSock(int color) {

        System.out.println("Matching machine got sock " + Constants.colorMap[color]);

        switch (color) {
            case Constants.WHITE_SOCKS:
                synchronized (locks[color]) {
                    if (hasSeenWhite) {
                        shelfManager.handleSockPair(color);
                        hasSeenWhite = false;
                    } else {
                        hasSeenWhite = true;
                    }
                    break;
                }
            case Constants.BLACK_SOCKS:
                synchronized (locks[color]) {
                    if (hasSeenBlack) {
                        shelfManager.handleSockPair(color);
                        hasSeenBlack = false;
                    } else {
                        hasSeenBlack = true;
                    }
                    break;
                }
            case Constants.BLUE_SOCKS:
                synchronized (locks[color]) {
                    if (hasSeenBlue) {
                        shelfManager.handleSockPair(color);
                        hasSeenBlue = false;
                    } else {
                        hasSeenBlue = true;
                    }
                    break;
                }
            case Constants.GREY_SOCKS:
                synchronized (locks[color]) {
                    if (hasSeenGrey) {
                        shelfManager.handleSockPair(color);
                        hasSeenGrey = false;
                    } else {
                        hasSeenGrey = true;
                    }
                    break;
                }
        }
    }
}
