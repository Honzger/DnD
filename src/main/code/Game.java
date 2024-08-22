package main.code;

import java.awt.Graphics;

public class Game implements Runnable {
    private WindowFrame windowFrame;
    private GameStateManager gameStateManager;
    private boolean running;
    private Thread gameThread;

    public Game() {
    	PreStart pr = new PreStart();
        windowFrame = new WindowFrame();
        gameStateManager = GameStateManager.getInstance();
        gameStateManager.pushState(new StartingScreen(windowFrame));
        running = false;
    }

    public synchronized void start() {
        if (running) return;

        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop() {
        if (!running) return;

        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double nsPerUpdate = 1000000000.0 / 60.0; // 60 updates per second
        double delta = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerUpdate;
            lastTime = now;

            while (delta >= 1) {
                update();
                delta--;
            }

            render();
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }

            // Sleep to avoid running too fast
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop();
    }

    private void update() {
        gameStateManager.update();
    }

    private void render() {
        Graphics g = windowFrame.getGraphics();
        if (g != null) {
            gameStateManager.render(g);
            g.dispose();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}

