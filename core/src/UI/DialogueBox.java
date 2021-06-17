package UI;


import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class DialogueBox extends Table {

    private String targetText = ""; //Que string queremos q vea el user
    private float animTimer = 0f; //Cuanto tiempo llevamos animando
    private float animationTotalTime = 0f;  //Cuanto tiempo vamos a animar
    private float TIME_PER_CHARACTER = 0.05f;//Tiempo de espera entre caracteres
    private STATE state = STATE.IDLE;


    private Label textLabel;

    private enum STATE {
        ANIMATING,
        IDLE, //Idle = inactivo
        ;
    }

    public  DialogueBox (Skin skin){
        super(skin);
        this.setBackground("BOXES");
        textLabel = new Label("\n", skin);
        this.add(textLabel).expand().align(Align.left).pad(25f);

    }

    public void animateText (String text){


        targetText = text;
        animationTotalTime = text.length() * TIME_PER_CHARACTER; //Cuantos caracteres hay en el texto x cuanto tiempo tenemos entre caracteres
        state = STATE.ANIMATING;
        animTimer = 0f;

    }

    public boolean isFinished() { //si termina se setea en inactivo
        if (state == STATE.IDLE) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if(state == STATE.ANIMATING) {
            animTimer += delta;
            if (animTimer > animationTotalTime) {
                state = STATE.IDLE;
                animTimer = animationTotalTime;
            }


            String actuallyDisplayedText = "";

            int charactersToDisplay = (int) ((animTimer / animationTotalTime) * targetText.length());

            for (int i = 0; i < charactersToDisplay; i++) {
                actuallyDisplayedText += targetText.charAt(i);
            }

            if (!actuallyDisplayedText.equals(textLabel.getText().toString())) {

              this.textLabel.setText(actuallyDisplayedText);


            }
        }
    }


    @Override
    public float getPrefWidth() {
        return 200f;
    }


}
