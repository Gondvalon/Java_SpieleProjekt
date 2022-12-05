package Game;

import eea.engine.entity.StateBasedEntityManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
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
