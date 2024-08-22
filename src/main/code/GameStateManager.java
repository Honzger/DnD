package main.code;

import java.awt.Graphics;
import java.util.Stack;

public class GameStateManager {
    private static GameStateManager instance;
    private Stack<GameState> states;

    private GameStateManager() {
        states = new Stack<>();
    }

    public static synchronized GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }

    public synchronized void pushState(GameState state) {
        states.push(state);
    }

    public synchronized void popState() {
        if (!states.isEmpty()) {
            states.pop();
        }
    }

    public synchronized void setState(GameState state) {
        popState();
        pushState(state);
    }

    public synchronized void update() {
        if (!states.isEmpty()) {
            states.peek().update();
        }
    }

    public synchronized void render(Graphics g) {
        if (!states.isEmpty()) {
            states.peek().render(g);
        }
    }

    public synchronized void handleInput() {
        if (!states.isEmpty()) {
            states.peek().handleInput();
        }
    }
}
