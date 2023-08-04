package Game;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

//import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Font;
import org.newdawn.slick.TrueTypeFont;


public class MainMenuState extends BasicGameState {

    private int stateID; 							// Identifier von diesem BasicGameState
    private StateBasedEntityManager entityManager; 	// zugehoeriger entityManager

    private final int distance = 100;
    private final int start_Position = 180;

    //design constants for main menu class
    private static final int leftPadding = 25;
    private static final int topPadding = 400;
    private static final int optionsSpacing = 25;

    TextField start;
    TextField quit;

    MainMenuState( int sid ) {
        stateID = sid;
        entityManager = StateBasedEntityManager.getInstance();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        // Hintergrund laden
        Entity mainBackground = new Entity("mainBackground");
        mainBackground.setPosition(new Vector2f(container.getWidth()/2.0f,container.getHeight()/2.0f));
        mainBackground.addComponent(new ImageRenderComponent(new Image("/assets/mainBackground.png")));

        // Hintergrund-Entitaet an StateBasedEntityManager uebergeben
        entityManager.addEntity(this.stateID, mainBackground);

        //Initialize the button to create a new Game
        Entity startButton = new Entity("startButton");

        startButton.addComponent(new ImageRenderComponent(new Image("/assets/greyButtonBackground.png")));
        startButton.setScale((float)0.5);
        startButton.setPosition(new Vector2f(leftPadding + startButton.getSize().x, topPadding));

        ANDEvent startEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
        Action newGameAction = new ChangeStateInitAction(Game.Launch.CHARACTER_SELECTION_STATE);
        startEvent.addAction(newGameAction);

        startButton.addComponent(startEvent);

        entityManager.addEntity(this.stateID, startButton);

        //read font into program
        java.awt.Font startFont = new java.awt.Font("/fonts/Bangers-Regular.ttf", java.awt.Font.PLAIN, 40);
        TrueTypeFont bangers = new TrueTypeFont(startFont, true);

        int posX = (int) startButton.getPosition().x - 50;
        int posY = (int) startButton.getPosition().y - 25;
        start = new TextField(container, bangers, posX, posY, (int)startButton.getSize().getX(), (int)startButton.getSize().getY());
        start.setBackgroundColor(null);
        start.setBorderColor(null);
        start.setText("Start");

        int spacing = topPadding + (int)startButton.getSize().y + optionsSpacing;
        //initialize the button to quit the game
        Entity quitButton = new Entity("quitButton");

        quitButton.addComponent((new ImageRenderComponent(new Image("/assets/greyButtonBackground.png"))));
        quitButton.setScale((float)0.5);
        quitButton.setPosition(new Vector2f(leftPadding + quitButton.getSize().x, spacing));

        ANDEvent quitEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
        Action quitGameAction = new QuitAction();
        quitEvent.addAction(quitGameAction);

        quitButton.addComponent(quitEvent);
        entityManager.addEntity(this.stateID, quitButton);

        posX = (int) quitButton.getPosition().x - 50;
        posY = (int) quitButton.getPosition().y - 25;
        quit = new TextField(container, bangers, posX, posY, (int)startButton.getSize().getX(), (int)startButton.getSize().getY());
        quit.setBackgroundColor(null);
        quit.setBorderColor(null);
        quit.setText("Quit");
    }

    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        entityManager.updateEntities(container, game, delta);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // StatedBasedEntityManager soll alle Entities rendern
        entityManager.renderEntities(container, game, g);
        start.render(container, g);
        quit.render(container, g);

        //g.drawString("Neues Spiel", 110, topPadding);
    }

    @Override
    public int getID() {
        return stateID;
    }
}
