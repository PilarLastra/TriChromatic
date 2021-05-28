package Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameScreen;

import java.awt.*;
import java.awt.geom.RectangularShape;
import static helper.Constante.PPM;

public class Player {


    private String nombre;
    private int vida;
    private int danio;
    private Texture player;
    private Rectangle pj;
    private BodyDef playerbody;
    private GameScreen gameScreen;
    private Body body;


    // Metodos //
    public Player(String nombre, int danio, GameScreen gameScreen) {

        this.nombre = nombre;
        this.vida = 100;
        this.danio = danio;
        this.player = new Texture(Gdx.files.internal("PJ/PJ.png"));
        this.pj = new Rectangle();
        this.gameScreen = gameScreen;
        playerRectangle();
        this.body = PjBodyGravity();

    }

    public Rectangle getPj() {
        return pj;
    }

    public Body getBody() { return body;}

    public Texture getPlayer() {
        return player;
    }

    //Crear un rectangulo para representar al personaje logicamente

    public void playerRectangle() {

        pj.x = 1;
        pj.y =3;
        pj.width = 32;
        pj.height = 40;

    }

    //Movimiento del PJ (el 200 * ... logra que el movimiento se traduzca a pixeles asi el pj se mueve fluido y no va pegando saltos de tile en tile)
    public void playerMove() {
        Vector2 vel = body.getLinearVelocity();
        Vector2 pos = body.getPosition();


        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.applyLinearImpulse(-50f, 0, pos.x, pos.y, true);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.applyLinearImpulse(50f, 0, pos.x, pos.y, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.applyLinearImpulse(0, 50f, pos.x, pos.y, true);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            body.applyLinearImpulse(0, -50f, pos.x, pos.y, true);

        }

    }

    //Le voy a tratar d dar gravedad
    public Body PjBodyGravity() {

        BodyDef playerbody = new BodyDef();
        playerbody.type = BodyDef.BodyType.DynamicBody; //Un objeto dinamico se mueve y es afectado x los objetos estaticos, etc
        playerbody.position.set(pj.x, pj.y);
        Body body = gameScreen.getWorld().createBody(playerbody);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(pj.width / 2 / PPM, pj.height / 2 / PPM);



        body.createFixture(shape, 1000);
        shape.dispose();

        return body;

    }
}