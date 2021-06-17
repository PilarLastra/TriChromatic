package UI;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class StatusBox extends Table {

    private Label text;
    protected Table uiContainer;

    public StatusBox(Skin skin) {
        super(skin);
        this.setBackground("BOXES");
        uiContainer = new Table ();
        this.add(uiContainer).pad(0f).expand();
        text = new Label("namenull", skin); ///lo que va a decir, el skin y el tipo de letra
        uiContainer.add(text).padTop(5f);
    }

    public void setText(String newText) {
        text.setText(newText);
    }
}
