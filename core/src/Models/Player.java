package Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

public class Player {


    private String nombre;
    private int vida;
    private int daño;
    private Texture player;
    private Rectangle pj;

    public Player(String nombre, int daño, Rectangle rectangle, Texture texture) {
        this.nombre = nombre;
        this.vida = 100;
        this.daño = daño;
        this.player = texture;
        this.pj = rectangle;
    }

    public Rectangle getPj() {
        return pj;
    }

    public void PlayerRectangle(Rectangle rectangle){

        rectangle.x = 0;
        rectangle.y = 0;
        rectangle.width = 64;
        rectangle.height = 64;

    }



    public void playerMove (){
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            pj.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            pj.x += 200 * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            pj.y += 200 * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            pj.y -= 200 * Gdx.graphics.getDeltaTime();
        }
    }


}
