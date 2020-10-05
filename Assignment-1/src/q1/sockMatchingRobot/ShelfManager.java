package q1.sockMatchingRobot;

public class ShelfManager {

    ShelfManager() {

    }

    void handleSockPair(int color){
        switch (color) {
            case Constants.WHITE_SOCKS:
                System.out.println("Putting WHITE pair on shelf");
                break;
            case Constants.BLACK_SOCKS:
                System.out.println("Putting BLACK pair on shelf");
                break;
            case Constants.BLUE_SOCKS:
                System.out.println("Putting BLUE pair on shelf");
                break;
            case Constants.GREY_SOCKS:
                System.out.println("Putting GREY pair on shelf");
                break;
        }
    }

}
