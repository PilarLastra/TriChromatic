package Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AnimatedPlayer {

    static TextureAtlas atlas;

    static Sprite playerQuieto; //Cuando esta quieto
    static Animation<Sprite> walkRight; //Cuando esta caminando
    static Animation<Sprite> walkLeft;
    static Animation<Sprite> walkUp;
    static Animation<Sprite> walkDown;

    public static void load(){

        atlas = new TextureAtlas(Gdx.files.internal("PJ/Player.txt"));

        playerQuieto = atlas.createSprite("pj0");

        walkDown = new Animation<Sprite>(5,
                atlas.createSprite("dawn_stand_south"),
                atlas.createSprite("dawn_walk_south_0"),
                atlas.createSprite("dawn_walk_south_1"),
                atlas.createSprite("dawn_walk_south_2"));
        walkLeft = new Animation<Sprite>(5,
                atlas.createSprite("dawn_stand_west"),
                atlas.createSprite("dawn_walk_west_0"),
                atlas.createSprite("dawn_walk_west_1"),
                atlas.createSprite("dawn_walk_west_2"));
        walkRight = new Animation<Sprite>(5,
                atlas.createSprite("dawn_stand_east"),
                atlas.createSprite("dawn_walk_east_0"),
                atlas.createSprite("dawn_walk_east_1"),
                atlas.createSprite("dawn_walk_east_2"));
        walkUp = new Animation<Sprite>(5,
                atlas.createSprite("dawn_stand_north"),
                atlas.createSprite("dawn_walk_north_0"),
                atlas.createSprite("dawn_walk_north_1"),
                atlas.createSprite("dawn_walk_north_2"));


    }

    public static void dispose(){

        atlas.dispose();
    }


}
