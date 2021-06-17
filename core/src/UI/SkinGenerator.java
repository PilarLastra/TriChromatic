package UI;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;




public class SkinGenerator {

    public static Skin generateSkin(AssetManager assetManager) {
        Skin skin = new Skin();

        if (!assetManager.isLoaded("Ui/textBox.atlas")) {
            throw new GdxRuntimeException("uitextBox.atlas was not loaded");
        }

        TextureAtlas uiAtlas = assetManager.get("Ui/textBox.atlas");

        TextureAtlas uiPack = assetManager.get("Ui/uipack.atlas");

        NinePatch dialoguebox = new NinePatch(uiAtlas.findRegion("BOXES"), 10, 10, 5, 5);
        skin.add("BOXES", dialoguebox);

        NinePatch optionBox = new NinePatch(uiPack.findRegion("optionbox"),6,6,6,6);
        skin.add("optionbox", optionBox);


        NinePatch battleinfobox = new NinePatch(uiPack.findRegion("battleinfobox"),14,14,5,8);
        battleinfobox.setPadLeft((int)battleinfobox.getTopHeight());
        skin.add("battleinfobox",battleinfobox);
        skin.add("arrow", uiPack.findRegion("arrow"), TextureRegion.class);
        skin.add("hpbar_side", uiPack.findRegion("hpbar_side"), TextureRegion.class);
        skin.add("hpbar_bar", uiPack.findRegion("hpbar_bar"), TextureRegion.class);
        skin.add("green", uiPack.findRegion("green"), TextureRegion.class);
        skin.add("yellow", uiPack.findRegion("yellow"), TextureRegion.class);
        skin.add("red", uiPack.findRegion("red"), TextureRegion.class);
        skin.add("background_hpbar", uiPack.findRegion("background_hpbar"), TextureRegion.class);





        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/pkmnrsi.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = new Color(96f/255f, 96f/255f, 96f/255f, 1f);
        parameter.shadowColor = new Color(208f/255f, 208f/255f, 200f/255f, 1f);
        parameter.shadowOffsetX = 1;
        parameter.shadowOffsetY = 1;
        parameter.characters = "!  \"  #  $  %  &  '  (  )  *  +  ,  -  .  /  0  1  2  3  4  5  6  7  8  9  :  ;  <  =  >  ?  @  A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z  [  \\  ]  ^  _  `  a  b  c  d  e  f  g  h  i  j  k  l  m  n  o  p  q  r  s  t  u  v  w  x  y  z  {  |  }  ~  \u2190  \u2191  \u2192  \u2193  \u2640  \u2642";

        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        font.getData().setLineHeight(30f);
        skin.add("font", font);

        BitmapFont smallFont = assetManager.get("font/small_letters_font.fnt", BitmapFont.class);
        skin.add("small_letters_font", smallFont);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("font");
        skin.add("default", labelStyle);

        Label.LabelStyle labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = skin.getFont("small_letters_font");
        skin.add("smallLabel", labelStyleSmall);



        return skin;
    }


}
