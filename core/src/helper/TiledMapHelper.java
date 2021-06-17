package helper;

import Screeen.BattleScreen;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import Screeen.GameScreen;

import static helper.Constante.PPM;

public class TiledMapHelper {

    private TiledMap tiledMap;
    private GameScreen gameScreen;


    private BattleScreen battleScreen;
    private Body body;



    // Metodos //

    public TiledMapHelper(GameScreen gameScreen){
        this.gameScreen = gameScreen;

    }

    public TiledMapHelper(BattleScreen battleScreen){
        this.battleScreen = battleScreen;

    }

//Este get esta echo para poder pasarle el mapa al actor
    public TiledMap getTiledMap() {
        return tiledMap;
    }



    public OrthogonalTiledMapRenderer setupMap(String path){

        tiledMap = new TmxMapLoader().load(path);
        parseMapObjects(tiledMap.getLayers().get("Objetos").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);

    }

    private void parseMapObjects(MapObjects mapObjects){

        for (MapObject mapObject : mapObjects) {

            if(mapObject instanceof PolygonMapObject){
                createStaticBody((PolygonMapObject) mapObject);
            }
        }
    }

    private void createStaticBody(PolygonMapObject polygonMapObject){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape (polygonMapObject);
        body.createFixture(shape, 1.0f);
        shape.dispose();

    }

    private ChainShape createPolygonShape(PolygonMapObject polygonMapObject) {

        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for(int i =0; i < worldVertices.length; i++){

            worldVertices[i] = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);

        }

        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;

    }




}
