package toolbox.ll.com.toolbox.core.inject;

/**
 * Created by ll on 2018/3/27.
 */

public class BarrageAttachment extends CustomAttachment<BarrageAttachment.Barrage>{
    public class Barrage{
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
