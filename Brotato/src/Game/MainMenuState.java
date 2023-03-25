package Game;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.component.Component;
import eea.engine.component.RenderComponent;
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


public class MainMenuState extends BasicGameState {

    private int stateID; 							// Identifier von diesem BasicGameState
    private StateBasedEntityManager entityManager; 	// zugehoeriger entityManager

    private final int distance = 100;
    private final int start_Position = 180;

    MainMenuState( int sid ) {
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
    	new_Game_Entity.setPosition(new Vector2f(218, 190));
    	new_Game_Entity.setScale(1f);
    	new_Game_Entity.addComponent(new ImageRenderComponent(new Image("assets/playBtn.png")));
    	
    	// Erstelle das Ausloese-Event und die zugehoerige Action
    	ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action new_Game_Action = new ChangeStateInitAction(Launch.CHARACTER_SELECTION_STATE);
    	mainEvents.addAction(new_Game_Action);
    	new_Game_Entity.addComponent(mainEvents);
    	
    	// Fuege die Entity zum StateBasedEntityManager hinzu
    	entityManager.addEntity(this.stateID, new_Game_Entity);
    	
    	
    	
    	/* Optionen-Entitaet */
    	Entity options_Entity = new Entity("Options");
    	
    	// Setze Position und Bildkomponente
    	options_Entity.setPosition(new Vector2f(218, 320));
    	options_Entity.setScale(1f);
    	options_Entity.addComponent(new ImageRenderComponent(new Image("assets/optionBtn.png")));
    	
    	// Erstelle das Ausloese-Event und die zugehoerige Action
    	ANDEvent mainEvents_o = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action options_Action = new ChangeStateInitAction(Launch.OPTIONS_STATE);
    	mainEvents_o.addAction(options_Action);
    	options_Entity.addComponent(mainEvents_o);
    	
    	// Fuege die Entity zum StateBasedEntityManager hinzu
    	entityManager.addEntity(this.stateID, options_Entity);
    	
    	
    	/* Beenden-Entitaet */
    	Entity quit_Entity = new Entity("Beenden");
    	
    	// Setze Position und Bildkomponente
    	quit_Entity.setPosition(new Vector2f(218, 450));
    	quit_Entity.addComponent(new ImageRenderComponent(new Image("assets/exitBtn.png")));
    	
    	// Erstelle das Ausloese-Event und die zugehoerige Action
    	ANDEvent mainEvents_q = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action quit_Action = new QuitAction();
    	mainEvents_q.addAction(quit_Action);
    	quit_Entity.addComponent(mainEvents_q);
    	
    	// Fuege die Entity zum StateBasedEntityManager hinzu
    	entityManager.addEntity(this.stateID, quit_Entity);
    }

    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        entityManager.updateEntities(container, game, delta);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // StatedBasedEntityManager soll alle Entities rendern
        entityManager.renderEntities(container, game, g);
        
		//int counter = 0;

		//g.drawString("Neues Spiel", 110, start_Position+counter*distance); counter++;
		//g.drawString("Optionen", 110, start_Position+counter*distance); counter++;
		//g.drawString("Beenden", 110, start_Position+counter*distance); counter++;
    }

    @Override
    public int getID() {
        return stateID;
    }
}
