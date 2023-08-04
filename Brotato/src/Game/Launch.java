package Game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.StateBasedEntityManager;

public class Launch extends StateBasedGame{

    // Jeder State wird durch einen Integer-Wert gekennzeichnet
    public static final int MAINMENU_STATE = 0;

    public static final int CHARACTER_SELECTION_STATE = 1;

    public static final int INGAME_STATE = 2;

    public Launch() {
        super("Brotato");
    }

    public static void main(String[] args) throws SlickException {
        // Setze den library Pfad abhaengig vom Betriebssystem
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/windows");
        } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/macosx");
        } else {
            System.setProperty("org.lwjgl.librarypath",
                    System.getProperty("user.dir") + "/native/" + System.getProperty("os.name").toLowerCase());
        }

        // Setze dieses StateBasedGame in einen App Container (oder Fenster)
        AppGameContainer app = new AppGameContainer(new Game.Launch());

        // Lege die Einstellungen des Fensters fest und starte das Fenster
        // (nicht aber im Vollbildmodus)
        app.setDisplayMode(1200, 900, false);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer arg0) throws SlickException {

        // Fuege dem StateBasedGame die States hinzu
        // (der zuerst hinzugefuegte State wird als erster State gestartet)
        addState(new MainMenuState(MAINMENU_STATE));
        addState(new CharacterSelectionState(CHARACTER_SELECTION_STATE));
        addState(new InGameState(INGAME_STATE));

        // Fuege dem StateBasedEntityManager die States hinzu
        StateBasedEntityManager.getInstance().addState(MAINMENU_STATE);
        StateBasedEntityManager.getInstance().addState(CHARACTER_SELECTION_STATE);
        StateBasedEntityManager.getInstance().addState(INGAME_STATE);

    }
}
