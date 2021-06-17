package Models.ObjetosEstaticos;

import Screeen.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static helper.Constante.PPM;

public class Door {
    /// Atributes

    private float x,y;
    private Texture texture;
    private Body body;
    private GameScreen gamescreen;

    /// Contruct

    public Door(float x, float y, /*Texture texture,*/ GameScreen gamescreen) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.gamescreen = gamescreen;
        this.body = createBody();
    }

    /// Getter & Setters

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Body getBody() {
        return body;
    }

    /// Methods

    public Body createBody(){

        BodyDef doorBody = new BodyDef();
        doorBody.position.x = getX();
        doorBody.position.y = getY();
        doorBody.type = BodyDef.BodyType.StaticBody;
        body = gamescreen.getWorld().createBody(doorBody);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(17/2/PPM, 24/2/PPM);
        body.createFixture(shape, 1.0f);
        shape.dispose();

        return body;
    }
}
