package toolbox.ll.com.toolbox.core.inject;

/**
 * Created by ll on 2018/3/27.
 */

public class BarrageAttachment extends CustomAttachment<BarrageAttachment.Barrage>{

    public BarrageAttachment() {
    }

    public BarrageAttachment(String text) {
        super();
        setType(CustomAttachmentType.BARRAGE);
        setData(new Barrage(text));
    }

    public class Barrage{
        public Barrage() {
        }
        public Barrage(String text) {
            this.text = text;
        }

        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
