package Game;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.action.basicactions.MoveBackwardAction;
import eea.engine.action.basicactions.MoveDownAction;
import eea.engine.action.basicactions.MoveForwardAction;
import eea.engine.action.basicactions.MoveLeftAction;
import eea.engine.action.basicactions.MoveRightAction;
import eea.engine.action.basicactions.MoveUpAction;
import eea.engine.action.basicactions.Movement;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.Event;
import eea.engine.event.basicevents.KeyDownEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.LeavingScreenEvent;
import eea.engine.event.basicevents.LoopEvent;
import eea.engine.event.basicevents.MouseClickedEvent;

public class InGameState extends BasicGameState {

    private int stateID; // Identifier dieses BasicGameState
    private StateBasedEntityManager entityManager; // zugehoeriger entityManager

    InGameState(int sid) {
        stateID = sid;
        entityManager = StateBasedEntityManager.getInstance();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	Entity background = new Entity("bg");	// Entitaet fuer Hintergrund
    	background.setPosition(new Vector2f(600,500));	// Startposition des Hintergrunds
    	background.setScale(1.8f);
    	background.addComponent(new ImageRenderComponent(new Image("assets/playgroundBg.png"))); 
    	    	
    	// Hintergrund-Entitaet an StateBasedEntityManager uebergeben
    	entityManager.addEntity(stateID, background);
    	
    	Entity character = new Entity("Character");
		character.setPosition(new Vector2f(container.getWidth() / 2, container.getHeight() / 2));
		try {
			character.addComponent(new ImageRenderComponent(new Image("assets/char.png")));
		} catch (SlickException e) {
			System.err.println("Cannot find file assets/char.png!");
			e.printStackTrace();
		}

		KeyDownEvent keyUp = new KeyDownEvent(Input.KEY_W);
		KeyDownEvent keyLeft = new KeyDownEvent(Input.KEY_A);
		KeyDownEvent keyDown = new KeyDownEvent(Input.KEY_S);
		KeyDownEvent keyRight = new KeyDownEvent(Input.KEY_D);
		float speed = 1f;
		keyUp.addAction(new MoveUpAction(speed));
		keyLeft.addAction(new MoveLeftAction(speed));
		keyDown.addAction(new MoveDownAction(speed));
		keyRight.addAction(new MoveRightAction(speed));
		character.addComponent(keyUp);
		character.addComponent(keyLeft);
		character.addComponent(keyDown);
		character.addComponent(keyRight);

		entityManager.addEntity(stateID, character);
    	
    	
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
