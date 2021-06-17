package UI;


import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DetailedStatusBox extends StatusBox{

    private Label hpTextPj;

    public DetailedStatusBox(Skin skin) {
        super(skin);
        hpTextPj = new Label("NAN/NAN", skin);
        uiContainer.row();
        uiContainer.add(hpTextPj).pad(20f).expand().right();

    }

    public void setHpTextPJ (int hpLeft, int hpTotal){
        hpTextPj.setText(hpLeft+"/"+hpTotal);
    }

    }
