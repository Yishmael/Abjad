package data;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class KeyHandler {
    private GameContainer gc;
    private Input input;
    private Player player;
    private int c = 0;
    private int index = 0;

    public KeyHandler(GameContainer gc, Player player) throws SlickException {
        this.gc = gc;
        this.player = player;
    }

    public void update() throws SlickException {
        input = gc.getInput();
        player.direction[0] = 0;
        player.direction[1] = 0;
        // TODO GOTTA MOVE THIS TO CLASS KEYLISTENER
        // move up
        if (input.isKeyDown(Input.KEY_NUMPAD5)) {
            player.direction[1] = -1;
        }
        // move down
        if (input.isKeyDown(Input.KEY_NUMPAD2)) {
            player.direction[1] = 1;
        }
        // move left
        if (input.isKeyDown(Input.KEY_NUMPAD1)) {
            player.direction[0] = -1;
        }
        // move right
        if (input.isKeyDown(Input.KEY_NUMPAD3)) {
            player.direction[0] = 1;
        }
        // get current tile name
        if (input.isKeyPressed(Input.KEY_NUMPAD7)) {
            // player.getMapTile().setTile(xTile, yTile,
            // TileType.values()[player.tileIndex]);
            player.addItem(new Boulder(new Image("images/boulder1.png"), "Boulder1", 0, 0, 1, 30, 0.1f));

        }
        // change tile on mouse location
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !player.showMenu) {
            int x = input.getMouseX();
            int y = input.getMouseY();
            int xTile = player.getMapTile().getTileX(x);
            int yTile = player.getMapTile().getTileY(y);
            // System.out.println(xTile + " " + yTile);
            player.getMapTile().setTile(xTile, yTile, TileType.values()[player.tileIndex]);
        }
        // open/close menu or close inventory
        if (input.isKeyPressed(Input.KEY_SUBTRACT)) {
            if (player.showInventory) {
                player.showInventory = false;
            } else {
                player.showMenu = !player.showMenu;
                // easter egg :D
                c++;
                if (c == 42) {
                    c = 0;
                    player.addItem(new Boulder(new Image("images/boulder1.png"), "Boulder1", 0, 0, 1000, 30, 0.2f));
                }
            }
        }
        // add item
        // if (!input.isKeyDown(Input.KEY_LSHIFT) &&
        // input.isKeyPressed(Input.KEY_SPACE)) {
        // if (!player.inventoryFull()) {
        // // player.addItem(ic.getAxes().get(0), 2, 4);
        // player.addItem(new Axe(new Image("images/axe2.png"), "Axe1", 0, 0,
        // 100, 10, 0.33f));
        // } else {
        // System.out.println("Inventory full!");
        // }
        // }
        // // remove item
        // if (input.isKeyDown(Input.KEY_MINUS)) {
        // if (player.getItems().length > 0) {
        // player.removeItem(index++);
        // index %= 30;
        // }
        // }
        // clear inventory
        if (input.isKeyDown(Input.KEY_NUMPAD9)) {
            for (int i = 0; i < player.matrix.length; i++) {
                player.removeItem(i);
            }
        }
        // change brush
        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            player.tileIndex++;
            player.tileIndex %= TileType.values().length;
            if (TileType.values()[player.tileIndex] == TileType.Null) {
                player.tileIndex++;
                player.tileIndex %= TileType.values().length;
            }
            System.out.println("Brush changed to " + TileType.values()[player.tileIndex]);
        }

        if (input.isKeyPressed(Input.KEY_NUMPAD8)) {
            if (!player.showMenu) {
                player.showInventory = !player.showInventory;
            }
            // System.out.println("Inventory " + (player.showInventory ? "on" :
            // "off"));

        }

    }

}
