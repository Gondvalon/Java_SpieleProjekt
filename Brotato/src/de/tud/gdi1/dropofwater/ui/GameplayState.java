package de.tud.gdi1.dropofwater.ui;

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

/**
 * @author Timo Bähr
 *
 *         Diese Klasse repraesentiert das Spielfenster, indem ein Wassertropfen
 *         erscheint und nach unten faellt.
 */
public class GameplayState extends BasicGameState {

	private int stateID; // Identifier dieses BasicGameState
	private StateBasedEntityManager entityManager; // zugehoeriger entityManager

	GameplayState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}

	/**
	 * Wird vor dem (erstmaligen) Starten dieses States ausgefuehrt
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		// Hintergrund laden
		Entity background = new Entity("background"); // Entitaet fuer Hintergrund
		background.setPosition(new Vector2f(400, 300)); // Startposition des Hintergrunds
		background.addComponent(new ImageRenderComponent(new Image("/assets/background.png"))); // Bildkomponente

		// Hintergrund-Entitaet an StateBasedEntityManager uebergeben
		StateBasedEntityManager.getInstance().addEntity(stateID, background);

		// Bei Drücken der ESC-Taste zurueck ins Hauptmenue wechseln
		Entity esc_Listener = new Entity("ESC_Listener");
		KeyPressedEvent esc_pressed = new KeyPressedEvent(Input.KEY_ESCAPE);
		esc_pressed.addAction(new ChangeStateAction(Launch.MAINMENU_STATE));
		esc_Listener.addComponent(esc_pressed);
		entityManager.addEntity(stateID, esc_Listener);

//		CHARACTER MOVEMENT

		Entity character = new Entity("Character");
		character.setPosition(new Vector2f(container.getWidth() / 2, container.getHeight() / 2));
		try {
			character.addComponent(new ImageRenderComponent(new Image("assets/drop.png")));
		} catch (SlickException e) {
			System.err.println("Cannot find file assets/drop.png!");
			e.printStackTrace();
		}

		KeyDownEvent keyUp = new KeyDownEvent(Input.KEY_W);
		KeyDownEvent keyLeft = new KeyDownEvent(Input.KEY_A);
		KeyDownEvent keyDown = new KeyDownEvent(Input.KEY_S);
		KeyDownEvent keyRight = new KeyDownEvent(Input.KEY_D);
		float speed = 0.1f;
		keyUp.addAction(new MoveUpAction(speed));
		keyLeft.addAction(new MoveLeftAction(speed));
		keyDown.addAction(new MoveDownAction(speed));
		keyRight.addAction(new MoveRightAction(speed));
		character.addComponent(keyUp);
		character.addComponent(keyLeft);
		character.addComponent(keyDown);
		character.addComponent(keyRight);

		entityManager.addEntity(stateID, character);

		// Enemy spawner

		Entity spawnEnemies = new Entity("SpawnEnemies");
		Random r = new Random();
		LoopEvent loop = new LoopEvent();
		loop.addAction(new Action() {
			int time = 0; //replace with ingame clock
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta, Component event) {
				if(time % 1000 == 0) {
					System.out.println("SPAWN");
					Entity enemy = new Entity("Enemy");

					
					enemy.setPosition(new Vector2f(container.getWidth() * r.nextFloat(), container.getHeight() * r.nextFloat()));
					try {
						enemy.addComponent(new ImageRenderComponent(new Image("assets/drop.png")));
					} catch (SlickException e) {
						System.err.println("Cannot find file assets/drop.png!");
						e.printStackTrace();
					}
					LoopEvent enemyLoop = new LoopEvent();
					enemyLoop.addAction(new Movement(speed) {
						
						@Override
						public Vector2f getNextPosition(Vector2f position, float speed, float rotation, int delta) {
							Vector2f pos = new Vector2f(position);
							
							pos.add(position.sub(character.getPosition()).normalise().scale(speed * delta *-1));
							
							
							return pos;
						}
					});
					enemy.addComponent(enemyLoop);
					
					entityManager.addEntity(stateID, enemy);
				}

				
				
				time++;
			}
		});

		spawnEnemies.addComponent(loop);
		entityManager.addEntity(stateID, spawnEnemies);

	}

	/**
	 * Wird vor dem Frame ausgefuehrt
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// StatedBasedEntityManager soll alle Entities aktualisieren
		entityManager.updateEntities(container, game, delta);
	}

	/**
	 * Wird mit dem Frame ausgefuehrt
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// StatedBasedEntityManager soll alle Entities rendern
		entityManager.renderEntities(container, game, g);
	}

	@Override
	public int getID() {
		return stateID;
	}
}
