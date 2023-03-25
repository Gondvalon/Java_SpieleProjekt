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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class CharacterSelectionState extends BasicGameState {
    private int stateID; 							// Identifier von diesem BasicGameState
    private StateBasedEntityManager entityManager; 	// zugehoeriger entityManager

    private final int distance = 100;
    private final int start_Position = 180;

    CharacterSelectionState( int sid ) {
        stateID = sid;
        entityManager = StateBasedEntityManager.getInstance();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	// Hintergrund laden
    	Entity background = new Entity("menu");	// Entitaet fuer Hintergrund
    	background.setPosition(new Vector2f(600,500));	// Startposition des Hintergrunds
    	background.setScale(1.8f);
    	background.addComponent(new ImageRenderComponent(new Image("assets/bg.png"))); // Bildkomponente
    	    	
    	// Hintergrund-Entitaet an StateBasedEntityManager uebergeben
    	entityManager.addEntity(stateID, background);
    	
    	
    	/* Neues Spiel starten-Entitaet */
    	Entity new_Game_Entity = new Entity("Start game");
    	
    	// Setze Position und Bildkomponente
    	new_Game_Entity.setPosition(new Vector2f(218, 290));
    	new_Game_Entity.setScale(1f);
    	new_Game_Entity.addComponent(new ImageRenderComponent(new Image("assets/playBtn.png")));
    	
    	// Erstelle das Ausloese-Event und die zugehoerige Action
    	ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action new_Game_Action = new ChangeStateInitAction(Launch.INGAME_STATE);
    	mainEvents.addAction(new_Game_Action);
    	new_Game_Entity.addComponent(mainEvents);
    	
    	entityManager.addEntity(this.stateID, new_Game_Entity);
    	
    	
    	
    }

    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        entityManager.updateEntities(container, game, delta);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // StatedBasedEntityManager soll alle Entities rendern
        entityManager.renderEntities(container, game, g);
    }

    @Override
    public int getID() {
        return stateID;
    }
}
