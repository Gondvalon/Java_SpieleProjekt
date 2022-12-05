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
