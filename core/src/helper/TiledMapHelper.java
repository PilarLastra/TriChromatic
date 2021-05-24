package helper;

import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledMapHelper {

    private TiledMap tiledMap;


    // Metodos //

    public TiledMapHelper(){


    }

    public OrthogonalTiledMapRenderer setupMap(){

        tiledMap = new TmxMapLoader().load("maps/Mapa 2.tmx");

        return new OrthogonalTiledMapRenderer(tiledMap);

    }



}
